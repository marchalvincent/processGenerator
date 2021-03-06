package fr.lip6.move.processGenerator.bpmn2.constraints.impl;

import org.eclipse.bpmn2.Task;
import fr.lip6.move.processGenerator.bpmn2.constraints.AbstractBpmnOclSolver;
import fr.lip6.move.processGenerator.bpmn2.constraints.BpmnWorkflowRepresentation;
import fr.lip6.move.processGenerator.constraint.IWorkflowRepresentation;

/**
 * Représente le WP1 - Sequence.
 * 
 * @author Vincent
 * @deprecated {@link BpmnSequence} est plus performante.
 */
public class BpmnSequenceOcl extends AbstractBpmnOclSolver {
	
	public BpmnSequenceOcl() {
		super();
		StringBuilder sb = new StringBuilder();
		sb.append("Activity.allInstances()->select(");
		sb.append("activity : Activity | activity.outgoing->exists(");
		sb.append("sequence : SequenceFlow | sequence.targetRef.oclIsKindOf(Activity)");
		sb.append(")");
		sb.append(")->size()");
		super.setOclQuery(sb.toString());
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
