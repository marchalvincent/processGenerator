package fr.lip6.move.processGenerator.uml2.constraints;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.uml2.uml.Action;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityFinalNode;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.ControlFlow;
import org.eclipse.uml2.uml.ControlNode;
import org.eclipse.uml2.uml.DecisionNode;
import org.eclipse.uml2.uml.FlowFinalNode;
import org.eclipse.uml2.uml.ForkNode;
import org.eclipse.uml2.uml.JoinNode;
import org.eclipse.uml2.uml.MergeNode;
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
		controlTwins = new HashMap<>();
	}
	
	/**
	 * Créé et lie à la représentation un {@link ActivityFinalNode}.
	 * 
	 * @return
	 */
	public ActivityFinalNode buildActivityFinalNode() {
		ActivityFinalNode finalNode = UMLFactory.eINSTANCE.createActivityFinalNode();
		this.nameAndLinkNode(finalNode);
		return finalNode;
	}
	
	/**
	 * Créé et lie à la représentation un {@link FlowFinalNode}.
	 * 
	 * @return
	 */
	public FlowFinalNode buildFlowFinalNode() {
		FlowFinalNode finalNode = UMLFactory.eINSTANCE.createFlowFinalNode();
		this.nameAndLinkNode(finalNode);
		return finalNode;
	}
	
	/**
	 * Créé et lie à la représentation un {@link ForkNode}.
	 * 
	 * @return
	 */
	public ForkNode buildForkNode() {
		ForkNode node = UMLFactory.eINSTANCE.createForkNode();
		this.nameAndLinkNode(node);
		return node;
	}
	
	/**
	 * Créé et lie à la représentation un {@link JoinNode}.
	 * 
	 * @return
	 */
	public JoinNode buildJoinNode() {
		JoinNode node = UMLFactory.eINSTANCE.createJoinNode();
		this.nameAndLinkNode(node);
		return node;
	}
	
	/**
	 * Créé et lie à la représentation un {@link ControlFlow}.
	 * 
	 * @param source
	 *            la source de l'arc.
	 * @param target
	 *            la destination de l'arc.
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
	 * Créé et lie à la représentation un {@link Action}.
	 * 
	 * @return
	 */
	public Action buildAction() {
		Action node = this.getRandomAction();
		this.nameAndLinkNode(node);
		return node;
	}
	
	/**
	 * Renvoie un {@link Action} tiré au hasard.
	 * 
	 * @return
	 */
	private Action getRandomAction() {
		return UmlProcess.generateAction();
	}
	
	/**
	 * Créé et lie à la représentation un {@link DecisionNode}.
	 * 
	 * @return
	 */
	public DecisionNode buildDecisionNode() {
		DecisionNode node = UMLFactory.eINSTANCE.createDecisionNode();
		this.nameAndLinkNode(node);
		return node;
	}
	
	/**
	 * Créé et lie à la représentation un {@link MergeNode}.
	 * 
	 * @return
	 */
	public MergeNode buildMergeNode() {
		MergeNode node = UMLFactory.eINSTANCE.createMergeNode();
		this.nameAndLinkNode(node);
		return node;
	}
	
	/**
	 * Nomme et lie un noeud à la représentation.
	 * 
	 * @param node
	 */
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
	
	/**
	 * Lie deux {@link ControlNode}s comme étant des jumeaux.
	 * 
	 * @param diverging
	 * @param converging
	 */
	public void linkControlNodes(ControlNode diverging, ControlNode converging) {
		controlTwins.put(diverging.getName(), converging.getName());
		controlTwins.put(converging.getName(), diverging.getName());
	}

}
