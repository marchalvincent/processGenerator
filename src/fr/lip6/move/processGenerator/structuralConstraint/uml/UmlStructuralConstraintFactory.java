package fr.lip6.move.processGenerator.structuralConstraint.uml;

import fr.lip6.move.processGenerator.IEnumElement;
import fr.lip6.move.processGenerator.structuralConstraint.AbstractStructuralConstraintFactory;
import fr.lip6.move.processGenerator.structuralConstraint.IStructuralConstraint;

/**
 * Représente la factory des contraintes structurelles pour les fichiers de type UML2.0.
 * @author Vincent
 *
 */
public class UmlStructuralConstraintFactory extends AbstractStructuralConstraintFactory {

	public static final UmlStructuralConstraintFactory instance = new UmlStructuralConstraintFactory();
	private UmlStructuralConstraintFactory() {}
	
	@Override
	public IStructuralConstraint newManualOclConstraint(String query) {
		return null; //TODO
	}

	@Override
	public IStructuralConstraint newElementConstraint(IEnumElement data) throws Exception {
		return null; //TODO
	}
}
