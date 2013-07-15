package fr.lip6.move.processGenerator.uml2.constraints.impl;

import org.eclipse.uml2.uml.Action;
import org.eclipse.uml2.uml.DecisionNode;
import org.eclipse.uml2.uml.MergeNode;
import fr.lip6.move.processGenerator.constraint.IWorkflowRepresentation;
import fr.lip6.move.processGenerator.uml2.constraints.AbstractUmlOclSolver;
import fr.lip6.move.processGenerator.uml2.constraints.UmlWorkflowRepresentation;

/**
 * Représente le WP4 - Exclusive Choice.
 * 
 * @author Vincent
 * @deprecated {@link UmlExclusiveChoice} est plus performante.
 */
public class UmlExclusiveChoiceOcl extends AbstractUmlOclSolver {
	
	public UmlExclusiveChoiceOcl() {
		super();
		super.setOclQuery("DecisionNode.allInstances()->size()");
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
