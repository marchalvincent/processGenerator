package fr.lip6.move.processGenerator.uml2.constraints;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;
import fr.lip6.move.processGenerator.constraint.IWorkflowRepresentation;

/**
 * Cette classe permet de représenter un morceau de process UML2.0. C'est une version simplifiée d'un process.
 * 
 * @author Vincent
 * 
 */
public class UmlWorkflowRepresentation implements IWorkflowRepresentation {
	
	/**
	 * Représente le début du morceau de process
	 */
	private ActivityNode begin;
	
	/**
	 * Représente la fin du morceau de process
	 */
	private ActivityNode end;
	private List<ActivityNode> nodes;
	private List<ActivityEdge> edges;
	private Map<String, String> controlTwins;
	
	public UmlWorkflowRepresentation() {
		super();
		nodes = new ArrayList<>();
		edges = new ArrayList<>();
		controlTwins = new HashMap<String, String>();
	}
	
	// TODO faire la création de noeud lorsqu'on en a besoin
	
	@Override
	public void setBegin(Object begin) {
		if (begin instanceof ActivityNode)
			this.begin = (ActivityNode) begin;
		else
			System.err.println("The begining of the representation is not an ActivityNode.");
	}
	
	@Override
	public void setEnd(Object end) {
		if (end instanceof ActivityNode)
			this.end = (ActivityNode) end;
		else
			System.err.println("The end of the representation is not an ActivityNode");
	}
	
	@Override
	public ActivityNode getBegin() {
		return begin;
	}
	
	@Override
	public ActivityNode getEnd() {
		return end;
	}
	
	@Override
	public Map<String, String> getLinks() {
		return controlTwins;
	}
	
	@Override
	public List<ActivityNode> getNodes() {
		return nodes;
	}
	
	@Override
	public List<ActivityEdge> getEdges() {
		return edges;
	}
}
