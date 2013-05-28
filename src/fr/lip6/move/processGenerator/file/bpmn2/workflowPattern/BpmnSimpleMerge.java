package fr.lip6.move.processGenerator.file.bpmn2.workflowPattern;

import fr.lip6.move.processGenerator.file.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.file.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.file.bpmn2.workflowPattern.query.QueryReaderHelper;


public class BpmnSimpleMerge extends AbstractBpmnWorkflowPattern {

	public BpmnSimpleMerge(BpmnProcess process) {

		super(process);
		try {
			super.setOclQuery(QueryReaderHelper.read("simpleMerge"));
		} catch (BpmnException e) {
			e.printStackTrace();
		}
	}
}
