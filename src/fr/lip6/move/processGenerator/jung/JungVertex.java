package fr.lip6.move.processGenerator.jung;

import org.eclipse.bpmn2.FlowNode;
import org.eclipse.uml2.uml.ActivityNode;

/**
 * Représente un noeud bpmn ({@link FlowNode}) ou uml ({@link ActivityNode}). Cette classe est destinée à être manipulée
 * par la librairie JUNG afin d'y appliquer certains algorithmes.
 * 
 * @author Vincent
 * 
 */
public class JungVertex {
	
	private String id;
	
	/**
	 * Constructeur à partir d'un {@link FlowNode} bpmn2.0.
	 * 
	 * @param node
	 */
	public JungVertex(FlowNode node) {
		super();
		this.id = node.getId();
	}
	
	/**
	 * Constructeur à partir d'un {@link ActivityNode} uml2.0.
	 * 
	 * @param node
	 */
	public JungVertex(ActivityNode node) {
		super();
		this.id = node.getName();
	}
	
	public String getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return "JungNode[id=" + id + "]";
	}
}
