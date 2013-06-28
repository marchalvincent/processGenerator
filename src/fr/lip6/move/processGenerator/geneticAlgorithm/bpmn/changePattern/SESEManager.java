package fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.changePattern;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eclipse.bpmn2.ExclusiveGateway;
import org.eclipse.bpmn2.FlowNode;
import org.eclipse.bpmn2.Gateway;
import org.eclipse.bpmn2.GatewayDirection;
import org.eclipse.bpmn2.InclusiveGateway;
import org.eclipse.bpmn2.ParallelGateway;
import org.eclipse.bpmn2.SequenceFlow;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.bpmn2.jung.JungEdge;
import fr.lip6.move.processGenerator.bpmn2.jung.JungProcess;
import fr.lip6.move.processGenerator.bpmn2.jung.JungVertex;
import fr.lip6.move.processGenerator.bpmn2.utils.Filter;

/**
 * Cette classe se charge de séparer un diagramme d'activité en plusieurs sous diagrammes
 * nommés SESE ({@link SingleEntrySingleExit}). Les diagrammes d'activités manipulés sont des 
 * diagrammes représentés à l'aide de BPMN2.0.
 * @author Vincent
 *
 */
public class SESEManager {

	public static SESEManager instance = new SESEManager();
	private SESEManager() {}

	/**
	 * Renvoie la {@link Gateway} jumelle correspondant à celle passée en paramètre.
	 * @param process le {@link BpmnProcess} contenant les Gateway.
	 * @param gateway la {@link Gateway} dont on cherche la jumelle.
	 * @return {@link Gateway} la twin si trouvée, null sinon.
	 */
	public Gateway findTwinGateway(BpmnProcess process, Gateway gateway) {

		// on tente de récupérer la twin par le process
		Gateway twin = process.getTwin(gateway.getId());
		if (twin != null) {
			if (twin.getGatewayDirection().equals(gateway.getGatewayDirection())) {
				System.err.println("Warning, the gateway and his twin have se same gateway direction.");
			}
			return twin;
		}
		
		// ces 3 lignes de code peuvent etre supprimées, elles étaient temporaires
		boolean bool = false;
		if (bool)
			return null;
		
		// sinon on récupère les gateways potentielles
		List<Gateway> potentialsCandidats = this.getPotentialsCandidats(process, gateway);
		
		// on cherche la gateway parmis les candidats
		twin = searchBestCandidat(process, gateway, potentialsCandidats);
		if (twin != null)
			return twin;
		
		// attention, ce code ne détecte pas les boucles
		// TODO détection des boucles
		return null;
	}
	

	/**
	 * Renvoie la liste des gateways succeptibles d'être la "twin" de la gateway passée en paramètre.
	 * @param process
	 * @param gateway
	 * @return
	 */
	private List<Gateway> getPotentialsCandidats(BpmnProcess process, Gateway gateway) {

		List<Gateway> candidats = new ArrayList<>();
		
		// on récupère la direction
		GatewayDirection direction;
		if (gateway.getGatewayDirection().equals(GatewayDirection.DIVERGING))
			direction = GatewayDirection.CONVERGING;
		else 
			direction = GatewayDirection.DIVERGING;
		
		// si c'est une parallel, on ne cherche que les parallel
		List<? extends Gateway> list, list2;
		if (gateway instanceof ParallelGateway) {
			list = Filter.byType(ParallelGateway.class, process.getProcess().getFlowElements(), direction);
			list2 = Collections.emptyList();
		} else {
			// les exclusives gateways
			list = Filter.byType(ExclusiveGateway.class, process.getProcess().getFlowElements(), direction);
			// et les inclusives gateways
			list2 = Filter.byType(InclusiveGateway.class, process.getProcess().getFlowElements(), direction);
		}
		
		// on peut enlever celles qui sont déjà liées
		// clean de la premiere liste
		List<Gateway> listToRemove = new ArrayList<>();
		for (Gateway g : list)
			if (process.getTwin(g.getId()) != null)
				listToRemove.add(g);
		for (Gateway remove : listToRemove)
			list.remove(remove);
		
		// clean de la deuxieme liste
		listToRemove.clear();
		for (Gateway g : list2)
			if (process.getTwin(g.getId()) != null)
				listToRemove.add(g);
		for (Gateway remove : listToRemove)
			list2.remove(remove);
		
		candidats.addAll(list);
		candidats.addAll(list2);
		return candidats;
	}

	/**
	 * Cherche parmis les candidats la gateway correspondant à la "twin" de celle passée en paramètre.
	 * @param process le {@link BpmnProcess}.
	 * @param gateway la {@link Gateway} dont on cherche la twin.
	 * @param potentialsCandidats une {@link List} de {@link Gateway} potentiellement twin.
	 * @return {@link Gateway} la twin si elle est trouvée, null sinon.
	 */
	private Gateway searchBestCandidat(BpmnProcess process, Gateway gateway, List<Gateway> potentialsCandidats) {

		boolean isDiverging = gateway.getGatewayDirection().equals(GatewayDirection.DIVERGING);

		Gateway twin = null;
		int scoreTwin = Integer.MAX_VALUE;
		
		JungProcess jung = new JungProcess(process);
		DijkstraShortestPath<JungVertex, JungEdge> algo = new DijkstraShortestPath<JungVertex, JungEdge>(jung.getGraph());
		
		// pour chaque candidat, on fait une évaluation
		for (Gateway candidat : potentialsCandidats) {
			boolean save = true;
			int score = 0;

			// on vérifie que pour chaque arc sortant/entrant (selon la direction de la gateway), on a un chemin
			List<SequenceFlow> sequences = isDiverging ? gateway.getOutgoing() : gateway.getIncoming();
			
			for (SequenceFlow seq : sequences) {
				// il est possible que l'arc soit directement lié au candidat
				FlowNode node = isDiverging ? seq.getTargetRef() : seq.getSourceRef();
				if (node == candidat) {
					score++;
				} 
				// sinon on fait le plus court chemin avec Dijkstra
				else {
					JungVertex v1 = jung.getVertex(node.getId());
					JungVertex v2 = jung.getVertex(candidat.getId());
					
					// si notre gateway est diverging, alors on cherche dans le sens v1 -> v2, sinon on cherche dans le sens v2 -> v1
					List<JungEdge> path = isDiverging ? algo.getPath(v1, v2) : algo.getPath(v2, v1);
					
					// si un chemin n'arrive pas au candidat on abandonne
					if (path.isEmpty()) {
						save = false;
						break;
					} 
					// sinon on peut ajouter le score
					else {
						score += path.size() + 1;
					}
				}
			}
			
			// dans ce cas, on enregistre le candidat avec son score
			if (save) {
				if (scoreTwin > score) {
					twin = candidat;
					scoreTwin = score;
				}
			}
		}
		return twin;
	}
}
