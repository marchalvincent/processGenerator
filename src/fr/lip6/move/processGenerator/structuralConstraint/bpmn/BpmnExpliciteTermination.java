package fr.lip6.move.processGenerator.structuralConstraint.bpmn;

import org.eclipse.bpmn2.EndEvent;
import org.eclipse.bpmn2.ParallelGateway;
import org.eclipse.bpmn2.Task;

import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.structuralConstraint.AbstractOclSolver;
import fr.lip6.move.processGenerator.structuralConstraint.IConstraintRepresentation;

public class BpmnExpliciteTermination extends AbstractOclSolver {


	public BpmnExpliciteTermination() throws BpmnException {
		super();
		super.setOclQuery("EndEvent.allInstances()->size() - 1");
	}
	@Override
	public IConstraintRepresentation getRepresentation() {
		ConstraintRepresentation representation = new ConstraintRepresentation();

		// on construit les noeuds
		ParallelGateway gateway = representation.buildParallelGatewayDiverging();
		Task a = representation.buildTask();
		EndEvent end = representation.buildEndEvent();
		Task b = representation.buildTask();
		
		// puis on construit les arcs
		representation.buildSequenceFlow(gateway, a);
		representation.buildSequenceFlow(a, end);
		representation.buildSequenceFlow(gateway, b);
		
		// et enfin on set le début et la fin de cette représentation
		representation.setBegin(gateway);
		representation.setEnd(b);
		
		return representation;
	}
	
}
