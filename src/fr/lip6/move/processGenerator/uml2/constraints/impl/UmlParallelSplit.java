package fr.lip6.move.processGenerator.uml2.constraints.impl;

import org.eclipse.uml2.uml.Action;
import org.eclipse.uml2.uml.ForkNode;
import org.eclipse.uml2.uml.JoinNode;
import fr.lip6.move.processGenerator.constraint.AbstractJavaSolver;
import fr.lip6.move.processGenerator.constraint.IWorkflowRepresentation;
import fr.lip6.move.processGenerator.uml2.UmlProcess;
import fr.lip6.move.processGenerator.uml2.constraints.UmlWorkflowRepresentation;
import fr.lip6.move.processGenerator.uml2.utils.UmlFilter;

/**
 * Représente le WP2 - Parallel Split.
 * 
 * @author Vincent
 * 
 */
public class UmlParallelSplit extends AbstractJavaSolver {
	
	@Override
	public int matches(Object object) throws Exception {
		if (!(object instanceof UmlProcess)) {
			System.err.println("Matches method : The object is not an " + UmlProcess.class.getSimpleName() + ".");
			return 0;
		}
		UmlProcess process = (UmlProcess) object;
		return UmlFilter.byType(ForkNode.class, process.getActivity().getNodes()).size();
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
