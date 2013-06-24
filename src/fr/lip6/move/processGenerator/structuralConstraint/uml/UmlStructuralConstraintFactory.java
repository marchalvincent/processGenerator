package fr.lip6.move.processGenerator.structuralConstraint.uml;

import fr.lip6.move.processGenerator.structuralConstraint.AbstractStructuralConstraintFactory;
import fr.lip6.move.processGenerator.structuralConstraint.IStructuralConstraint;


public class UmlStructuralConstraintFactory extends AbstractStructuralConstraintFactory {

	public static final UmlStructuralConstraintFactory instance = new UmlStructuralConstraintFactory();
	private UmlStructuralConstraintFactory() {}
	
	@Override
	public IStructuralConstraint newManualOclConstraint(String query) {
		return null;
	}

	@Override
	public IStructuralConstraint newElementConstraint(Object data) throws Exception {
		return null;
	}
}
