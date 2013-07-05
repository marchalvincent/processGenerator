package fr.lip6.move.processGenerator.bpmn2;

import org.eclipse.bpmn2.EndEvent;
import org.eclipse.bpmn2.ExclusiveGateway;
import org.eclipse.bpmn2.FlowNode;
import org.eclipse.bpmn2.InclusiveGateway;
import org.eclipse.bpmn2.ParallelGateway;
import org.eclipse.bpmn2.StartEvent;
import org.eclipse.bpmn2.Task;
import fr.lip6.move.processGenerator.IEnumElement;

/**
 * Cette énumération représente les éléments que peut sélectionner l'utilisateur sur l'interface graphique afin de
 * définir ses contraintes structurelles.
 * 
 * @author Vincent
 * 
 */
public enum EBpmnElement implements IEnumElement {
	
	START_EVENT(StartEvent.class),
	END_EVENT(EndEvent.class),
	TASK(Task.class),
	PARALLEL_GATEWAY(ParallelGateway.class),
	EXCLUSIVE_GATEWAY(ExclusiveGateway.class),
	INCLUSIVE_GATEWAY(InclusiveGateway.class);
	
	private Class<? extends FlowNode> clazz;
	
	private EBpmnElement(Class<? extends FlowNode> clazz) {
		this.clazz = clazz;
	}
	
	@Override
	public String toString() {
		return this.clazz.getSimpleName();
	}
}
