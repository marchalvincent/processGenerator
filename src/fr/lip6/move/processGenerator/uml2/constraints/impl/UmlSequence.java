package fr.lip6.move.processGenerator.uml2.constraints.impl;

import org.eclipse.uml2.uml.ExecutableNode;
import fr.lip6.move.processGenerator.constraint.IWorkflowRepresentation;
import fr.lip6.move.processGenerator.uml2.UmlException;
import fr.lip6.move.processGenerator.uml2.constraints.AbstractUmlOclSolver;
import fr.lip6.move.processGenerator.uml2.constraints.UmlWorkflowRepresentation;

/**
 * Représente le WP1 - Sequence.
 * 
 * @author Vincent
 * 
 */
public class UmlSequence extends AbstractUmlOclSolver {
	
	public UmlSequence() throws UmlException {
		super();
		StringBuilder sb = new StringBuilder();
		sb.append("ExecutableNode.allInstances()->select(");
		sb.append("node : ExecutableNode | node.outgoing->exists(");
		sb.append("edge : ActivityEdge | edge.target.oclIsKindOf(ExecutableNode)");
		sb.append(")");
		sb.append(")->size()");
		super.setOclQuery(sb.toString());
	}
	
	@Override
	public IWorkflowRepresentation getRepresentation() {
		UmlWorkflowRepresentation representation = new UmlWorkflowRepresentation();
		
		// on construit 2 noeuds executables
		ExecutableNode a = representation.buildExecutableNode();
		ExecutableNode b = representation.buildExecutableNode();
		
		// puis l'arc entre les deux
		representation.buildControlFlow(a, b);
		
		// et enfin on set le début et la fin de la représentation.s
		representation.setBegin(a);
		representation.setEnd(b);
		
		return representation;
	}
}
