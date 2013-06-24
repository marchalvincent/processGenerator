package fr.lip6.move.processGenerator.structuralConstraint;



public abstract class AbstractJavaSolver implements IStructuralConstraint {

	@Override
	public abstract int matches(Object process) throws Exception;
	
	@Override
	public IConstraintRepresentation getRepresentation() {
		// par défaut, les contraintes n'ont pas de représentation
		return null;
	}
}
