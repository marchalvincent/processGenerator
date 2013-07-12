package fr.lip6.move.processGenerator.uml2.constraints.impl;

import org.eclipse.uml2.uml.ExecutableNode;
import org.eclipse.uml2.uml.ForkNode;
import org.eclipse.uml2.uml.JoinNode;
import fr.lip6.move.processGenerator.constraint.IWorkflowRepresentation;
import fr.lip6.move.processGenerator.uml2.constraints.AbstractUmlOclSolver;
import fr.lip6.move.processGenerator.uml2.constraints.UmlWorkflowRepresentation;

/**
 * Représente le WP2 - Parallel Split.
 * 
 * @author Vincent
 *
 */
public class UmlParallelSplit extends AbstractUmlOclSolver {
	
	public UmlParallelSplit() {
		super();
		super.setOclQuery("ForkNode.allInstances()->size()");
	}
	
	@Override
	public IWorkflowRepresentation getRepresentation() {
		UmlWorkflowRepresentation representation = new UmlWorkflowRepresentation();

		// on construit les noeuds 
		ForkNode fork = representation.buildForkNode();
		ExecutableNode a = representation.buildExecutableNode();
		ExecutableNode b = representation.buildExecutableNode();
		JoinNode join = representation.buildJoinNode();
		
		// puis on construit les arcs
		representation.buildControlFlow(fork, a);
		representation.buildControlFlow(fork, b);
		representation.buildControlFlow(a, join);
		representation.buildControlFlow(b, join);
		
		// et on set le début et la fin
		representation.setBegin(fork);
		representation.setEnd(join);
		
		return representation;
	}
}
