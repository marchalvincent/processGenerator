package fr.lip6.move.processGenerator.bpmn2;

import org.eclipse.bpmn2.EndEvent;
import org.eclipse.bpmn2.Event;
import org.eclipse.bpmn2.ExclusiveGateway;
import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.Gateway;
import org.eclipse.bpmn2.InclusiveGateway;
import org.eclipse.bpmn2.ManualTask;
import org.eclipse.bpmn2.ParallelGateway;
import org.eclipse.bpmn2.ReceiveTask;
import org.eclipse.bpmn2.ScriptTask;
import org.eclipse.bpmn2.ServiceTask;
import org.eclipse.bpmn2.StartEvent;
import org.eclipse.bpmn2.Task;
import org.eclipse.bpmn2.UserTask;
import fr.lip6.move.processGenerator.IEnumElement;
import fr.lip6.move.processGenerator.IHierarchicalEnum;

/**
 * Cette énumération représente les éléments que peut sélectionner l'utilisateur sur l'interface graphique afin de
 * définir ses contraintes structurelles.
 * 
 * @author Vincent
 * 
 */
public enum EBpmnElement implements IEnumElement {
	
	EVENT(null, Event.class),
	START_EVENT(EVENT, StartEvent.class),
	END_EVENT(EVENT, EndEvent.class),
	
	TASK(null, Task.class),
	MANUAL_TASK(TASK, ManualTask.class),
	RECEIVE_TASK(TASK, ReceiveTask.class),
	SCRIPT_TASK(TASK, ScriptTask.class),
	SERVICE_TASK(TASK, ServiceTask.class),
	USER_TASK(TASK, UserTask.class),
	
	GATEWAY(null, Gateway.class),
	PARALLEL_GATEWAY(GATEWAY, ParallelGateway.class),
	EXCLUSIVE_GATEWAY(GATEWAY, ExclusiveGateway.class),
	INCLUSIVE_GATEWAY(GATEWAY, InclusiveGateway.class);
	
	private IHierarchicalEnum parent;
	private Class<? extends FlowElement> clazz;
	
	private EBpmnElement(IHierarchicalEnum parent, Class<? extends FlowElement> clazz) {
		this.parent = parent;
		this.clazz = clazz;
	}
	
	@Override
	public String toString() {
		return this.clazz.getSimpleName();
	}
	
	@Override
	public IHierarchicalEnum getParent() {
		return parent;
	}
	
	@Override
	public Class<?> getAssociatedClass() {
		return clazz;
	}
}
