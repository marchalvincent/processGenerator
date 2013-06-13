package fr.lip6.move.processGenerator.structuralConstraint;

import org.eclipse.emf.ecore.EObject;


public abstract class AbstractJavaSolver implements IStructuralConstraint {

	@Override
	public abstract int matches(EObject process) throws Exception;
}
