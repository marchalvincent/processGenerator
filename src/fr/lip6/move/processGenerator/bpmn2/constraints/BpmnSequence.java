package fr.lip6.move.processGenerator.bpmn2.constraints;

import org.eclipse.bpmn2.Task;

import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.constraint.IWorkflowRepresentation;

/**
 * Représente le WP1 - Sequence.
 * @author Vincent
 *
 */
public class BpmnSequence extends AbstractBpmnOclSolver {

	public BpmnSequence() throws BpmnException {
		super();
		StringBuilder sb = new StringBuilder();
		sb.append("Activity.allInstances()->select(");
		sb.append(	"activity : Activity | activity.outgoing->exists(");
		sb.append(		"sequence : SequenceFlow | sequence.targetRef.oclIsKindOf(Activity)");
		sb.append(	")");
		sb.append(")->size()");
		super.setOclQuery(sb.toString());
	}
	
	@Override
	public IWorkflowRepresentation getRepresentation() {
		WorkflowRepresentation representation = new WorkflowRepresentation();
		
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
