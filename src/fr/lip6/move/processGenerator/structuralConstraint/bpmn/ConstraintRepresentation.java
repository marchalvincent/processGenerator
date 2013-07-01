package fr.lip6.move.processGenerator.structuralConstraint.bpmn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.bpmn2.Bpmn2Factory;
import org.eclipse.bpmn2.EndEvent;
import org.eclipse.bpmn2.ExclusiveGateway;
import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.FlowNode;
import org.eclipse.bpmn2.Gateway;
import org.eclipse.bpmn2.GatewayDirection;
import org.eclipse.bpmn2.InclusiveGateway;
import org.eclipse.bpmn2.ParallelGateway;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.Task;

import fr.lip6.move.processGenerator.bpmn2.BpmnNameManager;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.structuralConstraint.IConstraintRepresentation;

public class ConstraintRepresentation implements IConstraintRepresentation {

	private FlowNode begin;
	private FlowNode end;
	private List<FlowElement> flowElements;
	private Map<String, String> gatewaysTwins;
	
	public ConstraintRepresentation() {
		super();
		flowElements = new ArrayList<>();
		gatewaysTwins = new HashMap<>();
	}
	
	public void linkGatewys(Gateway g1, Gateway g2) {
		gatewaysTwins.put(g1.getId(), g2.getId());
		gatewaysTwins.put(g2.getId(), g1.getId());
	}
	
	public Map<String, String> getLinks() {
		return gatewaysTwins;
	}
	
	public EndEvent buildEndEvent() {
		EndEvent end = Bpmn2Factory.eINSTANCE.createEndEvent();
		
		String name = BpmnNameManager.getEndName();
		end.setId("id_" + name);
		end.setName(name);
		
		getFlowElements().add(end);
		return end;
	}

	public Task buildTask() {
		Task task = this.buildRandomSubTask();
		
		String name = BpmnNameManager.getTaskName();
		task.setId("id_" + name);
		task.setName(name);
		
		getFlowElements().add(task);
		return task;
	}
	
	private Task buildRandomSubTask() {
		return BpmnProcess.buildRandomSubTask();
	}
	

	public ParallelGateway buildParallelGatewayDiverging() {
		return this.buildParallelGateway(GatewayDirection.DIVERGING);
	}
	
	public ParallelGateway buildParallelGatewayConverging() {
		return this.buildParallelGateway(GatewayDirection.CONVERGING);
	}
	
	private ParallelGateway buildParallelGateway(GatewayDirection direction) {
		ParallelGateway parallel = Bpmn2Factory.eINSTANCE.createParallelGateway();
		parallel.setGatewayDirection(direction);
		
		String name = BpmnNameManager.getParallelName(direction.toString());
		parallel.setId("id_" + name);
		parallel.setName(name);
		
		getFlowElements().add(parallel);
		return parallel;
	}

	public ExclusiveGateway buildExclusiveGatewayDiverging() {
		return this.buildExclusiveGateway(GatewayDirection.DIVERGING);
	}
	
	public ExclusiveGateway buildExclusiveGatewayConverging() {
		return this.buildExclusiveGateway(GatewayDirection.CONVERGING);
	}
	
	private ExclusiveGateway buildExclusiveGateway(GatewayDirection direction) {
		ExclusiveGateway exclusive = Bpmn2Factory.eINSTANCE.createExclusiveGateway();
		exclusive.setGatewayDirection(direction);
		
		String name = BpmnNameManager.getExclusiveName(direction.toString());
		exclusive.setId("id_" + name);
		exclusive.setName(name);
		
		getFlowElements().add(exclusive);
		return exclusive;
	}

	public InclusiveGateway buildInclusiveGatewayDiverging() {
		return this.buildInclusiveGateway(GatewayDirection.DIVERGING);
	}
	
	public InclusiveGateway buildInclusiveGatewayConverging() {
		return this.buildInclusiveGateway(GatewayDirection.CONVERGING);
	}
	
	private InclusiveGateway buildInclusiveGateway(GatewayDirection direction) {
		InclusiveGateway inclusive = Bpmn2Factory.eINSTANCE.createInclusiveGateway();
		inclusive.setGatewayDirection(direction);
		
		String name = BpmnNameManager.getInclusiveName(direction.toString());
		inclusive.setId("id_" + name);
		inclusive.setName(name);
		
		getFlowElements().add(inclusive);
		return inclusive;
	}

	private SequenceFlow buildSequenceFlow() {
		SequenceFlow sequence = Bpmn2Factory.eINSTANCE.createSequenceFlow();
		
		String name = BpmnNameManager.getSequenceName();
		sequence.setId("id_" + name);
		sequence.setName(name);
		
		getFlowElements().add(sequence);
		return sequence;
	}
	
	public SequenceFlow buildSequenceFlow(FlowNode source, FlowNode target) {
		SequenceFlow sequence = this.buildSequenceFlow();
		sequence.setSourceRef(source);
		sequence.setTargetRef(target);
		source.getOutgoing().add(sequence);
		target.getIncoming().add(sequence);
		return sequence;
	}
	
	public List<FlowElement> getFlowElements() {
		return flowElements;
	}
	
	public FlowNode getBegin() {
		return begin;
	}
	
	public void setBegin(FlowNode begin) {
		this.begin = begin;
	}
	
	public FlowNode getEnd() {
		return end;
	}
	
	public void setEnd(FlowNode end) {
		this.end = end;
	}
}
