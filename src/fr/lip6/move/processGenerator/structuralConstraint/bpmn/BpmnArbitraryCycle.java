package fr.lip6.move.processGenerator.structuralConstraint.bpmn;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.bpmn2.ExclusiveGateway;
import org.eclipse.bpmn2.FlowNode;
import org.eclipse.bpmn2.GatewayDirection;
import org.eclipse.bpmn2.SequenceFlow;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.bpmn2.jung.JungEdge;
import fr.lip6.move.processGenerator.bpmn2.jung.JungProcess;
import fr.lip6.move.processGenerator.bpmn2.jung.JungVertex;
import fr.lip6.move.processGenerator.bpmn2.utils.Filter;
import fr.lip6.move.processGenerator.structuralConstraint.AbstractJavaSolver;


public class BpmnArbitraryCycle extends AbstractJavaSolver {


	public BpmnArbitraryCycle() {
		super();
	}

	@Override
	public int matches(Object object) throws Exception {

		int count = 0;

		if (!(object instanceof BpmnProcess)) {
			System.err.println("Error, the eObject is not a Bpmn Process.");
			return count;
		}

		BpmnProcess process = (BpmnProcess) object;
		JungProcess jung = new JungProcess(process);
		List<List<JungEdge>> allPaths = new ArrayList<>();

		List<ExclusiveGateway> list = Filter.byType(ExclusiveGateway.class, process.getProcess().getFlowElements(), GatewayDirection.CONVERGING);
		for (ExclusiveGateway converging : list) {
			// on récupère l'activité juste après la converging
			if (converging.getOutgoing().size() > 1)
				System.err.println("Warning, the converging gateway has more than 1 outgoing sequence flow.");
			if (converging.getOutgoing().size() == 0)
				System.err.println("Warning, the converging gateway does not contains any outgoing sequence flow.");
			SequenceFlow sequence = converging.getOutgoing().get(0);
			
			if (sequence != null) {
				FlowNode next = sequence.getTargetRef();
				// et on cherche un chemin partant de ce noeud et revenant à la gateway merge.
				DijkstraShortestPath<JungVertex, JungEdge> algo = new DijkstraShortestPath<JungVertex, JungEdge>(jung.getGraph());
				List<JungEdge> path = algo.getPath(jung.getVertex(next.getId()), jung.getVertex(converging.getId()));

				// maintenant il faut vérifier que cette boucle trouvée n'a pas déjà été comptée
				if (!path.isEmpty() && !exist(allPaths, path)) {
					allPaths.add(path);
					count ++;
				}
			}
		}
		return count;
	}

	/**
	 * Cette méthode renvoie vrai si et seulement si il existe un {@link JungEdge} du paramètre {@code chemin} déjà existant 
	 * parmis les {@link JungEdge} du paramètre {@code allPaths}.
	 * @param allPaths une liste de liste de JungEdge correspondant aux boucles déjà enregistrées.
	 * @param path la liste de JungEdge à vérifier si elle n'existe pas dans l'autre paramètre. 
	 * @return true si il y a une concordance de {@link JungEdge} entre les deux paramètres.
	 */
	private boolean exist(List<List<JungEdge>> allPaths, List<JungEdge> path) {

		// pour chaque liste
		for (List<JungEdge> list : allPaths)
			// pour chaque JungEdge
			for (JungEdge jungEdgeList : list)
				// on parcours le chemin à vérifier
				for (JungEdge jungEdgePath : path)
					// on renvoie vrai s'il existe déjà
					if (jungEdgeList.equals(jungEdgePath))
						return true;
		return false;
	}
}
