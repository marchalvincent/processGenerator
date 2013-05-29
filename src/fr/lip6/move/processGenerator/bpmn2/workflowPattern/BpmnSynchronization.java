package fr.lip6.move.processGenerator.bpmn2.workflowPattern;

import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.bpmn2.workflowPattern.query.QueryReaderHelper;


public class BpmnSynchronization extends AbstractBpmnWorkflowPattern {

	public BpmnSynchronization(BpmnProcess process) {
		super(process);
		try {
			super.setOclQuery(QueryReaderHelper.read("synchronization"));
		} catch (BpmnException e) {
			e.printStackTrace();
		}
	}
}
