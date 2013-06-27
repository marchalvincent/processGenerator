package fr.lip6.move.processGenerator.structuralConstraint.bpmn;

import org.eclipse.bpmn2.ExclusiveGateway;
import org.eclipse.bpmn2.InclusiveGateway;
import org.eclipse.bpmn2.Task;

import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.structuralConstraint.AbstractOclSolver;
import fr.lip6.move.processGenerator.structuralConstraint.IConstraintRepresentation;

/**
 * Représente le WP6 - Multi Choice.
 * @author Vincent
 *
 */
public class BpmnMultiChoice extends AbstractOclSolver {

	public BpmnMultiChoice() throws BpmnException {
		super();
		StringBuilder sb = new StringBuilder();
		sb.append("InclusiveGateway.allInstances()->select(");
		sb.append(	"gate : InclusiveGateway | gate.gatewayDirection = GatewayDirection::Diverging");
		sb.append(")->size()");
		super.setOclQuery(sb.toString());
	}

	@Override
	public IConstraintRepresentation getRepresentation() {
		ConstraintRepresentation representation = new ConstraintRepresentation();

		// on construit les noeuds 
		InclusiveGateway choice = representation.buildInclusiveGatewayDiverging();
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
