package fr.lip6.move.processGenerator.bpmn2.constraints.impl;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.bpmn2.ExclusiveGateway;
import org.eclipse.bpmn2.FlowNode;
import org.eclipse.bpmn2.GatewayDirection;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.Task;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import fr.lip6.move.processGenerator.Utils;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.bpmn2.constraints.BpmnWorkflowRepresentation;
import fr.lip6.move.processGenerator.bpmn2.utils.BpmnFilter;
import fr.lip6.move.processGenerator.constraint.AbstractJavaSolver;
import fr.lip6.move.processGenerator.constraint.IWorkflowRepresentation;
import fr.lip6.move.processGenerator.jung.JungEdge;
import fr.lip6.move.processGenerator.jung.JungProcess;
import fr.lip6.move.processGenerator.jung.JungVertex;

/**
 * Représente le WP10 - Arbitrary Cycle.
 * 
 * @author Vincent
 * 
 */
public class BpmnArbitraryCycle extends AbstractJavaSolver {
	
	@Override
	public int matches(Object object) {
		
		if (!(object instanceof BpmnProcess)) {
			System.err.println("Error, the eObject is not a Bpmn Process.");
			return 0;
		}
		
		int count = 0;
		
		BpmnProcess process = (BpmnProcess) object;
		JungProcess jung = new JungProcess(process);
		List<List<JungEdge>> allPaths = new ArrayList<>();
		
		List<ExclusiveGateway> list = BpmnFilter.byType(ExclusiveGateway.class, process.getProcess().getFlowElements(),
				GatewayDirection.CONVERGING);
		for (ExclusiveGateway converging : list) {
			// on récupère l'activité juste après la converging
			if (converging.getOutgoing().size() > 1) {
				System.err.println("Warning, the converging gateway does not contains only 1 outgoing sequence flow. Number : "
						+ converging.getOutgoing().size() + ", id : " + converging.getId());
				
				if (Utils.DEBUG)
					process.save(System.getProperty("user.home") + Utils.bugPathBpmn);
			}
			
			SequenceFlow sequence = converging.getOutgoing().get(0);
			if (sequence != null) {
				FlowNode next = sequence.getTargetRef();
				// et on cherche un chemin partant de ce noeud et revenant à la gateway merge.
				DijkstraShortestPath<JungVertex, JungEdge> algo = new DijkstraShortestPath<JungVertex, JungEdge>(jung.getGraph());
				List<JungEdge> path = algo.getPath(jung.getVertex(next.getId()), jung.getVertex(converging.getId()));
				
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
		BpmnWorkflowRepresentation representation = new BpmnWorkflowRepresentation();
		
		// on construit les noeuds
		ExclusiveGateway converging = representation.buildExclusiveGatewayConverging();
		Task task = representation.buildTask();
		ExclusiveGateway diverging = representation.buildExclusiveGatewayDiverging();
		
		representation.linkGatewys(converging, diverging);
		
		// puis on construit les arcs
		representation.buildSequenceFlow(converging, task);
		representation.buildSequenceFlow(task, diverging);
		representation.buildSequenceFlow(diverging, converging);
		
		// on définit le début et la fin de la représentation
		representation.setBegin(converging);
		representation.setEnd(diverging);
		
		return representation;
	}
}
