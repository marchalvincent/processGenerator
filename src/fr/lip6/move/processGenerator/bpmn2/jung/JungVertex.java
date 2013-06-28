package fr.lip6.move.processGenerator.bpmn2.jung;

import org.eclipse.bpmn2.FlowNode;


public class JungVertex {
	
	private String id;
	
	public JungVertex(FlowNode node) {
		super();
		this.id = node.getId();
	}

	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return "JungNode[id=" + id + "]";
	}
}