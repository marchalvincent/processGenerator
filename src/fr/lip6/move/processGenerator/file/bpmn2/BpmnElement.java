package fr.lip6.move.processGenerator.file.bpmn2;

import fr.lip6.move.processGenerator.file.Element;


public enum BpmnElement implements Element {
	
	START_EVENT,
	END_EVENT,
	TASK,
	PARALLEL_GATEWAY_DIVERGING,
	PARALLEL_GATEWAY_CONVERGING,
	EXCLUSIVE_GATEWAY_DIVERGING,
	EXCLUSIVE_GATEWAY_CONVERGING
	
}
