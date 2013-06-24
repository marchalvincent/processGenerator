package fr.lip6.move.processGenerator.structuralConstraint.bpmn;

import org.eclipse.bpmn2.ParallelGateway;
import org.eclipse.bpmn2.Task;

import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.structuralConstraint.AbstractOclSolver;
import fr.lip6.move.processGenerator.structuralConstraint.IConstraintRepresentation;
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

	@Override
	public IConstraintRepresentation getRepresentation() {
		ConstraintRepresentation representation = new ConstraintRepresentation();
		
		// on construit les noeuds 
		ParallelGateway fork = representation.buildParallelGatewayDiverging();
		Task a = representation.buildTask();
		Task b = representation.buildTask();
		ParallelGateway join = representation.buildParallelGatewayConverging();

		representation.linkGatewys(fork, join);
		
		// puis les arcs
		representation.buildSequenceFlow(fork, a);
		representation.buildSequenceFlow(fork, b);
		representation.buildSequenceFlow(a, join);
		representation.buildSequenceFlow(b, join);
		
		// on définit le début et la fin
		representation.setBegin(fork);
		representation.setEnd(join);
		
		return representation;
	}
}
