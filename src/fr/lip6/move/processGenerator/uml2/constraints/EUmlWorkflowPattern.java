package fr.lip6.move.processGenerator.uml2.constraints;

import fr.lip6.move.processGenerator.IHierarchicalEnum;
import fr.lip6.move.processGenerator.constraint.IEnumWorkflowPattern;
import fr.lip6.move.processGenerator.constraint.IStructuralConstraint;

/**
 * Cette énumération représente les workflow patterns implémentés pour le type de fichier UML2.0.
 * 
 * @author Vincent
 * 
 */
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

	@Override
	public IHierarchicalEnum getParent() {
		// les workflows patterns n'ont pas de hierarchie
		return null;
	}
}
