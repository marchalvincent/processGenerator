package fr.lip6.move.processGenerator.structuralConstraint.bpmn;

import org.eclipse.bpmn2.ExclusiveGateway;
import org.eclipse.bpmn2.Task;

import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.structuralConstraint.IConstraintRepresentation;

/**
 * Représente le WP5 - Simple Merge.
 * @author Vincent
 *
 */
public class BpmnSimpleMerge extends AbstractBpmnOclSolver {

	public BpmnSimpleMerge() throws BpmnException {
		super();
		StringBuilder sb = new StringBuilder();
		sb.append("ExclusiveGateway.allInstances()->select(");
		sb.append(	"gate : ExclusiveGateway | gate.gatewayDirection = GatewayDirection::Converging");
		sb.append(")->size()");
		super.setOclQuery(sb.toString());
	}

	@Override
	public IConstraintRepresentation getRepresentation() {
		ConstraintRepresentation representation = new ConstraintRepresentation();
		
		// on construit les noeuds 
		ExclusiveGateway choice = representation.buildExclusiveGatewayDiverging();
		Task a = representation.buildTask();
		Task b = representation.buildTask();
		ExclusiveGateway merge = representation.buildExclusiveGatewayConverging();

		representation.linkGatewys(choice, merge);
		
		// puis les arcs
		representation.buildSequenceFlow(choice, a);
		representation.buildSequenceFlow(choice, b);
		representation.buildSequenceFlow(a, merge);
		representation.buildSequenceFlow(b, merge);
		
		// on définit le début et la fin
		representation.setBegin(choice);
		representation.setEnd(merge);
		
		return representation;
	}
}
