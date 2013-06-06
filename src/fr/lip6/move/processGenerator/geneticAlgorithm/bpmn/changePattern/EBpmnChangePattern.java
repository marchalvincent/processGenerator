package fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.changePattern;

import fr.lip6.move.processGenerator.geneticAlgorithm.IChangePattern;
import fr.lip6.move.processGenerator.geneticAlgorithm.IEnumChangePattern;


public enum EBpmnChangePattern implements IEnumChangePattern {
	
	CONDITIONAL_INSERT(BpmnConditionalInsert.class),
	PARALLEL_INSERT(BpmnParallelInsert.class),
	SERIAL_INSERT(BpmnSerialInsert.class),
	REMOVE(BpmnRemove.class);
	
	private Class<? extends IChangePattern> clazz;
	
	private EBpmnChangePattern(Class<? extends IChangePattern> clazz) {
		this.clazz = clazz;
	}

	@Override
	public IChangePattern newInstance(String proba) throws Exception {
		IChangePattern cp = this.clazz.newInstance();
		cp.setProba(proba);
		return cp;
	}
}
