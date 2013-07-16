package fr.lip6.move.processGenerator.uml2.constraints;

import fr.lip6.move.processGenerator.IHierarchicalEnum;
import fr.lip6.move.processGenerator.constraint.IEnumWorkflowPattern;
import fr.lip6.move.processGenerator.constraint.IStructuralConstraint;
import fr.lip6.move.processGenerator.uml2.constraints.impl.UmlArbitraryCycle;
import fr.lip6.move.processGenerator.uml2.constraints.impl.UmlExclusiveChoice;
import fr.lip6.move.processGenerator.uml2.constraints.impl.UmlExplicitTermination;
import fr.lip6.move.processGenerator.uml2.constraints.impl.UmlImplicitTermination;
import fr.lip6.move.processGenerator.uml2.constraints.impl.UmlParallelSplit;
import fr.lip6.move.processGenerator.uml2.constraints.impl.UmlSequence;
import fr.lip6.move.processGenerator.uml2.constraints.impl.UmlSimpleMerge;
import fr.lip6.move.processGenerator.uml2.constraints.impl.UmlSynchronization;

/**
 * Cette énumération représente les workflow patterns implémentés pour le type de fichier UML2.0.
 * 
 * @author Vincent
 * 
 */
public enum EUmlWorkflowPattern implements IEnumWorkflowPattern {
	
	SEQUENCE(UmlSequence.class),
	PARALLEL_SPLIT(UmlParallelSplit.class),
	SYNCHRONIZATION(UmlSynchronization.class),
	EXCLUSIVE_CHOICE(UmlExclusiveChoice.class),
	SIMPLE_MERGE(UmlSimpleMerge.class),
	ARBITRARY_CYCLE(UmlArbitraryCycle.class),
	EXPLICIT_TERMINATION(UmlExplicitTermination.class),
	IMPLICIT_TERMINATION(UmlImplicitTermination.class);
	
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
