package fr.lip6.move.processGenerator.constraint;

/**
 * Cette classe offre un niveau d'abstraction aux workflows patterns implémentés en Java.
 * 
 * @author Vincent
 * 
 */
public abstract class AbstractJavaSolver implements IStructuralConstraint {
	
	@Override
	public abstract int matches(Object object) throws Exception;
	
	@Override
	public IWorkflowRepresentation getRepresentation() {
		// par défaut, les contraintes n'ont pas de représentation
		return null;
	}
}
