package fr.lip6.move.processGenerator.structuralConstraint.bpmn;

import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.structuralConstraint.AbstractOclSolver;
import fr.lip6.move.processGenerator.structuralConstraint.bpmn.query.BpmnQueryReaderHelper;

/**
 * Représente le WP5 - Simple Merge.
 * @author Vincent
 *
 */
public class BpmnSimpleMerge extends AbstractOclSolver {

	public BpmnSimpleMerge() throws BpmnException {
		super();
		super.setOclQuery(BpmnQueryReaderHelper.read("simpleMerge"));
	}
}
