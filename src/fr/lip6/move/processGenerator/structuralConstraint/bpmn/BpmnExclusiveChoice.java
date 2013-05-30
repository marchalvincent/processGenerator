package fr.lip6.move.processGenerator.structuralConstraint.bpmn;

import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.structuralConstraint.AbstractOclSolver;
import fr.lip6.move.processGenerator.structuralConstraint.bpmn.query.BpmnQueryReaderHelper;


public class BpmnExclusiveChoice extends AbstractOclSolver {

	public BpmnExclusiveChoice() {
		super();
		try {
			super.setOclQuery(BpmnQueryReaderHelper.read("exclusiveChoice"));
		} catch (BpmnException e) {
			e.printStackTrace();
		}
	}
}
