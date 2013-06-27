package fr.lip6.move.processGenerator.structuralConstraint.bpmn;

import org.eclipse.bpmn2.ParallelGateway;
import org.eclipse.bpmn2.Task;

import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.structuralConstraint.AbstractOclSolver;
import fr.lip6.move.processGenerator.structuralConstraint.IConstraintRepresentation;

public class BpmnImplicitTermination extends AbstractOclSolver {

	public BpmnImplicitTermination() throws BpmnException {
		super();
		super.setOclQuery("Activity.allInstances()->select(activity : Activity | activity.outgoing->size() = 0)->size()");
	}
	
	@Override
	public IConstraintRepresentation getRepresentation() {
		ConstraintRepresentation representation = new ConstraintRepresentation();

		// on construit les noeuds
		ParallelGateway gateway = representation.buildParallelGatewayDiverging();
		Task a = representation.buildTask();
		Task b = representation.buildTask();
		
		// puis on construit les arcs
		representation.buildSequenceFlow(gateway, a);
		representation.buildSequenceFlow(gateway, b);
		
		// et enfin on set le début et la fin de cette représentation
		representation.setBegin(gateway);
		representation.setEnd(b);
		
		return representation;
	}
	
}
