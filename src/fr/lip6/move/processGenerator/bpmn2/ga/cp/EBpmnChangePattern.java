package fr.lip6.move.processGenerator.bpmn2.ga.cp;

import fr.lip6.move.processGenerator.ga.IChangePattern;
import fr.lip6.move.processGenerator.ga.IEnumChangePattern;

/**
 * Cette énumération représente les change pattern que peut sélectionner l'utilisateur sur l'interface graphique. Chaque
 * énumération est associée à un {@link IEnumChangePattern}.
 * 
 * @author Vincent
 * 
 */
public enum EBpmnChangePattern implements IEnumChangePattern {
	
	WORKFLOW_INSERT(BpmnWorkflowInsert.class),
	CONDITIONAL_INSERT(BpmnConditionalInsert.class),
	PARALLEL_INSERT(BpmnParallelInsert.class),
	SERIAL_INSERT(BpmnSerialInsert.class),
	REMOVE(BpmnRemove.class),
	LOOP_INSERT(BpmnLoopInsert.class),
	THREAD_INSERT(BpmnThreadInsert.class);
	
	private Class<? extends IChangePattern> clazz;
	
	private EBpmnChangePattern(Class<? extends IChangePattern> clazz) {
		this.clazz = clazz;
	}
	
	@Override
	public IChangePattern newInstance (String proba) throws Exception {
		IChangePattern cp = this.clazz.newInstance();
		cp.setProba(proba);
		return cp;
	}
}