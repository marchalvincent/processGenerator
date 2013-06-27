package fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.changePattern;

import java.util.List;
import java.util.Random;

import org.eclipse.bpmn2.Activity;
import org.eclipse.bpmn2.ExclusiveGateway;
import org.eclipse.bpmn2.SequenceFlow;

import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.geneticAlgorithm.GeneticException;
import fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.AbstractBpmnChangePattern;
import fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.IBpmnChangePattern;
import fr.lip6.move.processGenerator.structuralConstraint.StructuralConstraintChecker;


public class BpmnLoopInsert extends AbstractBpmnChangePattern implements IBpmnChangePattern {

	@Override
	public BpmnProcess apply(BpmnProcess oldProcess, Random rng, List<StructuralConstraintChecker> structuralConstraints) {

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

		// on récupère les arc arrivant et partant de cette activity
		List<SequenceFlow> sequencesIn = activity.getIncoming();
		List<SequenceFlow> sequencesOut = activity.getOutgoing();
		if (sequencesIn.size() != 1)
			System.err.println(getClass().getSimpleName() + " : The number of incoming sequenceFlows is not correct : " 
					+ sequencesIn.size() + ". " + activity.getClass());
		if (sequencesOut.size() > 1)
			System.err.println(getClass().getSimpleName() + " : The number of outgoing sequenceFlows is not correct : " 
					+ sequencesIn.size() + ". " + activity.getClass());

		/*
		 * pour des raisons de simplicité dans les diagrammes, on ne faire pas l'insertion de 
		 * boucle lorsque l'activité est en fin de process (sans arc sortant)
		 */
		if (sequencesOut.size() == 0)
			return process;

		SequenceFlow arcIn = sequencesIn.get(0);
		SequenceFlow arcOut = sequencesOut.get(0);

		// on créé les nouveaux noeuds (XOR split et XOR merge)
		ExclusiveGateway merge = process.buildExclusiveGatewayConverging();
		ExclusiveGateway choice = process.buildExclusiveGatewayDiverging();

		process.linkGateways(merge, choice);

		// puis on créé les arcs
		process.buildSequenceFlow(merge, activity);
		process.buildSequenceFlow(activity, choice);
		process.buildSequenceFlow(choice, merge);

		// et on modifie les anciens arcs
		arcIn.setTargetRef(merge);
		arcOut.setSourceRef(choice);

		return process;
	}
}
