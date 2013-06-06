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
			return oldProcess;
		}
		
		SequenceFlow ancienArc = null;
		try {
			ancienArc = ChangePatternHelper.getInstance().getRandomSequenceFlow(process, rng);
		} catch (GeneticException e) {
			// ici on n'a trouvé aucun arc
			return oldProcess;
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
