package fr.lip6.move.processGenerator.uml2.ga.cp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.eclipse.uml2.uml.Action;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ControlNode;
import org.eclipse.uml2.uml.DecisionNode;
import org.eclipse.uml2.uml.ForkNode;
import org.eclipse.uml2.uml.JoinNode;
import org.eclipse.uml2.uml.MergeNode;
import fr.lip6.move.processGenerator.Filter;
import fr.lip6.move.processGenerator.uml2.UmlException;
import fr.lip6.move.processGenerator.uml2.UmlProcess;
import fr.lip6.move.processGenerator.uml2.utils.UmlFilter;

/**
 * Cette classe permet de simplifier la manipulation des {@link UmlProcess}.
 * 
 * @author Vincent
 * 
 */
public class UmlChangePatternHelper {
	
	public static UmlChangePatternHelper instance = new UmlChangePatternHelper();
	
	private UmlChangePatternHelper() {}
	
	/**
	 * Renvoie un {@link ActivityEdge} au hasard contenu dans le process.
	 * 
	 * @param process
	 *            {@link UmlProcess}.
	 * @param rng
	 *            un source de random.
	 * @return {@link ActivityEdge}.
	 * @throws UmlException
	 *             si le process ne contient aucun arc.
	 */
	public ActivityEdge getRandomActivityEdge(UmlProcess process, Random rng) throws UmlException {
		List<ActivityEdge> list = process.getActivity().getEdges();
		
		// si la liste est vide (cela ne devrait jamasi arriver)
		if (list.isEmpty())
			throw new UmlException("The UmlProcess does not contains any ActivityEdge.");
		
		return list.get(rng.nextInt(list.size()));
	}
	
	/**
	 * Compte le nombre d'{@link Action} d'un process UML2.0.
	 * 
	 * @param process
	 *            {@link UmlProcess}.
	 * @return int.
	 */
	public int countAction(UmlProcess process) {
		return Filter.byType(Action.class, process.getActivity().getNodes()).size();
	}
	
	/**
	 * Compte le nombre de {@link ControlNode} d'un process uml2.0.
	 * 
	 * @param process
	 * @return
	 */
	public int countControlNode(UmlProcess process) {
		return Filter.byType(ControlNode.class, process.getActivity().getNodes()).size();
	}
	
	/**
	 * Compte le nombre d'arc présents dans le process.
	 * 
	 * @param process
	 * @return
	 */
	public int countEdges(UmlProcess process) {
		return process.getActivity().getNodes().size();
	}
	
	/**
	 * Compte le nombre de {@link DecisionNode} linké avec un autre noeud (typiquement un {@link MergeNode}).
	 * 
	 * @param process
	 * @return
	 */
	public int countConditionalLinked(UmlProcess process) {
		int count = 0;
		List<DecisionNode> list = UmlFilter.byType(DecisionNode.class, process.getActivity().getNodes());
		for (DecisionNode decisionNode : list) {
			if (ControlNodeManager.instance.findControlNodeTwin(process, decisionNode) != null)
				count ++;
		}
		return count;
	}
	
	/**
	 * Compte le nombre de {@link ForkNode} linké avec un autre noeud (typiquement un {@link JoinNode}).
	 * 
	 * @param process
	 * @return
	 */
	public int countForkLinked(UmlProcess process) {
		int count = 0;
		List<ForkNode> list = UmlFilter.byType(ForkNode.class, process.getActivity().getNodes());
		for (ForkNode fork : list) {
			if (ControlNodeManager.instance.findControlNodeTwin(process, fork) != null)
				count ++;
		}
		return count;
	}
	
	/**
	 * Renvoie un {@link DecisionNode} linké avec un autre noeuds au hasard parmis ceux du process passé en paramètre.
	 * 
	 * @param process
	 * @param rng
	 * @return
	 * @throws UmlException 
	 */
	public DecisionNode getRandomDecisionNodeLinked(UmlProcess process, Random rng) throws UmlException {
		List<DecisionNode> elus = new ArrayList<>();
		List<DecisionNode> list = UmlFilter.byType(DecisionNode.class, process.getActivity().getNodes());
		for (DecisionNode decisionNode : list) {
			if (ControlNodeManager.instance.findControlNodeTwin(process, decisionNode) != null)
				elus.add(decisionNode);
		}
		if (elus.isEmpty())
			throw new UmlException("The process does not contains any DecisionNode linked.");
		return elus.get(rng.nextInt(elus.size()));
	}
	
	/**
	 * Renvoie un {@link ForkNode} linké avec un autre noeuds au hasard parmis ceux du process passé en paramètre.
	 * 
	 * @param process
	 * @param rng
	 * @return
	 * @throws UmlException 
	 */
	public ForkNode getRandomForkNodeLinked(UmlProcess process, Random rng) throws UmlException {
		List<ForkNode> elus = new ArrayList<>();
		List<ForkNode> list = UmlFilter.byType(ForkNode.class, process.getActivity().getNodes());
		for (ForkNode forkNode : list) {
			if (ControlNodeManager.instance.findControlNodeTwin(process, forkNode) != null)
				elus.add(forkNode);
		}
		if (elus.isEmpty())
			throw new UmlException("The process does not contains any ForkNode linked.");
		return elus.get(rng.nextInt(elus.size()));
	}
	
	/**
	 * Renvoie une {@link Action} tirée au hasard dans le process.
	 * 
	 * @param process
	 * @param rng
	 * @return
	 * @throws UmlException
	 */
	public Action getRandomAction(UmlProcess process, Random rng) throws UmlException {
		List<Action> list = UmlFilter.byType(Action.class, process.getActivity().getNodes());
		if (list.isEmpty())
			throw new UmlException("The process does not contains any Action.");
		return list.get(rng.nextInt(list.size()));
	}
}
