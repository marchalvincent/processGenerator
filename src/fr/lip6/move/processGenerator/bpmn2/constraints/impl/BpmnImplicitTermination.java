package fr.lip6.move.processGenerator.bpmn2.constraints.impl;

import java.util.List;
import org.eclipse.bpmn2.EndEvent;
import org.eclipse.bpmn2.EventDefinition;
import org.eclipse.bpmn2.ParallelGateway;
import org.eclipse.bpmn2.Task;
import org.eclipse.bpmn2.TerminateEventDefinition;
import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.bpmn2.constraints.BpmnWorkflowRepresentation;
import fr.lip6.move.processGenerator.bpmn2.utils.BpmnFilter;
import fr.lip6.move.processGenerator.constraint.AbstractJavaSolver;
import fr.lip6.move.processGenerator.constraint.IWorkflowRepresentation;

/**
 * Représente le WP11 - Implicite Termination.
 * 
 * @author Vincent
 * 
 */
public class BpmnImplicitTermination extends AbstractJavaSolver {
	
	public BpmnImplicitTermination() throws BpmnException {
		super();
	}
	
	@Override
	public int matches(Object object) throws Exception {
		
		int count = 0;
		
		if (!(object instanceof BpmnProcess)) {
			System.err.println("Error, the eObject is not a Bpmn Process.");
			return count;
		}
		
		BpmnProcess process = (BpmnProcess) object;
		// on compte le nombre de EndEvent qui n'ont pas de TerminateEventDefinition moins 1
		List<EndEvent> list = BpmnFilter.byType(EndEvent.class, process.getProcess().getFlowElements());
		boolean isTermination = false;
		for (EndEvent endEvent : list) {
			isTermination = false;
			for (EventDefinition eventDef : endEvent.getEventDefinitions()) {
				if (eventDef instanceof TerminateEventDefinition) {
					isTermination = true;
					break;
				}
			}
			if (!isTermination)
				count++;
		}
		
		// on soustrait 1 car il ne faut pas compter le EndEvent par défaut
		return Math.max(0, count - 1);
	}
	
	@Override
	public IWorkflowRepresentation getRepresentation() {
		BpmnWorkflowRepresentation representation = new BpmnWorkflowRepresentation();
		
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
