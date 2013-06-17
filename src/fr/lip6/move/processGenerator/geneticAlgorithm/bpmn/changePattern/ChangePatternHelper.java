package fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.changePattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.eclipse.bpmn2.Activity;
import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.GatewayDirection;
import org.eclipse.bpmn2.SequenceFlow;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.bpmn2.MyExclusiveGateway;
import fr.lip6.move.processGenerator.bpmn2.MyGateway;
import fr.lip6.move.processGenerator.bpmn2.MyInclusiveGateway;
import fr.lip6.move.processGenerator.bpmn2.MyParallelGateway;
import fr.lip6.move.processGenerator.geneticAlgorithm.GeneticException;

/**
 * Cette classe permet de simplifier la manipulation des {@link BpmnProcess}. Par exemple pour rechercher
 * une activité aléatoirement.
 * @author Vincent
 *
 */
public class ChangePatternHelper {

	private final static ChangePatternHelper instance = new ChangePatternHelper();
	public static ChangePatternHelper getInstance() {
		return instance;
	}
	private ChangePatternHelper() {}

	/**
	 * Renvoie un {@link SequenceFlow} tiré au hasard parmis ceux du process passé en argument.
	 * @param process le {@link BpmnProcess} à parcourir.
	 * @param rng le {@link Random} utilisé.
	 * @return le {@link SequenceFlow} aléatoire.
	 * @throws GeneticException si le process ne contient aucun SequenceFlow.
	 */
	public SequenceFlow getRandomSequenceFlow(BpmnProcess process, Random rng) throws GeneticException {

		// on récupère la liste des sequence flow
		List<SequenceFlow> liste = new ArrayList<SequenceFlow>();
		for (FlowElement elem : process.getProcess().getFlowElements()) {
			if (elem instanceof SequenceFlow)
				liste.add((SequenceFlow) elem);
		}

		// s'il n'y a aucun arc (cela ne devrait jamais arriver !)
		if (liste.isEmpty())
			throw new GeneticException("The process does not contain any SequenceFlow.");

		// on en retourne un au hasard
		return liste.get(rng.nextInt(liste.size()));
	}

	/**
	 * Renvoie une {@link Activity} tirée au hasard parmis celles du process passé en argument.
	 * @param process le {@link BpmnProcess} à parcourir.
	 * @param rng le {@link Random} utilisé.
	 * @return l'{@link Activity} aléatoire.
	 * @throws GeneticException si le process ne contient aucune activité.
	 */
	public Activity getRandomActivity(BpmnProcess process, Random rng) throws GeneticException {

		// on récupère la liste des activité
		List<Activity> liste = new ArrayList<Activity>();
		for (FlowElement elem : process.getProcess().getFlowElements()) {
			if (elem instanceof Activity)
				liste.add((Activity) elem);
		}

		// s'il n'y a aucune activité...
		if (liste.isEmpty())
			throw new GeneticException("The process does not contain any Activity.");

		// on en retourne un au hasard
		return liste.get(rng.nextInt(liste.size()));
	}
	
	/**
	 * Renvoie une {@link MyParallelGateway} diverging tirée au hasard parmis celles du process passé en argument.
	 * @param process le {@link BpmnProcess} à parcourir.
	 * @param rng le {@link Random} utilisé.
	 * @return la {@link MyParallelGateway} aléatoire.
	 * @throws GeneticException si le process ne contient aucune ParallelGateway.
	 */
	public MyParallelGateway getRandomParallelGatewayDiverging(BpmnProcess process, Random rng) throws GeneticException {

		// on récupère la liste des ParallelGateway
		List<MyParallelGateway> liste = new ArrayList<MyParallelGateway>();
		for (FlowElement elem : process.getProcess().getFlowElements()) {
			if (elem instanceof MyParallelGateway && ((MyParallelGateway) elem).getGatewayDirection().equals(GatewayDirection.DIVERGING))
				liste.add((MyParallelGateway) elem);
		}

		// s'il n'y a aucune ParallelGateway...
		if (liste.isEmpty())
			throw new GeneticException("The process does not contain any ParallelGateway.");

		// on en retourne un au hasard
		return liste.get(rng.nextInt(liste.size()));
	}
	
	/**
	 * Renvoie une {@link MyExclusiveGateway} ou une {@link MyInclusiveGateway} diverging tirée au hasard parmis celles du process passé en argument.
	 * @param process le {@link BpmnProcess} à parcourir.
	 * @param rng le {@link Random} utilisé.
	 * @return la {@link MyGateway} aléatoire.
	 * @throws GeneticException si le process ne contient ni d'ExclusiveGateway, ni d'InclusiveGateway.
	 */
	public MyGateway getRandomConditionalGatewayDiverging(BpmnProcess process, Random rng) throws GeneticException {

		// on récupère la liste des Gateway de condition
		List<MyGateway> liste = new ArrayList<MyGateway>();
		for (FlowElement elem : process.getProcess().getFlowElements()) {
			if (elem instanceof MyExclusiveGateway || elem instanceof MyInclusiveGateway)
				if (((MyGateway) elem).getGatewayDirection().equals(GatewayDirection.DIVERGING))
					liste.add((MyGateway) elem);
		}

		// s'il n'y a aucune Gateway...
		if (liste.isEmpty())
			throw new GeneticException("The process does not contain any conditional Gateway.");

		// on en retourne un au hasard
		return liste.get(rng.nextInt(liste.size()));
	}

	/**
	 * Supprime tous les noeuds et arcs inutiles. Typiquement : deux chemins parallèles vides.
	 * @param process
	 */
	public void cleanProcess(BpmnProcess process) {
		this.removeUselessParallelGateway(process);
		this.removeUselessConditionalGateway(process);
	}

	/**
	 * Renvoie le nombre d'{@link Activity} contenues dans le process passé en paramètre.
	 * @param process le {@link Bpmnprocess}.
	 * @return int.
	 */
	public int countActivity(BpmnProcess process) {
		int count = 0;
		for (FlowElement element : process.getProcess().getFlowElements()) {
			if (element instanceof Activity)
				count ++;
		}
		return count;
	}
	
	/**
	 * Renvoie le nombre de {@link SequenceFlow} contenues dans le process passé en paramètre.
	 * @param process le {@link Bpmnprocess}.
	 * @return int.
	 */
	public int countSequenceFlow(BpmnProcess process) {
		int count = 0;
		for (FlowElement element : process.getProcess().getFlowElements()) {
			if (element instanceof SequenceFlow)
				count ++;
		}
		return count;
	}
	
	/**
	 * Renvoie le nombre de {@link MyParallelGateway} contenues dans le process passé en paramètre.
	 * @param process le {@link Bpmnprocess}.
	 * @return int.
	 */
	public int countParallelGateway(BpmnProcess process) {
		int count = 0;
		for (FlowElement element : process.getProcess().getFlowElements()) {
			if (element instanceof MyParallelGateway)
				count ++;
		}
		return count;
	}
	
	/**
	 * Renvoie le nombre de Conditional Gateways contenues dans le process passé en paramètre.
	 * @param process le {@link Bpmnprocess}.
	 * @return int.
	 */
	public int countConditionalGateway(BpmnProcess process) {
		int count = 0;
		for (FlowElement element : process.getProcess().getFlowElements()) {
			if (element instanceof MyExclusiveGateway || element instanceof MyInclusiveGateway)
				count ++;
		}
		return count;
	}
	
	/**
	 * Supprime les "ParallelGateway" inutiles du process.
	 * @param process un {@link BpmnProcess}.
	 */
	private void removeUselessParallelGateway(BpmnProcess process) {

		// on récupère la liste des ParallelGateway divergentes
		MyParallelGateway parallel = null, parallelConverging;
		List<MyParallelGateway> listeDiverging = new ArrayList<MyParallelGateway>();
		for (FlowElement elem : process.getProcess().getFlowElements()) {
			parallel = this.isParallelGateway(elem, GatewayDirection.DIVERGING);
			if (parallel != null)
				listeDiverging.add(parallel);
		}
		
		SingleEntrySingleExitManager seseManager = new SingleEntrySingleExitManager();

		// pour chaque ParallelGateway divergentes
		for (MyParallelGateway parallelGateway : listeDiverging) {

			// on cherche la parallelGateway converging qui referme le chemin
			parallelConverging = seseManager.getEndOfParallelGateway(parallelGateway);
			
			// si la parallelGateway possède plusieurs chemins
			if (parallelGateway.getOutgoing().size() > 1) {

				List<SequenceFlow> listeToRemove = new ArrayList<SequenceFlow>();
				// on parcours chaque arc sortant et on regarde s'il n'arrive pas directement à la ParallelGateway converging
				for (SequenceFlow sequenceFlow : parallelGateway.getOutgoing()) {

					if (parallelConverging != null && sequenceFlow.getTargetRef() == parallelConverging) {
						// on ajoute l'arc dans la liste de ceux a supprimer
						listeToRemove.add(sequenceFlow);
						// ici on fait un break sinon on risque de supprimer trop d'arc et couper le process en deux...
						break;
					}
				}
				
				// puis on fait les removes nécessaires
				for (SequenceFlow sequenceFlow : listeToRemove) {
					process.removeSequenceFlow(sequenceFlow);
				}
			} // fin de : si la parallelGateway divergente a plusieurs sorties
			
			// si la parallelGateway n'a qu'une seule sortie, on peut simplifier
			if (parallelGateway.getOutgoing().size() == 1) {
				
				// petite vérification
				if (parallelConverging == null) 
					return;
				if (parallelConverging.getIncoming().size() != 1) 
					System.err.println(this.getClass().getSimpleName() + " : The parallelGateway have more than 1 incoming sequence flow.");
				
				// ici on peut faire la suppression des 2 parallelGateway
				parallelGateway.getIncoming().get(0).setTargetRef(parallelGateway.getOutgoing().get(0).getTargetRef());
				process.removeSequenceFlow(parallelGateway.getOutgoing().get(0));
				process.removeFlowNode(parallelGateway);
				
				parallelConverging.getIncoming().get(0).setTargetRef(parallelConverging.getOutgoing().get(0).getTargetRef());
				process.removeSequenceFlow(parallelConverging.getOutgoing().get(0));
				process.removeFlowNode(parallelConverging);
				
			} // fin de : si la parallelGateway divergente n'a qu'une sortie
		} // fin de : pour chaque parallelGateway divergente
	}

	/**
	 * Renvoie un {@link MyParallelGateway} si le FlowElement est une instance de MyParallelGateway avec 
	 * la direction passée en paramètre. Renvoie null sinon.
	 * @param elem le {@link FlowElement} l'élément à tester.
	 * @param direction la {@link GatewayDirection}.
	 * @return {@link MyParallelGateway} ou null.
	 */
	private MyParallelGateway isParallelGateway(FlowElement elem, GatewayDirection direction) {
		if (elem instanceof MyParallelGateway && ((MyParallelGateway) elem).getGatewayDirection().equals(direction))
				return (MyParallelGateway) elem;
		return null;
	}

	/**
	 * Supprime les "ConditionalGateway" inutiles du process.
	 * @param process un {@link BpmnProcess}.
	 */
	private void removeUselessConditionalGateway(BpmnProcess process) {

		// on récupère la liste des ExclusiveGateway divergentes
		MyGateway conditionalDiverging = null, gatewayConverging = null;
		List<MyGateway> listeDivergente = new ArrayList<MyGateway>();
		for (FlowElement elem : process.getProcess().getFlowElements()) {
			conditionalDiverging = this.isConditionalGateway(elem, GatewayDirection.DIVERGING);
			if (conditionalDiverging != null)
				listeDivergente.add(conditionalDiverging);
		}

		SingleEntrySingleExitManager seseManager = new SingleEntrySingleExitManager();

		// pour chaque conditional gateway divergentes
		for (MyGateway gatewayDiverging : listeDivergente) {

			// on cherche la Gateway converging qui referme le chemin
			gatewayConverging = seseManager.getEndOfGateway(gatewayDiverging);
			if (gatewayConverging == null) {
				System.err.println("Error, the gateway diverging does not contains any gate converging.");
				continue;
			}
			
			// on vérifie si on a plusieurs arc vide allant de la porte diverging à la porte converging
			if (gatewayDiverging.getOutgoing().size() > 1) {
				
				// cette liste contiendra les arc éventuels à supprimer s'il y en a trop
				List<SequenceFlow> listeToRemove = new ArrayList<SequenceFlow>();
				
				for (SequenceFlow sequenceFlow : gatewayDiverging.getOutgoing()) {
					if (sequenceFlow.getTargetRef() == gatewayConverging)
						listeToRemove.add(sequenceFlow);
				}
				
				// ici si notre liste est de taille n >= 2, alors on peut supprimer n-1 arc.
				if (listeToRemove.size() >= 2) {
					for (int i = 0; i < (listeToRemove.size() - 1); i++) {
						process.removeSequenceFlow(listeToRemove.get(i));
					}
				}
				
			} // fin de : si on a plusieurs arcs partant de l'exclusive gateway diverging
			
			// si la Gateway n'a qu'une seule sortie, on peut simplifier
			if (gatewayDiverging.getOutgoing().size() == 1) {
				
				// petites vérifications
				if (gatewayConverging.getIncoming().size() != 1) 
					System.err.println(this.getClass().getSimpleName() + " : The Gateway have more than 1 incoming sequence flow.");
				
				// ici on peut faire la suppression des 2 Gateway
				gatewayDiverging.getIncoming().get(0).setTargetRef(gatewayDiverging.getOutgoing().get(0).getTargetRef());
				process.removeSequenceFlow(gatewayDiverging.getOutgoing().get(0));
				process.removeFlowNode(gatewayDiverging);
				
				gatewayConverging.getIncoming().get(0).setTargetRef(gatewayConverging.getOutgoing().get(0).getTargetRef());
				process.removeSequenceFlow(gatewayConverging.getOutgoing().get(0));
				process.removeFlowNode(gatewayConverging);
				
			} // fin de : si l'ExclusiveGateway divergente n'a qu'une sortie
			
		} // fin de : pour chaque ExclusiveGateway divergente
	}
	
	/**
	 * Renvoie une {@link MyGateway} si le FlowElement est une instance de MyExclusiveGateway ou MyInclusiveGateway avec 
	 * la direction passée en paramètre. Renvoie null sinon.
	 * @param elem le {@link FlowElement} l'élément à tester.
	 * @param direction la {@link GatewayDirection}.
	 * @return {@link MyGateway} ou null.
	 */
	private MyGateway isConditionalGateway(FlowElement elem, GatewayDirection direction) {
		if (elem != null && (elem instanceof MyExclusiveGateway || elem instanceof MyInclusiveGateway)) {
			if (((MyGateway) elem).getGatewayDirection().equals(direction)) {
				return (MyGateway) elem;
			}
		}
		return null;
	}
}
