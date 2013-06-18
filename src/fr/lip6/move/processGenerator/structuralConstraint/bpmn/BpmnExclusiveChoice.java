package fr.lip6.move.processGenerator.structuralConstraint.bpmn;

import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.structuralConstraint.AbstractOclSolver;
import fr.lip6.move.processGenerator.structuralConstraint.bpmn.query.BpmnQueryReaderHelper;

/**
 * Représente le WP4 - Exclusive Choice.
 * @author Vincent
 *
 */
public class BpmnExclusiveChoice extends AbstractOclSolver {

	public BpmnExclusiveChoice() throws BpmnException {
		super();
		super.setOclQuery(BpmnQueryReaderHelper.read("exclusiveChoice"));
	}
}
