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
	private String type;
	private String fillColor;
	private String width;
	private String height;
	
	/**
	 * Initialise la partie graphique "dot".
	 * 
	 * @param type
	 * @param color
	 * @param width
	 * @param height
	 */
	private JungVertex(String type, String color, String width, String height) {
		super();
		this.type = type;
		this.fillColor = color;
		this.width = width;
		this.height = height;
	}
	
	/**
	 * Constructeur à partir d'un {@link FlowNode} bpmn2.0.
	 * 
	 * @param node
	 * @param type 
	 * @param color
	 * @param height 
	 * @param width 
	 */
	public JungVertex(FlowNode node, String type, String color, String width, String height) {
		this(type, color, width, height);
		this.id = node.getId();
	}
	
	/**
	 * Constructeur à partir d'un {@link ActivityNode} uml2.0.
	 * 
	 * @param node
	 * @param color 
	 * @param height 
	 * @param width 
	 */
	public JungVertex(ActivityNode node, String type, String color, String width, String height) {
		this(type, color, width, height);
		this.id = node.getName();
	}
	
	public String getId() {
		return id;
	}
	
	public String getType() {
		return type;
	}
	
	public String getWidth() {
		return width;
	}

	
	public String getHeight() {
		return height;
	}

	public String getFillColor() {
		return fillColor;
	}
	
	@Override
	public String toString() {
		return "JungNode[id=" + id + "]";
	}
}
