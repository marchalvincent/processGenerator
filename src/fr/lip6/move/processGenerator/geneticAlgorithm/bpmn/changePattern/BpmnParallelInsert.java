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
			return oldProcess;
		}
		
		// on considère qu'une insertion parallèle ne peut arriver qu'autour d'une Activity
		Activity activity = null;
		try {
			activity = ChangePatternHelper.getInstance().getRandomActivity(process, rng);
		} catch (GeneticException e) {
			// si on n'a pas d'activity
			return oldProcess;
		}
		
		// on créé les nouveaux noeuds
		ParallelGateway fork = process.buildParallelGatewayDiverging();
		ParallelGateway join = process.buildParallelGatewayConverging();
		Task newTask = process.buildTask();
		
		// on récupère les arc arrivant et partant de cette activity
		List<SequenceFlow> sequencesIn = activity.getIncoming();
		List<SequenceFlow> sequencesOut = activity.getOutgoing();
		if (sequencesIn.size() != 1)
			System.err.println("BpmnParallelInsert : The number of incoming sequenceFlows is not correct : " + sequencesIn.size() + "." +
					" " + activity.getClass());
		if (sequencesOut.size() != 1)
			System.err.println("BpmnParallelInsert : The number of outgoing sequenceFlows is not correct : " + sequencesIn.size() + "." +
					" " + activity.getClass());

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
