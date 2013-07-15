package fr.lip6.move.processGenerator.uml2.constraints.impl;

import org.eclipse.uml2.uml.Action;
import org.eclipse.uml2.uml.ForkNode;
import org.eclipse.uml2.uml.JoinNode;
import fr.lip6.move.processGenerator.constraint.IWorkflowRepresentation;
import fr.lip6.move.processGenerator.uml2.constraints.AbstractUmlOclSolver;
import fr.lip6.move.processGenerator.uml2.constraints.UmlWorkflowRepresentation;

/**
 * Représente le WP3 - Synchronization.
 * 
 * @author Vincent
 * @deprecated {@link UmlSynchronization} est plus performante.
 */
public class UmlSynchronizationOcl extends AbstractUmlOclSolver {
	
	public UmlSynchronizationOcl() {
		super();
		super.setOclQuery("JoinNode.allInstances()->size()");
	}
	
	@Override
	public IWorkflowRepresentation getRepresentation() {
		UmlWorkflowRepresentation representation = new UmlWorkflowRepresentation();

		// on construit les noeuds 
		ForkNode fork = representation.buildForkNode();
		Action a = representation.buildAction();
		Action b = representation.buildAction();
		JoinNode join = representation.buildJoinNode();
		
		representation.linkControlNodes(fork, join);
		
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
