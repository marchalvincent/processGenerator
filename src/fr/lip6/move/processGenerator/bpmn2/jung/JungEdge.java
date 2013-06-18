package fr.lip6.move.processGenerator.bpmn2.jung;

import org.eclipse.bpmn2.SequenceFlow;


public class JungEdge {
	
	private String id;
	
	public JungEdge(SequenceFlow sequence) {
		super();
		this.id = sequence.getId();
	}

	@Override
	public String toString() {
		return "JungEdge[id=" + id + "]";
	}
}
