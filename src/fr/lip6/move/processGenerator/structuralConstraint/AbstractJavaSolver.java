package fr.lip6.move.processGenerator.structuralConstraint;


/**
 * Cette classe offre un niveau d'abstraction aux workflows patterns implémentés en Java.
 * @author Vincent
 *
 */
public abstract class AbstractJavaSolver implements IStructuralConstraint {

	@Override
	public abstract int matches(Object process) throws Exception;
	
	@Override
	public IConstraintRepresentation getRepresentation() {
		// par défaut, les contraintes n'ont pas de représentation
		return null;
	}
}
