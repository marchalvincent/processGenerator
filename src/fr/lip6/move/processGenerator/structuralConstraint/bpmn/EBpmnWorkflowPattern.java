package fr.lip6.move.processGenerator.structuralConstraint.bpmn;

import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.structuralConstraint.IEnumWorkflowPattern;
import fr.lip6.move.processGenerator.structuralConstraint.IStructuralConstraint;


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
	STRUCTURED_SYNCHRONIZING_MERGE(BpmnStructuredSynchronizingMerge.class);
	
	private Class<? extends IStructuralConstraint> clazz;
	
	private EBpmnWorkflowPattern(Class<? extends IStructuralConstraint> clazz) {
		this.clazz = clazz;
	}
	
	/**
	 * 
	 * @return
	 * @throws BpmnException peut renvoyer cette exception si la lecture de la requête OCL est impossible
	 */
	@Override
	public IStructuralConstraint newInstance() throws Exception {
		return this.clazz.newInstance();
	}
}
