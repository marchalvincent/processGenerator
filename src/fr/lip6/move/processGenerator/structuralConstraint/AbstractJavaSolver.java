package fr.lip6.move.processGenerator.structuralConstraint;



public abstract class AbstractJavaSolver implements IStructuralConstraint {

	@Override
	public abstract int matches(Object process) throws Exception;
}
