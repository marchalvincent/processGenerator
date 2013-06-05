package fr.lip6.move.processGenerator.structuralConstraint.uml;

import fr.lip6.move.processGenerator.structuralConstraint.IEnumWorkflowPattern;
import fr.lip6.move.processGenerator.structuralConstraint.IStructuralConstraint;


public enum EUmlWorkflowPattern implements IEnumWorkflowPattern {

	SEQUENCE(UmlSequence.class);
	
	private Class<? extends IStructuralConstraint> clazz;
	
	private EUmlWorkflowPattern(Class<? extends IStructuralConstraint> clazz) {
		this.clazz = clazz;
	}

	@Override
	public IStructuralConstraint newInstance() throws Exception {
		return this.clazz.newInstance();
	}
}
