package fr.lip6.move.processGenerator.uml2.constraints;

import fr.lip6.move.processGenerator.IEnumElement;
import fr.lip6.move.processGenerator.constraint.AbstractStructuralConstraintFactory;
import fr.lip6.move.processGenerator.constraint.IStructuralConstraint;
import fr.lip6.move.processGenerator.uml2.EUmlElement;
import fr.lip6.move.processGenerator.uml2.UmlException;
import fr.lip6.move.processGenerator.uml2.constraints.impl.UmlElementConstraint;
import fr.lip6.move.processGenerator.uml2.constraints.impl.UmlManualOclConstraint;

/**
 * Représente la factory des contraintes structurelles pour les fichiers de type UML2.0.
 * 
 * @author Vincent
 * 
 */
public class UmlStructuralConstraintFactory extends AbstractStructuralConstraintFactory {
	
	public static final UmlStructuralConstraintFactory instance = new UmlStructuralConstraintFactory();
	
	private UmlStructuralConstraintFactory() {}
	
	@Override
	public IStructuralConstraint newManualOclConstraint(String query) {
		return new UmlManualOclConstraint(query);
	}
	
	@Override
	public IStructuralConstraint newElementConstraint(IEnumElement data) throws Exception {
		if (data instanceof EUmlElement) {
			return new UmlElementConstraint((EUmlElement) data);
		}
		throw new UmlException("The parameter of the newElementConstraint method is not a " + EUmlElement.class.getSimpleName()
				+ ".");
	}
}
