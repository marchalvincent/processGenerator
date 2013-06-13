package fr.lip6.move.processGenerator.structuralConstraint.bpmn;

import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.structuralConstraint.AbstractOclSolver;
import fr.lip6.move.processGenerator.structuralConstraint.bpmn.query.BpmnQueryReaderHelper;


public class BpmnMultiChoice extends AbstractOclSolver {

	public BpmnMultiChoice() throws BpmnException {
		super();
		super.setOclQuery(BpmnQueryReaderHelper.read("multiChoice"));
	}
}
