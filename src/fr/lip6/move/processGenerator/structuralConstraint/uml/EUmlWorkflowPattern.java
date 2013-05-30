package fr.lip6.move.processGenerator.structuralConstraint.uml;

import java.util.ArrayList;
import java.util.List;
import fr.lip6.move.processGenerator.structuralConstraint.IStructuralConstraint;


public final class EUmlWorkflowPattern {

	public static List<Class<? extends IStructuralConstraint>> patterns;
	static {
		patterns = new ArrayList<Class<? extends IStructuralConstraint>>();
		patterns.add(UmlSequence.class);
	}
}
