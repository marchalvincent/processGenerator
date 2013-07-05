package fr.lip6.move.processGenerator.bpmn2.constraints;

import java.util.List;
import org.eclipse.bpmn2.Bpmn2Factory;
import org.eclipse.bpmn2.EndEvent;
import org.eclipse.bpmn2.EventDefinition;
import org.eclipse.bpmn2.ParallelGateway;
import org.eclipse.bpmn2.Task;
import org.eclipse.bpmn2.TerminateEventDefinition;
import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.bpmn2.utils.BpmnFilter;
import fr.lip6.move.processGenerator.constraint.AbstractJavaSolver;
import fr.lip6.move.processGenerator.constraint.IWorkflowRepresentation;

/**
 * Représente le WP43 - Explicite Termination.
 * 
 * @author Vincent
 * 
 */
public class BpmnExpliciteTermination extends AbstractJavaSolver {
	
	public BpmnExpliciteTermination() throws BpmnException {
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
		// on compte le nombre de EndEvent qui ont une TerminateEventDefinition
		List<EndEvent> list = BpmnFilter.byType(EndEvent.class, process.getProcess().getFlowElements());
		for (EndEvent endEvent : list) {
			for (EventDefinition eventDef : endEvent.getEventDefinitions()) {
				if (eventDef instanceof TerminateEventDefinition) {
					count++;
					break;
				}
			}
		}
		
		return count;
	}
	
	@Override
	public IWorkflowRepresentation getRepresentation() {
		BpmnWorkflowRepresentation representation = new BpmnWorkflowRepresentation();
		
		// on construit les noeuds
		ParallelGateway gateway = representation.buildParallelGatewayDiverging();
		Task a = representation.buildTask();
		EndEvent end = representation.buildEndEvent();
		end.getEventDefinitions().add(Bpmn2Factory.eINSTANCE.createTerminateEventDefinition());
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
