package fr.lip6.move.processGenerator.structuralConstraint.bpmn;

import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.structuralConstraint.AbstractOclSolver;
import fr.lip6.move.processGenerator.structuralConstraint.bpmn.query.BpmnQueryReaderHelper;

/**
 * Représente le WP2 - Parallel Split.
 * @author Vincent
 *
 */
public class BpmnParallelSplit extends AbstractOclSolver {

	
	public BpmnParallelSplit() throws BpmnException {
		super();
		super.setOclQuery(BpmnQueryReaderHelper.read("parallelSplit"));
	}
}
