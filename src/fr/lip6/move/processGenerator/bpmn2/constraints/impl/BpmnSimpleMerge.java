package fr.lip6.move.processGenerator.bpmn2.constraints.impl;

import org.eclipse.bpmn2.ExclusiveGateway;
import org.eclipse.bpmn2.GatewayDirection;
import org.eclipse.bpmn2.Task;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.bpmn2.constraints.BpmnWorkflowRepresentation;
import fr.lip6.move.processGenerator.bpmn2.utils.BpmnFilter;
import fr.lip6.move.processGenerator.constraint.AbstractJavaSolver;
import fr.lip6.move.processGenerator.constraint.IWorkflowRepresentation;

/**
 * Représente le WP5 - Simple Merge.
 * 
 * @author Vincent
 * 
 */
public class BpmnSimpleMerge extends AbstractJavaSolver {
	
	@Override
	public int matches(Object object) throws Exception {
		if (!(object instanceof BpmnProcess)) {
			System.err.println("Matches method : The object is not a " + BpmnProcess.class.getSimpleName() + ".");
			return 0;
		}
		BpmnProcess process = (BpmnProcess) object;
		return BpmnFilter.byType(ExclusiveGateway.class, process.getProcess().getFlowElements(), GatewayDirection.CONVERGING)
				.size();
	}
	
	@Override
	public IWorkflowRepresentation getRepresentation() {
		BpmnWorkflowRepresentation representation = new BpmnWorkflowRepresentation();
		
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
