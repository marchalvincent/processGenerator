package fr.lip6.move.processGenerator.structuralConstraint.bpmn;

import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.structuralConstraint.AbstractOclSolver;
import fr.lip6.move.processGenerator.structuralConstraint.bpmn.query.BpmnQueryReaderHelper;


public class BpmnSimpleMerge extends AbstractOclSolver {

	public BpmnSimpleMerge() {
		super();
		try {
			super.setOclQuery(BpmnQueryReaderHelper.read("simpleMerge"));
		} catch (BpmnException e) {
			e.printStackTrace();
		}
	}
}
