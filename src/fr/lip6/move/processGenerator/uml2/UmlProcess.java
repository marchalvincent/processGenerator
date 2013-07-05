package fr.lip6.move.processGenerator.uml2;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.uml.Action;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityFinalNode;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.ControlFlow;
import org.eclipse.uml2.uml.DecisionNode;
import org.eclipse.uml2.uml.ForkNode;
import org.eclipse.uml2.uml.InitialNode;
import org.eclipse.uml2.uml.JoinNode;
import org.eclipse.uml2.uml.MergeNode;
import org.eclipse.uml2.uml.UMLFactory;

/**
 * Représente un process UML.
 * 
 * @author Vincent
 * 
 */
public class UmlProcess {
	
	private Activity activity;
	
	/**
	 * Créé un process UML vide.
	 */
	public UmlProcess() {
		super();
		activity = UMLFactory.eINSTANCE.createActivity();
		activity.setName(UmlNameManager.instance.getProcessName());
	}
	
	/**
	 * Créé une copie du process UML passé en paramètre.
	 * 
	 * @param activityToCopy
	 */
	public UmlProcess(UmlProcess processToCopy) {
		super();
		this.activity = EcoreUtil.copy(processToCopy.getActivity());
	}
	
	/**
	 * Constructeur avec un process déjà définit.
	 * 
	 * @param activity
	 */
	public UmlProcess(Activity activity) {
		super();
		this.activity = activity;
	}
	
	/**
	 * Lie au process et nomme le noeud passé en paramètre.
	 * 
	 * @param node
	 */
	private void linkedAndNameNode(ActivityNode node) {
		activity.getNodes().add(node);
		node.setActivity(activity);
		node.setName(UmlNameManager.instance.getActivityNodeName(node));
	}
	
	/**
	 * Lie au process et nomme l'arc passé en paramètre.
	 * 
	 * @param edge
	 */
	private void linkedAndNameEdge(ActivityEdge edge) {
		activity.getEdges().add(edge);
		edge.setActivity(activity);
		edge.setName(UmlNameManager.instance.getActivityEdgeName(edge));
	}
	
	/**
	 * Créé et lie au process une {@link Action} choisie au hasard.
	 * 
	 * @return
	 */
	public Action buildRandomAction() {
		Action action = this.generateAction();
		linkedAndNameNode(action);
		return action;
	}
	
	/**
	 * Renvoie une {@link Action} tirée au hasard.
	 * 
	 * @return
	 */
	public Action generateAction() {
		return UMLFactory.eINSTANCE.createOpaqueAction();
	}
	
	/**
	 * Créé et lie au process un {@link InitialNode}.
	 * 
	 * @return
	 */
	public InitialNode buildInitialNode() {
		InitialNode init = UMLFactory.eINSTANCE.createInitialNode();
		linkedAndNameNode(init);
		return init;
	}
	
	/**
	 * Créé et lie au process une {@link ActivityFinalNode}.
	 * 
	 * @return
	 */
	public ActivityFinalNode buildActivityFinalNode() {
		ActivityFinalNode finalNode = UMLFactory.eINSTANCE.createActivityFinalNode();
		linkedAndNameNode(finalNode);
		return finalNode;
	}
	
	/**
	 * Créé et lie au process une {@link DecisionNode}.
	 * 
	 * @return
	 */
	public DecisionNode buildDecisionNode() {
		DecisionNode decision = UMLFactory.eINSTANCE.createDecisionNode();
		linkedAndNameNode(decision);
		return decision;
	}
	
	/**
	 * Créé et lie au process un {@link MergeNode}.
	 * 
	 * @return
	 */
	public MergeNode buildMergeNode() {
		MergeNode merge = UMLFactory.eINSTANCE.createMergeNode();
		linkedAndNameNode(merge);
		return merge;
	}
	
	/**
	 * Créé et lie au process un {@link ForkNode}.
	 * 
	 * @return
	 */
	public ForkNode buildForkNode() {
		ForkNode fork = UMLFactory.eINSTANCE.createForkNode();
		linkedAndNameNode(fork);
		return fork;
	}
	
	/**
	 * Créé et lie au process un {@link JoinNode}.
	 * 
	 * @return
	 */
	public JoinNode buildJoinNode() {
		JoinNode join = UMLFactory.eINSTANCE.createJoinNode();
		linkedAndNameNode(join);
		return join;
	}
	
	/**
	 * Créé et lie au process un {@link ControlFlow}.
	 * 
	 * @return
	 */
	public ControlFlow buildControlFlow() {
		ControlFlow controlFlow = UMLFactory.eINSTANCE.createControlFlow();
		linkedAndNameEdge(controlFlow);
		return controlFlow;
	}
	
	/**
	 * Créé et lie au process un {@link ControlFlow}. De plus, spécifie sa source et sa destination.
	 * 
	 * @param source
	 *            la source de l'arc.
	 * @param target
	 *            la destination de l'arc.
	 * @return
	 */
	public ControlFlow buildControlFlow(ActivityNode source, ActivityNode target) {
		ControlFlow controlFlow = buildControlFlow();
		controlFlow.setSource(source);
		controlFlow.setTarget(target);
		return controlFlow;
	}
	
	/**
	 * Getter.
	 * 
	 * @return {@link Activity}.
	 */
	public Activity getActivity() {
		return activity;
	}

	public void save(String string) {
		// TODO Auto-generated method stub
		
	}
}
