package fr.lip6.move.processGenerator.structuralConstraint.bpmn;

import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.structuralConstraint.AbstractOclSolver;
import fr.lip6.move.processGenerator.structuralConstraint.bpmn.query.BpmnQueryReaderHelper;

/**
 * Représente le WP1 - Sequence.
 * @author Vincent
 *
 */
public class BpmnSequence extends AbstractOclSolver {

	public BpmnSequence() throws BpmnException {
		super();
		super.setOclQuery(BpmnQueryReaderHelper.read("sequence"));
	}
}
