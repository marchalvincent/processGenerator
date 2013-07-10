package fr.lip6.move.processGenerator.uml2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.Map;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.util.XMLProcessor;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityFinalNode;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.ControlFlow;
import org.eclipse.uml2.uml.ControlNode;
import org.eclipse.uml2.uml.DecisionNode;
import org.eclipse.uml2.uml.ExecutableNode;
import org.eclipse.uml2.uml.ForkNode;
import org.eclipse.uml2.uml.InitialNode;
import org.eclipse.uml2.uml.JoinNode;
import org.eclipse.uml2.uml.MergeNode;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.resource.UMLResource;

/**
 * Représente un process UML.
 * 
 * @author Vincent
 * 
 */
public class UmlProcess {
	
	private Activity activity;
	
	private Map<String, String> twins;
	
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
	 * Créé et lie au process un {@link ExecutableNode} choisi au hasard.
	 * 
	 * @return
	 */
	public ExecutableNode buildRandomExecutableNode() {
		ExecutableNode node = generateExecutableNode();
		linkedAndNameNode(node);
		return node;
	}
	
	/**
	 * Renvoie un {@link ExecutableNode} tiré au hasard.
	 * 
	 * @return
	 */
	public static ExecutableNode generateExecutableNode() {
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
	private ControlFlow buildControlFlow() {
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
	
	/**
	 * Sauvegarde le process dans un fichier dont le path est spécifié en paramètre.
	 * 
	 * @param path
	 *            String, le path du fichier à enregistrer.
	 */
	public void save(String path) {
		
		Model model = UMLFactory.eINSTANCE.createModel();
		model.getPackagedElements().add(getActivity());
		
		Factory.Registry.INSTANCE.getExtensionToFactoryMap().put(UMLResource.FILE_EXTENSION, UMLResource.Factory.INSTANCE);
		
		Resource resource = new ResourceSetImpl().createResource(URI.createPlatformResourceURI(path, true));
		resource.getContents().add(model);
		
		XMLProcessor proc = new XMLProcessor();
		Map<Object, Object> options = Collections.emptyMap();
		
		File f = new File(path);
		OutputStream out = null;
		try {
			f.createNewFile();
			out = new FileOutputStream(f);
			proc.save(out, resource, options);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null)
				try {
					out.close();
				} catch (IOException e) {}
		}
	}
	
	/**
	 * Lie deux {@link ControlNode}s.
	 * 
	 * @param diverging
	 * @param converging
	 */
	public void linkControlNodes(ControlNode diverging, ControlNode converging) {
		twins.put(diverging.getName(), converging.getName());
		twins.put(converging.getName(), diverging.getName());
	}
	
	/**
	 * Ajoute plusieurs liens entre deux {@link ControlNode}. La Map manipulée contient les noms des ControlNodes.
	 * 
	 * @param links
	 */
	public void addLinksControlNodes(Map<String, String> links) {
		twins.putAll(links);
	}
	
	/**
	 * Renvoie le {@link ControlNode} jumeau de celui dont l'id est passé en paramètre.
	 * 
	 * @param controlNodeName
	 *            l'id
	 * @return {@link ControlNode}.
	 */
	public ControlNode getTwin(String controlNodeName) {
		String twinId = twins.get(controlNodeName);
		if (twinId != null) {
			for (ActivityNode node : getActivity().getNodes()) {
				if (node instanceof ControlNode && node.getName().equals(twinId))
					return (ControlNode) node;
			}
		}
		return null;
	}
}
