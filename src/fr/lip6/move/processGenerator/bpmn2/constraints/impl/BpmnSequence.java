package fr.lip6.move.processGenerator.bpmn2.constraints.impl;

import java.util.List;
import org.eclipse.bpmn2.Task;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.bpmn2.constraints.BpmnWorkflowRepresentation;
import fr.lip6.move.processGenerator.bpmn2.utils.BpmnFilter;
import fr.lip6.move.processGenerator.constraint.AbstractJavaSolver;
import fr.lip6.move.processGenerator.constraint.IWorkflowRepresentation;

/**
 * Représente le WP1 - Sequence.
 * 
 * @author Vincent
 * 
 */
public class BpmnSequence extends AbstractJavaSolver {
	
	public BpmnSequence() {
		super();
	}
	
	@Override
	public int matches(Object object) throws Exception {
		if (!(object instanceof BpmnProcess)) {
			System.err.println("Matches method : The object is not a " + BpmnProcess.class.getSimpleName() + ".");
			return 0;
		}
		
		int count = 0;
		BpmnProcess process = (BpmnProcess) object;
		
		// on récupère toutes les Tasks
		List<Task> list = BpmnFilter.byType(Task.class, process.getProcess().getFlowElements());
		for (Task task : list) {
			try {
				if (task.getOutgoing().get(0).getTargetRef() instanceof Task)
					count++;
			} catch (NullPointerException e) {
				System.err.println("NullPointerException : " + BpmnSequence.class.getSimpleName() + ", method matches.");
			}
		}
		return count;
	}
	
	@Override
	public IWorkflowRepresentation getRepresentation() {
		BpmnWorkflowRepresentation representation = new BpmnWorkflowRepresentation();
		
		// on construit les noeuds
		Task a = representation.buildTask();
		Task b = representation.buildTask();
		
		// puis les arcs
		representation.buildSequenceFlow(a, b);
		
		// on définit le début et la fin
		representation.setBegin(a);
		representation.setEnd(b);
		
		return representation;
	}
}
