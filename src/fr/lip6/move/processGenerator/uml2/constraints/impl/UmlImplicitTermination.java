package fr.lip6.move.processGenerator.uml2.constraints.impl;

import org.eclipse.uml2.uml.Action;
import org.eclipse.uml2.uml.FlowFinalNode;
import org.eclipse.uml2.uml.ForkNode;
import fr.lip6.move.processGenerator.constraint.AbstractJavaSolver;
import fr.lip6.move.processGenerator.constraint.IWorkflowRepresentation;
import fr.lip6.move.processGenerator.uml2.UmlProcess;
import fr.lip6.move.processGenerator.uml2.constraints.UmlWorkflowRepresentation;
import fr.lip6.move.processGenerator.uml2.utils.UmlFilter;


public class UmlImplicitTermination extends AbstractJavaSolver {
	
	@Override
	public int matches(Object object) throws Exception {
		if (!(object instanceof UmlProcess)) {
			System.err.println("Matches method : The object is not an " + UmlProcess.class.getSimpleName() + ".");
			return 0;
		}
		UmlProcess process = (UmlProcess) object;
		int count = UmlFilter.byType(FlowFinalNode.class, process.getActivity().getNodes()).size();
		return Math.max(0, count - 1);
	}
	
	@Override
	public IWorkflowRepresentation getRepresentation() {
		UmlWorkflowRepresentation representation = new UmlWorkflowRepresentation();
		
		// on build les noeuds
		ForkNode fork = representation.buildForkNode();
		Action a = representation.buildAction();
		FlowFinalNode finalNode = representation.buildFlowFinalNode();
		
		// puis les arcs
		representation.buildControlFlow(fork, a);
		representation.buildControlFlow(a, finalNode);
		
		// et enfin on set le début et la fin de cette représentation
		representation.setBegin(fork);
		representation.setEnd(fork);
		
		return representation;
	}
}
