package fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.changePattern;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.bpmn2.Activity;
import org.eclipse.bpmn2.EndEvent;
import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.FlowNode;
import org.eclipse.bpmn2.GatewayDirection;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.StartEvent;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.bpmn2.MyGateway;
import fr.lip6.move.processGenerator.bpmn2.MyParallelGateway;

/**
 * Cette classe se charge de séparer un diagramme d'activité en plusieurs sous diagrammes
 * nommés SESE ({@link SingleEntrySingleExit}). Les diagrammes d'activités manipulés sont des 
 * diagrammes représentés à l'aide de BPMN2.0.
 * @author Vincent
 *
 */
public class SingleEntrySingleExitManager {

	public SingleEntrySingleExitManager() {}

	/**
	 * Renvoie la liste des {@link SingleEntrySingleExit} contenus dans le process passé en paramètre.
	 * @param process le {@link BpmnProcess} à parcourir.
	 * @return une liste de SESE.
	 */
	public List<SingleEntrySingleExit> getAllSESEs(BpmnProcess process) {

		StartEvent start = null;
		EndEvent end = null;
		for (FlowElement element : process.getProcess().getFlowElements()) {
			if (element instanceof StartEvent)
				start = (StartEvent) element;
			else if (element instanceof EndEvent)
				end = (EndEvent) element;
		}
		if (start == null || end == null) {
			System.err.println("The process does not contains a StartEvent and a EndEvent.");
			return null;
		}

		return getComplexSESEs(start, end);
	}

	/**
	 * 
	 * Renvoie la liste des {@link SingleEntrySingleExit} contenus entre les deux arcs passés en paramètres.
	 * @param arc
	 * @param arcFinal
	 * @return une liste de SESE.
	 */
	private List<SingleEntrySingleExit> getComplexSESEs(FlowNode noeudDepart, FlowNode noeudArrivee) {

		List<SingleEntrySingleExit> listeFinale = new ArrayList<SingleEntrySingleExit>();
		List<SingleEntrySingleExit> listeTemp = new ArrayList<SingleEntrySingleExit>();
		
		// pour chaque arc sortant
		for (SequenceFlow arc : noeudDepart.getOutgoing()) {
			// on fait un premier parcours simple pour avoir les SESE non imbriqués
			while (arc.getTargetRef() != noeudArrivee) {
				FlowNode targetRef = arc.getTargetRef();
				// si on tombe sur une activité, alors c'est un SESE simple
				if (targetRef instanceof Activity) {
					SequenceFlow end = targetRef.getOutgoing().get(0);
					listeTemp.add(new SingleEntrySingleExit(arc, end));
					arc = end;
				}
				// si c'est une gateway, alors c'est un SESE complexe qui doit avoir un traitement particulier
				else if (targetRef instanceof MyGateway) {
					SequenceFlow end = this.getEndOfGateway((MyGateway) targetRef).getOutgoing().get(0);
					listeTemp.add(new SingleEntrySingleExit(arc, end));
					arc = end;
				}
			}
			
			// à partir de la listeTemp, pour une taille de n, on a n(n+1)/2 solutions, il faut combiner chaque SESE
			for (int i = 0; i < listeTemp.size(); i++) {
				for (int j = i; j < listeTemp.size(); j++) {
					listeFinale.add(new SingleEntrySingleExit(listeTemp.get(i).getFirst(), listeTemp.get(j).getLast()));
				}
			}

			// et enfin on peut appliquer la récursivité sur les SESE complexes
			for (SingleEntrySingleExit sese : listeTemp) {
				if (sese.isComplexe()) {
					listeFinale.addAll(this.getComplexSESEs(sese.getFirst().getTargetRef(), sese.getLast().getSourceRef()));
				}
			}
			
			// on vide la liste temporaire dans le cas où le for each a plusieurs arcs
			listeTemp.clear();
		}
		
		return listeFinale;
	}

	/**
	 * Renvoie la MyGateway convergente correspondant à celle passée en paramètre (qui doit être divergente).
	 * @param gatewayDiverging une {@link MyGateway} divergente.
	 * @return la {@link MyGateway} convergente correspondante.
	 */
	public MyGateway getEndOfGateway(MyGateway gatewayDiverging) {

		// si la gateway n'est pas diverging...
		if (gatewayDiverging.getGatewayDirection().equals(GatewayDirection.CONVERGING)) {
			System.err.println("Error the gateway parameters is converging...");
			return null;
		}
		
		// on tente de récupérer la twin
		MyGateway twin = gatewayDiverging.getTwin();
		if (twin != null && twin.getGatewayDirection().equals(GatewayDirection.CONVERGING))
			return twin;
		
		return tryToFindEndOfGateway(gatewayDiverging);
	}
	
	/**
	 * Essaie de parcourir le diagramme afin de retrouver la {@link MyGateway} correspondante.
	 * @param gatewayDiverging
	 * @return
	 */
	private MyGateway tryToFindEndOfGateway(MyGateway gatewayDiverging) {

		MyGateway gateway = null;
		// on cherche la gateway converging qui referme le chemin
		FlowNode nextNode = gatewayDiverging;
		int cpt = 0, error = 0;
		do {
			if (nextNode.getOutgoing() == null || nextNode.getOutgoing().size() == 0 || nextNode instanceof EndEvent) {
				// si on a atteint la fin on renvoie null
				return null;
			}
			nextNode = nextNode.getOutgoing().get(0).getTargetRef();
			// il faut vérifier que c'est le bon type de gateway
			if (nextNode instanceof MyGateway) {
				// il faut faire attention à ce que ca ne soit pas une autre gateway diverging (dans le cas d'un fork dans un fork typiquement)
				gateway = (MyGateway) nextNode;
				if (gateway.getGatewayDirection().equals(GatewayDirection.DIVERGING)) {
					cpt++;
				} else if (gateway.getGatewayDirection().equals(GatewayDirection.CONVERGING)) {
					cpt--;
				}

				// quand le compteur est a -1 c'est qu'on vient de passer la bonne gateway fermante
				if (cpt == -1) {
					break;
				}
			}
			error++;
		} while (error < 10000000);
		
		if (error == 10000000) {
			System.err.println("Error during the parsing of the model. The loop is probably infinite...");
			return null;
		}
		
		// on peut enfin retourner la gateway fermante
		if (nextNode instanceof MyGateway)
			return (MyGateway) nextNode;
		return null;
	}
	
	/**
	 * Renvoie la ParallelGateway convergente correspondant à celle passée en paramètre (qui doit être divergente).
	 * @param gatewayDiverging une {@link MyParallelGateway} divergente.
	 * @return la {@link MyParallelGateway} convergente correspondante.
	 */
	public MyParallelGateway getEndOfParallelGateway(MyParallelGateway gatewayDiverging) {
		return (MyParallelGateway) this.getEndOfGateway(gatewayDiverging);
	}
}
