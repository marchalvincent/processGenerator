package fr.lip6.move.processGenerator.uml2.constraints;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.ControlFlow;
import org.eclipse.uml2.uml.ExecutableNode;
import org.eclipse.uml2.uml.ForkNode;
import org.eclipse.uml2.uml.JoinNode;
import org.eclipse.uml2.uml.UMLFactory;
import fr.lip6.move.processGenerator.constraint.IWorkflowRepresentation;
import fr.lip6.move.processGenerator.uml2.UmlNameManager;
import fr.lip6.move.processGenerator.uml2.UmlProcess;

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
	

	/**
	 * Créé et lie à la représentation un {@link ForkNode}.
	 * @return
	 */
	public ForkNode buildForkNode() {
		ForkNode node = UMLFactory.eINSTANCE.createForkNode();
		this.nameAndLinkNode(node);
		return node;
	}
	
	/**
	 * Créé et lie à la représentation un {@link JoinNode}.
	 * @return
	 */
	public JoinNode buildJoinNode() {
		JoinNode node = UMLFactory.eINSTANCE.createJoinNode();
		this.nameAndLinkNode(node);
		return node;
	}
	
	/**
	 * Créé et lie à la représentation un {@link ControlFlow}.
	 * @param source la source de l'arc.
	 * @param target la destination de l'arc.
	 * @return
	 */
	public ControlFlow buildControlFlow(ActivityNode source, ActivityNode target) {
		ControlFlow edge = UMLFactory.eINSTANCE.createControlFlow();
		
		edge.setName(UmlNameManager.instance.getActivityEdgeName(edge));
		getEdges().add(edge);
		
		edge.setSource(source);
		edge.setTarget(target);
		
		return edge;
	}
	
	/**
	 * Créé et lie à la représentation un {@link ExecutableNode}.
	 * @return
	 */
	public ExecutableNode buildExecutableNode() {
		ExecutableNode node = this.getRandomExecutableNode();
		this.nameAndLinkNode(node);
		return node;
	}
	
	/**
	 * Renvoie un {@link ExecutableNode} tiré au hasard.
	 * @return
	 */
	private ExecutableNode getRandomExecutableNode() {
		return UmlProcess.generateExecutableNode();
	}
	
	private void nameAndLinkNode(ActivityNode node) {
		node.setName(UmlNameManager.instance.getActivityNodeName(node));
		nodes.add(node);
	}

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
