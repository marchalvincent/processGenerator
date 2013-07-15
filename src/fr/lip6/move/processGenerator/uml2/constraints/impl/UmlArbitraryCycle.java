package fr.lip6.move.processGenerator.uml2.constraints.impl;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.MergeNode;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import fr.lip6.move.processGenerator.Utils;
import fr.lip6.move.processGenerator.constraint.AbstractJavaSolver;
import fr.lip6.move.processGenerator.constraint.IWorkflowRepresentation;
import fr.lip6.move.processGenerator.jung.JungEdge;
import fr.lip6.move.processGenerator.jung.JungProcess;
import fr.lip6.move.processGenerator.jung.JungVertex;
import fr.lip6.move.processGenerator.uml2.UmlProcess;
import fr.lip6.move.processGenerator.uml2.utils.UmlFilter;

/**
 * Représente le WP10 - Arbitrary Cycle.
 * 
 * @author Vincent
 * 
 */
public class UmlArbitraryCycle extends AbstractJavaSolver {
	
	@Override
	public int matches(Object object) throws Exception {
		if (!(object instanceof UmlProcess)) {
			System.err.println("Matches method : The object is not an " + UmlProcess.class.getSimpleName() + ".");
			return 0;
		}
		
		int count = 0;
		UmlProcess process = (UmlProcess) object;
		JungProcess jung = new JungProcess(process);
		List<List<JungEdge>> allPaths = new ArrayList<>();
		
		List<MergeNode> listMerge = UmlFilter.byType(MergeNode.class, process.getActivity().getNodes());
		for (MergeNode merge : listMerge) {
			// on récupère l'activité juste après la converging
			if (merge.getOutgoings().size() > 1) {
				System.err.println("Warning, the merge node does not contains only 1 outgoing activityEdge. Number : "
						+ merge.getOutgoings().size() + ", id : " + merge.getName());
				
				if (Utils.DEBUG)
					process.save(System.getProperty("user.home") + Utils.bugPathUml);
			}
			
			ActivityEdge edge = merge.getOutgoings().get(0);
			if (edge != null) {
				ActivityNode next = edge.getTarget();
				// et on cherche un chemin partant de ce noeud et revenant à la gateway merge.
				DijkstraShortestPath<JungVertex, JungEdge> algo = new DijkstraShortestPath<>(jung.getGraph());
				List<JungEdge> path = algo.getPath(jung.getVertex(next.getName()), jung.getVertex(merge.getName()));
				
				// maintenant il faut vérifier que cette boucle trouvée n'a pas déjà été comptée
				if (!path.isEmpty() && !exist(allPaths, path)) {
					allPaths.add(path);
					count++;
				}
			}
		}
		return count;
	}
	
	/**
	 * Cette méthode renvoie vrai si et seulement si il existe un {@link JungEdge} du paramètre {@code path} déjà
	 * existant parmi les {@link JungEdge} du paramètre {@code allPaths}.
	 * 
	 * @param allPaths
	 *            une liste de liste de JungEdge correspondant aux boucles déjà enregistrées.
	 * @param path
	 *            la liste de JungEdge à vérifier si elle n'existe pas dans l'autre paramètre.
	 * @return true si il y a une concordance de {@link JungEdge} entre les deux paramètres.
	 */
	private boolean exist(List<List<JungEdge>> allPaths, List<JungEdge> path) {
		
		// pour chaque liste
		for (List<JungEdge> list : allPaths)
			// pour chaque JungEdge
			for (JungEdge jungEdgeList : list)
				// on parcours le chemin à vérifier
				if (path.contains(jungEdgeList))
					return true;
		return false;
	}
	
	@Override
	public IWorkflowRepresentation getRepresentation() {
		// TODO
		return null;
	}
}
