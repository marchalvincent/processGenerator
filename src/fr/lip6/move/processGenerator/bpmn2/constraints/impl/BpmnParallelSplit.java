package fr.lip6.move.processGenerator.bpmn2.constraints.impl;

import org.eclipse.bpmn2.GatewayDirection;
import org.eclipse.bpmn2.ParallelGateway;
import org.eclipse.bpmn2.Task;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.bpmn2.constraints.BpmnWorkflowRepresentation;
import fr.lip6.move.processGenerator.bpmn2.utils.BpmnFilter;
import fr.lip6.move.processGenerator.constraint.AbstractJavaSolver;
import fr.lip6.move.processGenerator.constraint.IWorkflowRepresentation;

/**
 * Représente le WP2 - Parallel Split.
 * 
 * @author Vincent
 * 
 */
public class BpmnParallelSplit extends AbstractJavaSolver {
	
	@Override
	public int matches(Object object) throws Exception {
		if (!(object instanceof BpmnProcess)) {
			System.err.println("Matches method : The object is not a " + BpmnProcess.class.getSimpleName() + ".");
			return 0;
		}
		
		BpmnProcess process = (BpmnProcess) object;
		return BpmnFilter.byType(ParallelGateway.class, process.getProcess().getFlowElements(), GatewayDirection.DIVERGING)
				.size();
	}
	
	@Override
	public IWorkflowRepresentation getRepresentation() {
		BpmnWorkflowRepresentation representation = new BpmnWorkflowRepresentation();
		
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
