package fr.lip6.move.processGenerator.file.bpmn2.workflowPattern;

import fr.lip6.move.processGenerator.file.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.file.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.file.bpmn2.workflowPattern.query.QueryReaderHelper;

/**
 * Ce workflow pattern représente deux tâches consécutives.
 * @author Vincent
 *
 */
public class BpmnSequence extends AbstractBpmnWorkflowPattern {

	public BpmnSequence(BpmnProcess process) {
		super(process);
		try {
			super.setOclQuery(QueryReaderHelper.read("sequence"));
		} catch (BpmnException e) {
			e.printStackTrace();
		}
	}
}
