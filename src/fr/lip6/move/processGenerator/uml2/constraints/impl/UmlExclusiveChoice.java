package fr.lip6.move.processGenerator.uml2.constraints.impl;

import fr.lip6.move.processGenerator.constraint.IWorkflowRepresentation;
import fr.lip6.move.processGenerator.uml2.constraints.AbstractUmlOclSolver;
import fr.lip6.move.processGenerator.uml2.constraints.UmlWorkflowRepresentation;


public class UmlExclusiveChoice extends AbstractUmlOclSolver {
	
	public UmlExclusiveChoice() {
		super();
		// TODO
		super.setOclQuery("ForkNode.allInstances()->size()");
	}
	
	@Override
	public IWorkflowRepresentation getRepresentation() {
		UmlWorkflowRepresentation representation = new UmlWorkflowRepresentation();
		//TODO
		return representation;
	}
}
