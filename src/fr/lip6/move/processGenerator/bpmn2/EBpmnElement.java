package fr.lip6.move.processGenerator.bpmn2;

import org.eclipse.bpmn2.EndEvent;
import org.eclipse.bpmn2.ExclusiveGateway;
import org.eclipse.bpmn2.FlowNode;
import org.eclipse.bpmn2.ParallelGateway;
import org.eclipse.bpmn2.StartEvent;
import org.eclipse.bpmn2.Task;


public enum EBpmnElement {
	
	START_EVENT(StartEvent.class),
	END_EVENT(EndEvent.class),
	TASK(Task.class),
	PARALLEL_GATEWAY_DIVERGING(ParallelGateway.class),
	PARALLEL_GATEWAY_CONVERGING(ParallelGateway.class),
	EXCLUSIVE_GATEWAY_DIVERGING(ExclusiveGateway.class),
	EXCLUSIVE_GATEWAY_CONVERGING(ExclusiveGateway.class);
	
	private Class<? extends FlowNode> clazz;
	private EBpmnElement(Class<? extends FlowNode> clazz) {
		this.clazz = clazz;
	}
	
	@Override
	public String toString() {
		return this.clazz.getSimpleName();
	}
}
