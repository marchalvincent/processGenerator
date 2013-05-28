package fr.lip6.move.processGenerator.file.bpmn2.workflowPattern;

import fr.lip6.move.processGenerator.file.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.file.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.file.bpmn2.workflowPattern.query.QueryReaderHelper;


public class BpmnParallelSplit extends AbstractBpmnWorkflowPattern {

	
	public BpmnParallelSplit(BpmnProcess process) {
		super(process);
		try {
			super.setOclQuery(QueryReaderHelper.read("parallelSplit"));
		} catch (BpmnException e) {
			e.printStackTrace();
		}
	}
}
