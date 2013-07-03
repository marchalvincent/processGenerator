package fr.lip6.move.processGenerator.bpmn2.constraints;

import fr.lip6.move.processGenerator.constraint.IEnumWorkflowPattern;
import fr.lip6.move.processGenerator.constraint.IStructuralConstraint;


/**
 * Cette enumération liste l'ensemble des workflow patterns implémentés pour le type de fichier bpmn.
 * @author Vincent
 *
 */
public enum EBpmnWorkflowPattern implements IEnumWorkflowPattern {
	
	SEQUENCE(BpmnSequence.class),
	PARALLEL_SPLIT(BpmnParallelSplit.class),
	SYNCHRONISATION(BpmnSynchronization.class),
	EXCLUSIVE_CHOICE(BpmnExclusiveChoice.class),
	SIMPLE_MERGE(BpmnSimpleMerge.class),
	MULTI_CHOICE(BpmnMultiChoice.class),
	MULTI_MERGE(BpmnMultiMerge.class),
	STRUCTURED_SYNCHRONIZING_MERGE(BpmnStructuredSynchronizingMerge.class),
	ARBITRARY_CYCLE(BpmnArbitraryCycle.class),
	IMPLICITE_TERMINATION(BpmnImplicitTermination.class),
	EXPLICITE_TERMINATION(BpmnExpliciteTermination.class);
	
	private Class<? extends IStructuralConstraint> clazz;
	
	private EBpmnWorkflowPattern(Class<? extends IStructuralConstraint> clazz) {
		this.clazz = clazz;
	}
	
	@Override
	public IStructuralConstraint newInstance() throws Exception {
		return this.clazz.newInstance();
	}
}
