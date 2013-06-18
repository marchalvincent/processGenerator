package fr.lip6.move.processGenerator.structuralConstraint.bpmn;

import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.structuralConstraint.AbstractOclSolver;
import fr.lip6.move.processGenerator.structuralConstraint.bpmn.query.BpmnQueryReaderHelper;

/**
 * Représente le WP3 - Synchronisation.
 * @author Vincent
 *
 */
public class BpmnSynchronization extends AbstractOclSolver {

	public BpmnSynchronization() throws BpmnException {
		super();
		super.setOclQuery(BpmnQueryReaderHelper.read("synchronization"));
	}
}
