package fr.lip6.move.processGenerator.structuralConstraint.bpmn;

import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.structuralConstraint.AbstractOclSolver;
import fr.lip6.move.processGenerator.structuralConstraint.bpmn.query.BpmnQueryReaderHelper;

/**
 * Ce workflow pattern représente deux tâches consécutives.
 * @author Vincent
 *
 */
public class BpmnSequence extends AbstractOclSolver {

	public BpmnSequence() {
		super();
		try {
			super.setOclQuery(BpmnQueryReaderHelper.read("sequence"));
		} catch (BpmnException e) {
			e.printStackTrace();
		}
	}
}
