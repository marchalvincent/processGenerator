package fr.lip6.move.processGenerator.uml2.constraints.impl;

import org.eclipse.uml2.uml.Action;
import org.eclipse.uml2.uml.DecisionNode;
import org.eclipse.uml2.uml.MergeNode;
import fr.lip6.move.processGenerator.constraint.AbstractJavaSolver;
import fr.lip6.move.processGenerator.constraint.IWorkflowRepresentation;
import fr.lip6.move.processGenerator.uml2.UmlProcess;
import fr.lip6.move.processGenerator.uml2.constraints.UmlWorkflowRepresentation;
import fr.lip6.move.processGenerator.uml2.utils.UmlFilter;

/**
 * Représente le WP4 - Exclusive Choice.
 * 
 * @author Vincent
 * 
 */
public class UmlExclusiveChoice extends AbstractJavaSolver {
	
	@Override
	public int matches(Object object) throws Exception {
		if (!(object instanceof UmlProcess)) {
			System.err.println("Matches method : The object is not an " + UmlProcess.class.getSimpleName() + ".");
			return 0;
		}
		UmlProcess process = (UmlProcess) object;
		return UmlFilter.byType(DecisionNode.class, process.getActivity().getNodes()).size();
	}
	
	@Override
	public IWorkflowRepresentation getRepresentation() {
		UmlWorkflowRepresentation representation = new UmlWorkflowRepresentation();
		
		// les noeuds
		DecisionNode decision = representation.buildDecisionNode();
		Action a = representation.buildAction();
		Action b = representation.buildAction();
		MergeNode merge = representation.buildMergeNode();
		
		representation.linkControlNodes(decision, merge);
		
		// les arcs
		representation.buildControlFlow(decision, a);
		representation.buildControlFlow(decision, b);
		representation.buildControlFlow(a, merge);
		representation.buildControlFlow(b, merge);
		
		// le début et la fin
		representation.setBegin(decision);
		representation.setEnd(merge);
		
		return representation;
	}
}
