package fr.lip6.move.processGenerator.uml2.ga.cp;

import java.util.List;
import java.util.Random;
import org.eclipse.uml2.uml.Action;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ControlNode;
import fr.lip6.move.processGenerator.Filter;
import fr.lip6.move.processGenerator.uml2.UmlException;
import fr.lip6.move.processGenerator.uml2.UmlProcess;

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
}
