package fr.lip6.move.processGenerator.bpmn2.constraints;

import org.eclipse.bpmn2.ParallelGateway;
import org.eclipse.bpmn2.Task;

import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.constraint.IWorkflowRepresentation;

/**
 * Représente le WP2 - Parallel Split.
 * @author Vincent
 *
 */
public class BpmnParallelSplit extends AbstractBpmnOclSolver {

	
	public BpmnParallelSplit() throws BpmnException {
		super();
		StringBuilder sb = new StringBuilder();
		sb.append("ParallelGateway.allInstances()->select(");
		sb.append(	"gate : ParallelGateway | gate.gatewayDirection = GatewayDirection::Diverging");
		sb.append(")->size()");
		super.setOclQuery(sb.toString());
	}

	@Override
	public IWorkflowRepresentation getRepresentation() {
		WorkflowRepresentation representation = new WorkflowRepresentation();
		
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
