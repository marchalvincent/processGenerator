package fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.changePattern;

import java.util.List;
import java.util.Random;
import org.eclipse.bpmn2.Activity;
import org.eclipse.bpmn2.ExclusiveGateway;
import org.eclipse.bpmn2.SequenceFlow;
import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.geneticAlgorithm.AbstractChangePattern;
import fr.lip6.move.processGenerator.geneticAlgorithm.GeneticException;
import fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.IBpmnChangePattern;


public class BpmnLoopInsert extends AbstractChangePattern implements IBpmnChangePattern {

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

		// on récupère une activité au hasard
		Activity activity = null;
		try {
			activity = ChangePatternHelper.getInstance().getRandomActivity(process, rng);
		} catch (GeneticException e) {
			// si on n'a pas d'activity
			return process;
		}

		// on créé les nouveaux noeuds (XOR split et XOR merge)
		ExclusiveGateway merge = process.buildExclusiveGatewayConverging();
		ExclusiveGateway choice = process.buildExclusiveGatewayDiverging();

		process.linkGateways(merge, choice);

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

		// puis on créé les arcs
		process.buildSequenceFlow(merge, activity);
		process.buildSequenceFlow(activity, choice);
		process.buildSequenceFlow(choice, merge);
		
		// et on modifie les anciens arcs
		arcIn.setTargetRef(merge);
		arcOut.setSourceRef(choice);

		//TODO faire le remove, voir avec ce qui existe déjà...
		return process;
	}
}
