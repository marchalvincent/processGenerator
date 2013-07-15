package fr.lip6.move.processGenerator.uml2.constraints.impl;

import org.eclipse.uml2.uml.Action;
import fr.lip6.move.processGenerator.constraint.IWorkflowRepresentation;
import fr.lip6.move.processGenerator.uml2.constraints.AbstractUmlOclSolver;
import fr.lip6.move.processGenerator.uml2.constraints.UmlWorkflowRepresentation;

/**
 * Représente le WP1 - Sequence.
 * 
 * @author Vincent
 * @deprecated {@link UmlSequence} est plus performante.
 */
public class UmlSequenceOcl extends AbstractUmlOclSolver {
	
	public UmlSequenceOcl() {
		super();
		StringBuilder sb = new StringBuilder();
		sb.append("Action.allInstances()->select(");
		sb.append("node : Action | node.outgoing->exists(");
		sb.append("edge : ActivityEdge | edge.target.oclIsKindOf(Action)");
		sb.append(")");
		sb.append(")->size()");
		super.setOclQuery(sb.toString());
	}
	
	@Override
	public IWorkflowRepresentation getRepresentation() {
		UmlWorkflowRepresentation representation = new UmlWorkflowRepresentation();
		
		// on construit 2 noeuds executables
		Action a = representation.buildAction();
		Action b = representation.buildAction();
		
		// puis l'arc entre les deux
		representation.buildControlFlow(a, b);
		
		// et enfin on set le début et la fin de la représentation.
		representation.setBegin(a);
		representation.setEnd(b);
		
		return representation;
	}
}
