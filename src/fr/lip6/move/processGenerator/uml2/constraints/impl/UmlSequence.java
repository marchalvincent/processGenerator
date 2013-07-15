package fr.lip6.move.processGenerator.uml2.constraints.impl;

import java.util.List;
import org.eclipse.uml2.uml.Action;
import fr.lip6.move.processGenerator.constraint.AbstractJavaSolver;
import fr.lip6.move.processGenerator.constraint.IWorkflowRepresentation;
import fr.lip6.move.processGenerator.uml2.UmlProcess;
import fr.lip6.move.processGenerator.uml2.constraints.UmlWorkflowRepresentation;
import fr.lip6.move.processGenerator.uml2.utils.UmlFilter;

/**
 * Représente le WP1 - Sequence.
 * 
 * @author Vincent
 * 
 */
public class UmlSequence extends AbstractJavaSolver {
	
	@Override
	public int matches(Object object) throws Exception {
		if (!(object instanceof UmlProcess)) {
			System.err.println("Matches method : The object is not a " + UmlProcess.class.getSimpleName() + ".");
			return 0;
		}
		UmlProcess process = (UmlProcess) object;
		
		int count = 0;
		List<Action> list = UmlFilter.byType(Action.class, process.getActivity().getNodes());
		for (Action action : list) {
			try {
				if (action.getOutgoings().get(0).getTarget() instanceof Action)
					count++;
			} catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
				System.err.println(e.getClass().getSimpleName() + " : " + UmlSequence.class.getSimpleName() + ", method matches.");
			}
		}
		return count;
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
