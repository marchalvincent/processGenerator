package fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.changePattern;

import java.util.Random;
import org.eclipse.bpmn2.ExclusiveGateway;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.Task;
import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.geneticAlgorithm.AbstractChangePattern;
import fr.lip6.move.processGenerator.geneticAlgorithm.GeneticException;
import fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.IBpmnChangePattern;


public class BpmnConditionalInsert extends AbstractChangePattern implements IBpmnChangePattern {

	@Override
	public BpmnProcess apply(BpmnProcess oldProcess, Random rng) {

		BpmnProcess process = null;
		try {
			process = new BpmnProcess(oldProcess);
		} catch (BpmnException e) {
			// impossible de copier...
			System.err.println(getClass().getSimpleName() + e.getMessage());
			return oldProcess;
		}

		// on récupère toutes le nombre de séquence et le nombre d'exclusive gateway
		int nbSequence = ChangePatternHelper.getInstance().countSequenceFlow(process);
		int nbExclusive = ChangePatternHelper.getInstance().countExclusiveGateway(process);
		if (nbExclusive % 2 != 0) {
			System.err.println("Error, the number of ExclusiveGateway is odd.");
			return process;
		}
		
		// on divise par deux le nombre d'ExclusiveGateway car il y a une ouvrante et une fermante.
		nbExclusive = nbExclusive / 2;
		
		if ((nbSequence + nbExclusive) == 0)
			return process;
		
		// on fait un random équitable pour savoir si on applique la condition sur un arc ou sur une ExclusiveGateway deja existante
		int[] tableau = new int[nbSequence + nbExclusive];
		for (int i = 0; i < nbSequence; i++) {
			tableau[i] = 0;
		}
		for (int i = nbSequence; i < nbSequence + nbExclusive; i++) {
			tableau[i] = 1;
		}

		// on procède au tirage au sort. 0 pour une insertion sur un arc, 1 pour une insertion sur une ExclusiveGateway.
		int tirage = tableau[rng.nextInt(tableau.length)];
		if (tirage == 0) {
			return applyOnSequenceFlow(process, rng);
		} else {
			return applyOnExclusive(process, rng);
		}
	}
	
	/**
	 * Applique la modification génétique d'une insertion conditionelle sur une ExclusiveGateway déjà existente. 
	 * Cette modification va entrainer l'ajout d'un chemin supplémentaire à l'ExclusiveGateway tirée au sort.
	 * @param process le {@link BpmnProcess} à modifier.
	 * @param rng une source de {@link Random}.
	 * @return le {@link BpmnProcess} modifié.
	 */
	private BpmnProcess applyOnExclusive(BpmnProcess process, Random rng) {

		// on récupère une ExclusiveGateway diverging au hasard
		ExclusiveGateway exclusiveDiverging = null;
		try {
			exclusiveDiverging = ChangePatternHelper.getInstance().getRandomExclusiveGatewayDiverging(process, rng);
		} catch (GeneticException e) {
			// si on n'a pas d'activity
			return process;
		}
		
		// on récupère l'exclusive converging
		SingleEntrySingleExitManager seseManager = new SingleEntrySingleExitManager();
		ExclusiveGateway exclusiveConverging = seseManager.getEndOfExclusiveGateway(exclusiveDiverging);
		
		// on créé la nouvelle tache
		Task newTask = process.buildTask();
		
		// et enfin les nouveaux arcs
		process.buildSequenceFlow(exclusiveDiverging, newTask);
		process.buildSequenceFlow(newTask, exclusiveConverging);
		
		return process;
	}

	/**
	 * Applique la modification génétique d'une insertion conditionelle autour d'un arc ({@link SequenceFlow}. Cette modification va entrainer
	 * une création de deux {@link ExclusiveGateway} qui seront fixées avant et après cet arc.
	 * @param process le {@link BpmnProcess} à modifier.
	 * @param rng une source de {@link Random}.
	 * @return le {@link BpmnProcess} modifié.
	 */
	private BpmnProcess applyOnSequenceFlow(BpmnProcess process, Random rng) {

		SequenceFlow ancienArc = null;
		try {
			ancienArc = ChangePatternHelper.getInstance().getRandomSequenceFlow(process, rng);
		} catch (GeneticException e) {
			// ici on n'a trouvé aucun arc (ce n'est pas normal, il doit toujours en avoir)
			System.err.println(getClass().getSimpleName() + e.getMessage());
			return process;
		}

		// les nouveaux noeuds
		ExclusiveGateway xorSplit = process.buildExclusiveGatewayDiverging();
		ExclusiveGateway xorJoin = process.buildExclusiveGatewayConverging();
		Task task = process.buildTask();
		
		// les nouveaux arcs
		process.buildSequenceFlow(xorSplit, task);
		process.buildSequenceFlow(task, xorJoin);
		process.buildSequenceFlow(xorSplit, xorJoin);
		process.buildSequenceFlow(xorJoin, ancienArc.getTargetRef());
		
		// l'ancien arc a maintenant une nouvelle destination
		ancienArc.setTargetRef(xorSplit);
		
		return process;
	}
}
