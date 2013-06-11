package fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.changePattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.eclipse.bpmn2.Activity;
import org.eclipse.bpmn2.ExclusiveGateway;
import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.FlowNode;
import org.eclipse.bpmn2.GatewayDirection;
import org.eclipse.bpmn2.ParallelGateway;
import org.eclipse.bpmn2.SequenceFlow;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
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
	 * Renvoie une {@link ParallelGateway} diverging tirée au hasard parmis celles du process passé en argument.
	 * @param process le {@link BpmnProcess} à parcourir.
	 * @param rng le {@link Random} utilisé.
	 * @return la {@link ParallelGateway} aléatoire.
	 * @throws GeneticException si le process ne contient aucune ParallelGateway.
	 */
	public ParallelGateway getRandomParallelGatewayDiverging(BpmnProcess process, Random rng) throws GeneticException {

		// on récupère la liste des ParallelGateway
		List<ParallelGateway> liste = new ArrayList<ParallelGateway>();
		for (FlowElement elem : process.getProcess().getFlowElements()) {
			if (elem instanceof ParallelGateway && ((ParallelGateway) elem).getGatewayDirection().equals(GatewayDirection.DIVERGING))
				liste.add((ParallelGateway) elem);
		}

		// s'il n'y a aucune ParallelGateway...
		if (liste.isEmpty())
			throw new GeneticException("The process does not contain any ParallelGateway.");

		// on en retourne un au hasard
		return liste.get(rng.nextInt(liste.size()));
	}

	/**
	 * Supprime tous les noeuds et arcs inutiles. Typiquement : deux chemins parallèles vides.
	 * @param process
	 */
	public void cleanProcess(BpmnProcess process) {
		this.removeUselessParallelGateway(process);
		this.removeUselessExclusiveGateway(process);
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
	 * Renvoie le nombre de {@link ParallelGateway} contenues dans le process passé en paramètre.
	 * @param process le {@link Bpmnprocess}.
	 * @return int.
	 */
	public int countParallelGateway(BpmnProcess process) {
		int count = 0;
		for (FlowElement element : process.getProcess().getFlowElements()) {
			if (element instanceof ParallelGateway)
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
		ParallelGateway parallel = null;
		List<ParallelGateway> listeDiverging = new ArrayList<ParallelGateway>();
		for (FlowElement elem : process.getProcess().getFlowElements()) {
			parallel = this.isParallelGateway(elem, GatewayDirection.DIVERGING);
			if (parallel != null)
				listeDiverging.add(parallel);
		}

		// pour chaque ParallelGateway divergentes
		for (ParallelGateway parallelGateway : listeDiverging) {

			// si la parallelGateway possède plusieurs chemins
			if (parallelGateway.getOutgoing().size() > 1) {

				List<SequenceFlow> listeToRemove = new ArrayList<SequenceFlow>();
				// on parcours chaque arc sortant et on regarde s'il n'arrive pas directement à la ParallelGateway converging
				for (SequenceFlow sequenceFlow : parallelGateway.getOutgoing()) {

					FlowNode target = sequenceFlow.getTargetRef();
					parallel = this.isParallelGateway(target, GatewayDirection.CONVERGING);
					if (parallel != null) {
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
				// on cherche la parallelGateway converging qui referme le chemin
				SingleEntrySingleExitManager seseManager = new SingleEntrySingleExitManager();
				parallel = seseManager.getEndOfParallelGateway(parallelGateway);
				
				// petite vérification
				if (parallel == null) 
					return;
				if (parallel.getIncoming().size() != 1) 
					System.err.println(this.getClass().getSimpleName() + " : The parallelGateway have more than 1 incoming sequence flow.");
				
				// ici on peut faire la suppression des 2 parallelGateway
				parallelGateway.getIncoming().get(0).setTargetRef(parallelGateway.getOutgoing().get(0).getTargetRef());
				process.removeSequenceFlow(parallelGateway.getOutgoing().get(0));
				process.removeFlowNode(parallelGateway);
				
				parallel.getIncoming().get(0).setTargetRef(parallel.getOutgoing().get(0).getTargetRef());
				process.removeSequenceFlow(parallel.getOutgoing().get(0));
				process.removeFlowNode(parallel);
				
			} // fin de : si la parallelGateway divergente n'a qu'une sortie
		} // fin de : pour chaque parallelGateway divergente
	}

	/**
	 * Renvoie un {@link ParallelGateway} si le FlowElement est une instance de ParallelGateway avec 
	 * la direction passée en paramètre. Renvoie null sinon.
	 * @param elem le {@link FlowElement} l'élément à tester.
	 * @param direction la {@link GatewayDirection}.
	 * @return {@link ParallelGateway} ou null.
	 */
	private ParallelGateway isParallelGateway(FlowElement elem, GatewayDirection direction) {
		if (elem instanceof ParallelGateway) {
			if (((ParallelGateway) elem).getGatewayDirection().equals(direction)) {
				return (ParallelGateway) elem;
			}
		}
		return null;
	}

	/**
	 * Supprime les "ExclusiveGateway" inutiles du process.
	 * @param process un {@link BpmnProcess}.
	 */
	private void removeUselessExclusiveGateway(BpmnProcess process) {

		// on récupère la liste des ExclusiveGateway divergentes
		ExclusiveGateway exclusive = null;
		List<ExclusiveGateway> listeDivergente = new ArrayList<ExclusiveGateway>();
		for (FlowElement elem : process.getProcess().getFlowElements()) {
			exclusive = this.isExclusiveGateway(elem, GatewayDirection.DIVERGING);
			if (exclusive != null)
				listeDivergente.add(exclusive);
		}

		// pour chaque ExclusiveGateway divergentes
		for (ExclusiveGateway exclusiveGateway : listeDivergente) {

			// si l'ExclusiveGateway n'a qu'une seule sortie, on peut simplifier
			if (exclusiveGateway.getOutgoing().size() == 1) {
				// on cherche l'ExclusiveGateway converging qui referme le chemin
				SingleEntrySingleExitManager seseManager = new SingleEntrySingleExitManager();
				exclusive = seseManager.getEndOfExclusiveGateway(exclusiveGateway);
				
				// petites vérifications
				if (exclusive == null)
					return;
				if (exclusive.getIncoming().size() != 1) 
					System.err.println(this.getClass().getSimpleName() + " : The ExclusiveGateway have more than 1 incoming sequence flow.");
				
				// ici on peut faire la suppression des 2 ExclusiveGateway
				exclusiveGateway.getIncoming().get(0).setTargetRef(exclusiveGateway.getOutgoing().get(0).getTargetRef());
				process.removeSequenceFlow(exclusiveGateway.getOutgoing().get(0));
				process.removeFlowNode(exclusiveGateway);
				
				exclusive.getIncoming().get(0).setTargetRef(exclusive.getOutgoing().get(0).getTargetRef());
				process.removeSequenceFlow(exclusive.getOutgoing().get(0));
				process.removeFlowNode(exclusive);
				
			} // fin de : si l'ExclusiveGateway divergente n'a qu'une sortie
		} // fin de : pour chaque ExclusiveGateway divergente
	}
	
	/**
	 * Renvoie un {@link ExclusiveGateway} si le FlowElement est une instance de ExclusiveGateway avec 
	 * la direction passée en paramètre. Renvoie null sinon.
	 * @param elem le {@link FlowElement} l'élément à tester.
	 * @param direction la {@link GatewayDirection}.
	 * @return {@link ExclusiveGateway} ou null.
	 */
	private ExclusiveGateway isExclusiveGateway(FlowElement elem, GatewayDirection direction) {
		if (elem != null && elem instanceof ExclusiveGateway) {
			if (((ExclusiveGateway) elem).getGatewayDirection().equals(direction)) {
				return (ExclusiveGateway) elem;
			}
		}
		return null;
	}
}
