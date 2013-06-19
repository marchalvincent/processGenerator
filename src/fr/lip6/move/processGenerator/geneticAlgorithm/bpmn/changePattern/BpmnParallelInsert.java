package fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.changePattern;

import java.util.List;
import java.util.Random;
import org.eclipse.bpmn2.Activity;
import org.eclipse.bpmn2.ParallelGateway;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.Task;
import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.geneticAlgorithm.AbstractChangePattern;
import fr.lip6.move.processGenerator.geneticAlgorithm.GeneticException;
import fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.IBpmnChangePattern;


public class BpmnParallelInsert extends AbstractChangePattern implements IBpmnChangePattern {

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
		
		// on récupère toutes les Activities et le nombre de ParallelGateway
		int nbActivity = ChangePatternHelper.getInstance().countActivity(process);
		int nbParallel = ChangePatternHelper.getInstance().countParallelGateway(process);
		if (nbParallel % 2 != 0) {
			System.err.println("Error, the number of ParallelGateway is odd.");
			return process;
		}
		// on divise par deux le nombre de parallelGateway car il y a une ouvrante et une fermante.
		nbParallel = nbParallel / 2;
		
		if ((nbActivity + nbParallel) == 0)
			return process;
		
		// on fait un random équitable pour savoir si on applique le parallel sur une Activity ou sur une parallelGateway deja existante
		int[] tableau = new int[nbActivity + nbParallel];
		for (int i = 0; i < nbActivity; i++) {
			tableau[i] = 0;
		}
		for (int i = nbActivity; i < nbActivity + nbParallel; i++) {
			tableau[i] = 1;
		}

		// on procède au tirage au sort. 0 pour une insertion sur une ativity, 1 pour une insertion sur une parallel.
		int tirage = tableau[rng.nextInt(tableau.length)];
		if (tirage == 0) {
			return applyOnActivity(process, rng);
		} else {
			return applyOnParallel(process, rng);
		}
	}
	
	/**
	 * Applique la modification génétique d'une insertion en parallèle sur une ParallelGateway déjà existente. 
	 * Cette modification va entrainer l'ajout d'un chemin supplémentaire à la parallelGateway tirée au sort.
	 * @param process le {@link BpmnProcess} à modifier.
	 * @param rng une source de {@link Random}.
	 * @return le {@link BpmnProcess} modifié.
	 */
	private BpmnProcess applyOnParallel(BpmnProcess process, Random rng) {

		// on récupère une ParallelGateway diverging au hasard
		ParallelGateway parallelDiverging = null;
		try {
			parallelDiverging = ChangePatternHelper.getInstance().getRandomParallelGatewayDiverging(process, rng);
		} catch (GeneticException e) {
			// si on n'a pas d'activity
			return process;
		}
		
		// on récupère la parallelConverging
		ParallelGateway parallelConverging = (ParallelGateway) SESEManager.instance.findTwinGateway(process, parallelDiverging);
		
		// on créé la nouvelle tache
		Task newTask = process.buildTask();
		
		// et enfin les nouveaux arcs
		process.buildSequenceFlow(parallelDiverging, newTask);
		process.buildSequenceFlow(newTask, parallelConverging);
		
		return process;
	}

	/**
	 * Applique la modification génétique d'une insertion en parallèle autour d'une activité. Cette modification va entrainer
	 * une création de deux {@link ParallelGateway} qui seront fixées avant et après cette activité.
	 * @param process le {@link BpmnProcess} à modifier.
	 * @param rng une source de {@link Random}.
	 * @return le {@link BpmnProcess} modifié.
	 */
	private BpmnProcess applyOnActivity(BpmnProcess process, Random rng) {

		// on récupère une activité au hasard
		Activity activity = null;
		try {
			activity = ChangePatternHelper.getInstance().getRandomActivity(process, rng);
		} catch (GeneticException e) {
			// si on n'a pas d'activity
			return process;
		}
		
		// on créé les nouveaux noeuds
		ParallelGateway fork = process.buildParallelGatewayDiverging();
		ParallelGateway join = process.buildParallelGatewayConverging();
		Task newTask = process.buildTask();

		process.linkGateways(fork, join);
		
		// on récupère les arc arrivant et partant de cette activity
		List<SequenceFlow> sequencesIn = activity.getIncoming();
		List<SequenceFlow> sequencesOut = activity.getOutgoing();
		if (sequencesIn.size() != 1)
			System.err.println(getClass().getSimpleName() + " : The number of incoming sequenceFlows is not correct : " 
					+ sequencesIn.size() + ". " + activity.getClass());
		if (sequencesOut.size() != 1)
			System.err.println(getClass().getSimpleName() + " : The number of outgoing sequenceFlows is not correct : " 
					+ sequencesIn.size() + ". " + activity.getClass());

		SequenceFlow arcIn = sequencesIn.get(0);
		SequenceFlow arcOut = sequencesOut.get(0);
		
		// modification des arcs
		arcIn.setTargetRef(fork);
		process.buildSequenceFlow(fork, activity);
		process.buildSequenceFlow(fork, newTask);
		process.buildSequenceFlow(newTask, join);
		process.buildSequenceFlow(activity, join);
		arcOut.setSourceRef(join);
		
		return process;
	}
}
