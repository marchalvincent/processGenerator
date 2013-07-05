package fr.lip6.move.processGenerator.bpmn2.constraints;

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
import fr.lip6.move.processGenerator.constraint.IWorkflowRepresentation;

/**
 * Cette classe permet de représenter un morceau de process BPMN. C'est une version simplifiée d'un process.
 * 
 * @author Vincent
 * 
 */
public class BpmnWorkflowRepresentation implements IWorkflowRepresentation {
	
	/**
	 * Représente le début de la représentation.
	 */
	private FlowNode begin;
	
	/**
	 * Représente la fin de la représentation.
	 */
	private FlowNode end;
	private List<FlowElement> nodes;
	private List<FlowElement> edges;
	private Map<String, String> gatewaysTwins;
	
	public BpmnWorkflowRepresentation() {
		super();
		nodes = new ArrayList<>();
		edges = new ArrayList<>();
		gatewaysTwins = new HashMap<>();
	}
	
	/**
	 * Lie deux gateways.
	 * 
	 * @param g1
	 * @param g2
	 */
	public void linkGatewys(Gateway g1, Gateway g2) {
		gatewaysTwins.put(g1.getId(), g2.getId());
		gatewaysTwins.put(g2.getId(), g1.getId());
	}
	
	@Override
	public Map<String, String> getLinks() {
		return gatewaysTwins;
	}
	
	/**
	 * Construit et lie un {@link EndEvent} à la représentation.
	 * 
	 * @return
	 */
	public EndEvent buildEndEvent() {
		EndEvent end = Bpmn2Factory.eINSTANCE.createEndEvent();
		
		String name = BpmnNameManager.instance.getEndName();
		end.setId("id_" + name);
		end.setName(name);
		
		getNodes().add(end);
		return end;
	}
	
	/**
	 * Construit et lie une {@link Task} à la représentation.
	 * 
	 * @return
	 */
	public Task buildTask() {
		Task task = this.buildRandomSubTask();
		
		String name = BpmnNameManager.instance.getTaskName();
		task.setId("id_" + name);
		task.setName(name);
		
		getNodes().add(task);
		return task;
	}
	
	/**
	 * Renvoie une {@link Task} tirée au hasard.
	 * 
	 * @return
	 */
	private Task buildRandomSubTask() {
		return BpmnProcess.buildRandomSubTask();
	}
	
	/**
	 * Construit et lie une {@link ParallelGateway} divergente à la représentation.
	 * 
	 * @return
	 */
	public ParallelGateway buildParallelGatewayDiverging() {
		return this.buildParallelGateway(GatewayDirection.DIVERGING);
	}
	
	/**
	 * Construit et lie une {@link ParallelGateway} convergente à la représentation.
	 * 
	 * @return
	 */
	public ParallelGateway buildParallelGatewayConverging() {
		return this.buildParallelGateway(GatewayDirection.CONVERGING);
	}
	
	/**
	 * Construit et lie une {@link ParallelGateway} à la représentation.
	 * 
	 * @param la
	 *            {@link GatewayDirection} de la gateway.
	 * @return
	 */
	private ParallelGateway buildParallelGateway(GatewayDirection direction) {
		ParallelGateway parallel = Bpmn2Factory.eINSTANCE.createParallelGateway();
		parallel.setGatewayDirection(direction);
		
		String name = BpmnNameManager.instance.getParallelName(direction.toString());
		parallel.setId("id_" + name);
		parallel.setName(name);
		
		getNodes().add(parallel);
		return parallel;
	}
	
	/**
	 * Construit et lie une {@link ExclusiveGateway} divergente à la représentation.
	 * 
	 * @return
	 */
	public ExclusiveGateway buildExclusiveGatewayDiverging() {
		return this.buildExclusiveGateway(GatewayDirection.DIVERGING);
	}
	
	/**
	 * Construit et lie une {@link ExclusiveGateway} convergente à la représentation.
	 * 
	 * @return
	 */
	public ExclusiveGateway buildExclusiveGatewayConverging() {
		return this.buildExclusiveGateway(GatewayDirection.CONVERGING);
	}
	
	/**
	 * Construit et lie une {@link ExclusiveGateway} à la représentation.
	 * 
	 * @param la
	 *            {@link GatewayDirection} de la gateway
	 * @return
	 */
	private ExclusiveGateway buildExclusiveGateway(GatewayDirection direction) {
		ExclusiveGateway exclusive = Bpmn2Factory.eINSTANCE.createExclusiveGateway();
		exclusive.setGatewayDirection(direction);
		
		String name = BpmnNameManager.instance.getExclusiveName(direction.toString());
		exclusive.setId("id_" + name);
		exclusive.setName(name);
		
		getNodes().add(exclusive);
		return exclusive;
	}
	
	/**
	 * Construit et lie une {@link InclusiveGateway} divergente à la représentation.
	 * 
	 * @return
	 */
	public InclusiveGateway buildInclusiveGatewayDiverging() {
		return this.buildInclusiveGateway(GatewayDirection.DIVERGING);
	}
	
	/**
	 * Construit et lie une {@link InclusiveGateway} convergente à la représentation.
	 * 
	 * @return
	 */
	public InclusiveGateway buildInclusiveGatewayConverging() {
		return this.buildInclusiveGateway(GatewayDirection.CONVERGING);
	}
	
	/**
	 * Construit et lie une {@link InclusiveGateway} à la représentation.
	 * 
	 * @param la
	 *            {@link GatewayDirection} de la gateway.
	 * @return
	 */
	private InclusiveGateway buildInclusiveGateway(GatewayDirection direction) {
		InclusiveGateway inclusive = Bpmn2Factory.eINSTANCE.createInclusiveGateway();
		inclusive.setGatewayDirection(direction);
		
		String name = BpmnNameManager.instance.getInclusiveName(direction.toString());
		inclusive.setId("id_" + name);
		inclusive.setName(name);
		
		getNodes().add(inclusive);
		return inclusive;
	}
	
	/**
	 * Construit et lie un {@link SequenceFlow} à la représentation.
	 * 
	 * @return
	 */
	private SequenceFlow buildSequenceFlow() {
		SequenceFlow sequence = Bpmn2Factory.eINSTANCE.createSequenceFlow();
		
		String name = BpmnNameManager.instance.getSequenceName();
		sequence.setId("id_" + name);
		sequence.setName(name);
		
		getEdges().add(sequence);
		return sequence;
	}
	
	/**
	 * Construit et lie un {@link SequenceFlow} à la représentation.
	 * 
	 * @param source
	 *            la source de l'arc.
	 * @param target
	 *            la destination de l'arc.
	 * @return
	 */
	public SequenceFlow buildSequenceFlow(FlowNode source, FlowNode target) {
		SequenceFlow sequence = this.buildSequenceFlow();
		sequence.setSourceRef(source);
		sequence.setTargetRef(target);
		source.getOutgoing().add(sequence);
		target.getIncoming().add(sequence);
		return sequence;
	}
	
	@Override
	public FlowNode getBegin() {
		return begin;
	}
	
	@Override
	public void setBegin(Object begin) {
		if (begin instanceof FlowNode)
			this.begin = (FlowNode) begin;
		else
			System.err.println("The beginning of the representation is not a FlowNode.");
	}
	
	@Override
	public FlowNode getEnd() {
		return end;
	}
	
	@Override
	public void setEnd(Object end) {
		if (end instanceof FlowNode)
			this.end = (FlowNode) end;
		else
			System.err.println("The end of the representation is not a FlowNode.");
	}

	@Override
	public List<FlowElement> getNodes() {
		return nodes;
	}

	@Override
	public List<FlowElement> getEdges() {
		return edges;
	}
}
