package fr.lip6.move.processGenerator.bpmn2.jung;

import org.eclipse.bpmn2.FlowNode;

/**
 * Représente un noeud bpmn (FlowNode). Cette classe est destinée à être manipulée
 * par la librairie JUNG afin d'y appliquer certains algorithmes.
 * @author Vincent
 *
 */
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
