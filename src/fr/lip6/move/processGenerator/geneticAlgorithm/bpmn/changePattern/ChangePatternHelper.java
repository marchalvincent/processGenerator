package fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.changePattern;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.eclipse.bpmn2.Activity;
import org.eclipse.bpmn2.EndEvent;
import org.eclipse.bpmn2.ExclusiveGateway;
import org.eclipse.bpmn2.Gateway;
import org.eclipse.bpmn2.GatewayDirection;
import org.eclipse.bpmn2.InclusiveGateway;
import org.eclipse.bpmn2.ParallelGateway;
import org.eclipse.bpmn2.SequenceFlow;

import fr.lip6.move.processGenerator.Utils;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.bpmn2.utils.BpmnFilter;
import fr.lip6.move.processGenerator.geneticAlgorithm.GeneticException;

/**
 * Cette classe permet de simplifier la manipulation des {@link BpmnProcess}.
 * @author Vincent
 *
 */
public class ChangePatternHelper {

	public final static ChangePatternHelper instance = new ChangePatternHelper();
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
		List<SequenceFlow> liste = BpmnFilter.byType(SequenceFlow.class, process.getProcess().getFlowElements());

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
		List<Activity> liste = BpmnFilter.byType(Activity.class, process.getProcess().getFlowElements());

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
		List<ParallelGateway> liste = BpmnFilter.byType(ParallelGateway.class, process.getProcess().getFlowElements(), GatewayDirection.DIVERGING);

		// s'il n'y a aucune ParallelGateway...
		if (liste.isEmpty())
			throw new GeneticException("The process does not contain any ParallelGateway.");

		// on en retourne un au hasard
		return liste.get(rng.nextInt(liste.size()));
	}
	
	/**
	 * Renvoie une {@link ExclusiveGateway} ou une {@link InclusiveGateway} diverging tirée au hasard parmis celles du process passé en argument.
	 * @param process le {@link BpmnProcess} à parcourir.
	 * @param rng le {@link Random} utilisé.
	 * @return la {@link Gateway} aléatoire.
	 * @throws GeneticException si le process ne contient ni d'ExclusiveGateway, ni d'InclusiveGateway.
	 */
	public Gateway getRandomConditionalGatewayDiverging(BpmnProcess process, Random rng) throws GeneticException {

		// on récupère la liste des Gateway de condition
		List<Class<? extends Gateway>> classes = new ArrayList<>();
		classes.add(ExclusiveGateway.class);
		classes.add(InclusiveGateway.class);
		List<Gateway> liste = BpmnFilter.gatewayByType(classes, process.getProcess().getFlowElements(), GatewayDirection.DIVERGING);
		
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
		return BpmnFilter.byType(Activity.class, process.getProcess().getFlowElements()).size();
	}
	
	/**
	 * Renvoie le nombre de {@link SequenceFlow} contenues dans le process passé en paramètre.
	 * @param process le {@link Bpmnprocess}.
	 * @return int.
	 */
	public int countSequenceFlow(BpmnProcess process) {
		return BpmnFilter.byType(SequenceFlow.class, process.getProcess().getFlowElements()).size();
	}
	
	/**
	 * Renvoie le nombre de {@link ParallelGateway} contenues dans le process passé en paramètre.
	 * @param process le {@link Bpmnprocess}.
	 * @return int.
	 */
	public int countParallelGateway(BpmnProcess process) {
		int count = 0;
		List<ParallelGateway> list = BpmnFilter.byType(ParallelGateway.class, process.getProcess().getFlowElements());
		for (ParallelGateway parallelGateway : list)
			if (process.getTwin(parallelGateway.getId()) != null)
				count ++;
		return count;
	}
	
	/**
	 * Renvoie le nombre de Conditional Gateways contenues dans le process passé en paramètre.
	 * @param process le {@link Bpmnprocess}.
	 * @return int.
	 */
	public int countConditionalGateway(BpmnProcess process) {
		List<Class<? extends Gateway>> classes = new ArrayList<>();
		classes.add(ExclusiveGateway.class);
		classes.add(InclusiveGateway.class);
		return BpmnFilter.gatewayByType(classes, process.getProcess().getFlowElements()).size();
	}
	
	/**
	 * Supprime les "ParallelGateway" inutiles du process.
	 * @param process un {@link BpmnProcess}.
	 */
	private void removeUselessParallelGateway(BpmnProcess process) {

		// on récupère la liste des ParallelGateway divergentes
		ParallelGateway parallelConverging;
		List<ParallelGateway> listeDiverging = BpmnFilter.byType(ParallelGateway.class, process.getProcess().getFlowElements(), GatewayDirection.DIVERGING);

		// pour chaque ParallelGateway divergentes
		for (ParallelGateway parallelGateway : listeDiverging) {

			// on cherche la parallelGateway converging qui referme le chemin
			parallelConverging = (ParallelGateway) GatewayManager.instance.findTwinGateway(process, parallelGateway);
			// petite vérification, si on a une parallel sans twin, cela peut être un nouveau thread dans le process
			if (parallelConverging == null) {
				this.cleanNewThread(process, parallelGateway);
				continue;
			}
			
			// si la parallelGateway possède plusieurs chemins
			if (parallelGateway.getOutgoing().size() > 1) {

				List<SequenceFlow> listeToRemove = new ArrayList<SequenceFlow>();
				// on parcours chaque arc sortant et on regarde s'il n'arrive pas directement à la ParallelGateway converging
				for (SequenceFlow sequenceFlow : parallelGateway.getOutgoing()) {

					if (sequenceFlow.getTargetRef() == parallelConverging) {
						// on ajoute l'arc dans la liste de ceux a supprimer
						listeToRemove.add(sequenceFlow);
						// ici on fait un break sinon on risque de supprimer trop d'arc et couper le process en deux...
						break;
					}
				}
				
				// puis on fait les removes nécessaires
				for (SequenceFlow sequenceFlow : listeToRemove)
					process.removeSequenceFlow(sequenceFlow);
			} // fin de : si la parallelGateway divergente a plusieurs sorties
			
			// si la parallelGateway n'a qu'une seule sortie, on peut simplifier
			if (parallelGateway.getOutgoing().size() == 1) {
				
				if (parallelConverging.getIncoming().size() != 1) 
					System.err.println(this.getClass().getSimpleName() + " : The parallelGateway have more than 1 incoming sequence flow.");
				
				// ici on peut faire la suppression des 2 parallelGateway
				parallelGateway.getIncoming().get(0).setTargetRef(parallelGateway.getOutgoing().get(0).getTargetRef());
				process.removeSequenceFlow(parallelGateway.getOutgoing().get(0));
				process.removeFlowNode(parallelGateway);
				
				// si notre parallel gateway est en fin de processus
				if (parallelConverging.getOutgoing().size() == 0) {
					process.removeSequenceFlow(parallelConverging.getIncoming().get(0));
				} else {
					parallelConverging.getIncoming().get(0).setTargetRef(parallelConverging.getOutgoing().get(0).getTargetRef());
					process.removeSequenceFlow(parallelConverging.getOutgoing().get(0));
				}
				process.removeFlowNode(parallelConverging);
				
			} // fin de : si la parallelGateway divergente n'a qu'une sortie
		} // fin de : pour chaque parallelGateway divergente
	}

	/**
	 * Cette méthode se charge de supprimer les parallel gateway de nouveaux threads qui ne servent 
	 * à rien ou qui ne sont pas logiques.
	 * @param process le {@link BpmnProcess} à nettoyer.
	 * @param parallelGateway la {@link ParallelGateway} du nouveau thread.
	 */
	private void cleanNewThread(BpmnProcess process, ParallelGateway parallelGateway) {
		
		// si la gateway a plusieurs arcs sortants
		if (parallelGateway.getOutgoing().size() > 1) {
			
			/*
			 * on vérifie le cas où un des arcs arrive directement à un EndEvent.
			 * C'est illogique, car le process se finira automatiquement. Il faut dont le supprimer.
			 */
			List<SequenceFlow> listToRemove = new ArrayList<>();
			for (SequenceFlow sequenceAfter : parallelGateway.getOutgoing()) {
				if (sequenceAfter.getTargetRef() instanceof EndEvent)
					listToRemove.add(sequenceAfter);
			}
			
			// on supprime tous les arcs et EndEvent
			for (SequenceFlow sequenceFlow : listToRemove) {
				process.removeFlowNode(sequenceFlow.getTargetRef());
				process.removeSequenceFlow(sequenceFlow);
			}
		}
		
		// si la gateway n'a qu'une seule sortie, on peut la supprimer
		if (parallelGateway.getOutgoing().size() == 1) {
			
			if (parallelGateway.getIncoming().size() != 1)
				System.err.println("Error, the parallel gateway has not only 1 incoming edge : " + parallelGateway.getIncoming().size());
			
			SequenceFlow sequenceBefore = parallelGateway.getIncoming().get(0);
			SequenceFlow sequenceAfter = parallelGateway.getOutgoing().get(0);
			
			sequenceBefore.setTargetRef(sequenceAfter.getTargetRef());
			process.removeSequenceFlow(sequenceAfter);
			process.removeFlowNode(parallelGateway);
		}
	}
	/**
	 * Supprime les "ConditionalGateway" inutiles du process.
	 * @param process un {@link BpmnProcess}.
	 */
	private void removeUselessConditionalGateway(BpmnProcess process) {

		// on récupère la liste des ExclusiveGateway divergentes
		Gateway gatewayConverging = null;

		List<Class<? extends Gateway>> classes = new ArrayList<>();
		classes.add(ExclusiveGateway.class);
		classes.add(InclusiveGateway.class);
		
		List<Gateway> listeDivergente = BpmnFilter.gatewayByType(classes, process.getProcess().getFlowElements(), GatewayDirection.DIVERGING);
		
		// pour chaque conditional gateway divergentes
		for (Gateway gatewayDiverging : listeDivergente) {
			boolean removed = false;

			// on cherche la Gateway converging qui referme le chemin
			gatewayConverging = GatewayManager.instance.findTwinGateway(process, gatewayDiverging);
			if (gatewayConverging == null)
				continue;
			
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
				if (gatewayConverging.getIncoming().size() != 1) {
					System.err.println(this.getClass().getSimpleName() + " : The Gateway converging does not contains only 1 incoming sequence flow. Number : " +
							gatewayConverging.getIncoming().size() + ", id : " + gatewayConverging.getId());
					try {
						if (Utils.DEBUG)
							process.save(System.getProperty("user.home") + "/workspace/processGenerator/gen/bug.bpmn");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				// ici on peut faire la suppression des 2 Gateway
				gatewayDiverging.getIncoming().get(0).setTargetRef(gatewayDiverging.getOutgoing().get(0).getTargetRef());
				process.removeSequenceFlow(gatewayDiverging.getOutgoing().get(0));
				process.removeFlowNode(gatewayDiverging);
				
				// si la gateway est en fin de processus
				if (gatewayConverging.getOutgoing().size() == 0) {
					process.removeSequenceFlow(gatewayConverging.getIncoming().get(0));
				} else {
					gatewayConverging.getIncoming().get(0).setTargetRef(gatewayConverging.getOutgoing().get(0).getTargetRef());
					process.removeSequenceFlow(gatewayConverging.getOutgoing().get(0));
				}
				process.removeFlowNode(gatewayConverging);
				
				removed = true;
			} // fin de : si l'ExclusiveGateway divergente n'a qu'une sortie
			
			// et on supprime les loop potentielles si on n'a encore rien supprimé
			if (!removed)
				this.cleanPotentialUselessLoop(process, gatewayDiverging, gatewayConverging);
			
		} // fin de : pour chaque ExclusiveGateway divergente
	}
	
	/**
	 * Supprime l'enventuelle boucle vide correspondant aux deux gateways passées en paramètre.
	 * @param process le {@link BpmnProcess}.
	 * @param gatewayDiverging la {@link Gateway} divergente.
	 * @param gatewayConverging la {@link Gateway} convergente.
	 */
	private void cleanPotentialUselessLoop(BpmnProcess process, Gateway gatewayDiverging, Gateway gatewayConverging) {

		List<SequenceFlow> listToRemove = new ArrayList<>();
		
		// on récupère les arcs vides allant du merge vers la decision
		for (SequenceFlow sequence : gatewayConverging.getOutgoing()) {
			if (sequence.getTargetRef().equals(gatewayDiverging))
				listToRemove.add(sequence);
		}
		
		// si on en a n >= 2, alors on peut en supprimer n - 1
		if (listToRemove.size() >= 2) {
			for (int i = 0; i < (listToRemove.size() - 1); i++) {
				process.removeSequenceFlow(listToRemove.get(i));
			}
		}
		
		// ici, soit il nous reste 1 arc vide (merge->decision), soit on n'en a pas.
		if (!listToRemove.isEmpty()) {
			// on récupère le dernier arc vide (merge->decision)
			SequenceFlow mergeToDecisionEmpty = listToRemove.get(listToRemove.size() - 1);
			
			// et on cherche s'il n'y a pas d'arc vide (decision->merge)
			SequenceFlow decisionToMergeEmpty = null;
			for (SequenceFlow sequence : gatewayDiverging.getOutgoing()) {
				if (sequence.getTargetRef().equals(gatewayConverging)) {
					decisionToMergeEmpty = sequence;
				}
			}
			
			// si on a trouvé un arc vide, alors on peut simplifier le tout
			if (decisionToMergeEmpty != null) {
				
				// dans un premier temps on va compter le nombre de branche qu'a cette boucle
				int nbBranches = gatewayConverging.getOutgoing().size() + gatewayDiverging.getOutgoing().size() - 1;
				
				if (nbBranches == 1)
					System.err.println("Warning, the loop contains only " + nbBranches + " different path(s).");
				// si on a 2 branches, on peut tout enlever car elles sont vides
				else if (nbBranches == 2) {
					
					// on récupère les arcs avant et après la loop
					List<SequenceFlow> liste = gatewayConverging.getIncoming();
					if (liste.size() != 2) {
						System.err.println("Warning, the gateway converging does not contain 2 incoming edges. Number : " + 
								liste.size() + ", id : " + gatewayConverging.getId());
					}
					SequenceFlow edgeBefore = null;
					for (SequenceFlow sequenceFlow : liste) {
						if (sequenceFlow != decisionToMergeEmpty)
							edgeBefore = sequenceFlow;
					}
					
					liste = gatewayDiverging.getOutgoing();
					if (liste.size() != 2)
						System.err.println("Warning, the gateway diverging does not contain 2 outgoings edges. Number : " + 
								liste.size() + ", id : " + gatewayDiverging.getId());

					SequenceFlow edgeAfter = null;
					for (SequenceFlow sequenceFlow : liste) {
						if (sequenceFlow != decisionToMergeEmpty)
							edgeAfter = sequenceFlow;
					}
					
					// on peut faire la suppression si on a trouvé les arcs
					if (edgeBefore != null && edgeAfter != null) {
						edgeBefore.setTargetRef(edgeAfter.getTargetRef());

						process.removeSequenceFlow(decisionToMergeEmpty);
						process.removeSequenceFlow(mergeToDecisionEmpty);
						process.removeSequenceFlow(edgeAfter);
						process.removeFlowNode(gatewayConverging);
						process.removeFlowNode(gatewayDiverging);
					} else {
						System.err.println("Warning, we can't remove the useless loop.");
					}
				} // fin de : si on a 2 branches
				// si on a trois branches on ne supprime qu'un arc
				else if (nbBranches == 3) {
					// attention a bien supprimer l'arc (decision->merge) car l'autre pourrait générer un deadlock
					process.removeSequenceFlow(decisionToMergeEmpty);
				}
				
			}
		}
	}
}
