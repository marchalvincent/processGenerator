package fr.lip6.move.processGenerator.bpmn2.ga.cp;

import fr.lip6.move.processGenerator.IHierarchicalEnum;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.ga.IChangePattern;
import fr.lip6.move.processGenerator.ga.IEnumChangePattern;

/**
 * Cette énumération représente les change pattern que peut sélectionner l'utilisateur sur l'interface graphique. Chaque
 * énumération est associée à un {@link IEnumChangePattern}.
 * 
 * @author Vincent
 * 
 */
public enum EBpmnChangePattern implements IEnumChangePattern<BpmnProcess> {
	
	WORKFLOW_PATTERN_INSERT(null, BpmnWorkflowInsert.class),
	
	CONDITIONAL_INSERT(null, BpmnConditionalInsertRandom.class),
	CONDITIONAL_ON_SEQUENCE(CONDITIONAL_INSERT, BpmnConditionalInsertSequence.class),
	CONDITIONAL_ON_GATEWAY(CONDITIONAL_INSERT, BpmnConditionalInsertGateway.class),
	
	PARALLEL_INSERT(null, BpmnParallelInsertRandom.class),
	PARALLEL_ON_ACTIVITY(PARALLEL_INSERT, BpmnParallelInsertActivity.class),
	PARALLEL_ON_GATEWAY(PARALLEL_INSERT, BpmnParallelInsertGateway.class),
	
	SERIAL_INSERT(null, BpmnSerialInsert.class),
	REMOVE(null, BpmnRemove.class),
	LOOP_INSERT(null, BpmnLoopInsert.class),
	
	THREAD_INSERT(null, BpmnThreadInsertRandom.class),
	THREAD_IMPLICITE_INSERT(THREAD_INSERT, BpmnThreadInsertImplicit.class),
	THREAD_EXPLICITE_INSERT(THREAD_INSERT, BpmnThreadInsertExplicit.class);
	
	private Class<? extends IChangePattern<BpmnProcess>> clazz;
	private IHierarchicalEnum parent;
	
	private EBpmnChangePattern(IHierarchicalEnum parent, Class<? extends IChangePattern<BpmnProcess>> clazz) {
		this.clazz = clazz;
		this.parent = parent;
	}
	
	@Override
	public IChangePattern<BpmnProcess> newInstance(String proba) throws Exception {
		IChangePattern<BpmnProcess> cp = this.clazz.newInstance();
		cp.setProba(proba);
		return cp;
	}
	
	@Override
	public IHierarchicalEnum getParent() {
		return parent;
	}
}
