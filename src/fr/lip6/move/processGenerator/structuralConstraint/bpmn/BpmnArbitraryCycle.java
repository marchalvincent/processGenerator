package fr.lip6.move.processGenerator.structuralConstraint.bpmn;

import java.util.List;
import org.eclipse.bpmn2.ExclusiveGateway;
import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.Gateway;
import org.eclipse.bpmn2.GatewayDirection;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.bpmn2.jung.JungEdge;
import fr.lip6.move.processGenerator.bpmn2.jung.JungNode;
import fr.lip6.move.processGenerator.bpmn2.jung.JungProcess;
import fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.changePattern.SESEManager;
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
		
		for (FlowElement element : process.getProcess().getFlowElements()) {
			// on cherche une exclusive gateway fermante qui possède un chemin vers sa jumelle ouvrante
			if (element instanceof ExclusiveGateway) {
				ExclusiveGateway converging = (ExclusiveGateway) element;
				if (converging.getGatewayDirection().equals(GatewayDirection.CONVERGING)) {
					// on récupère la diverging correspondante
					Gateway diverging = SESEManager.instance.getEndOfGateway(process, converging);
					if (diverging == null) {
						System.err.println("Error here, we can't find the twin of the converging exclusive.");
						//TODO trouver la twin lorsqu'elle n'est pas définie par l'utilisateur
					} else {
						// ici on peut vérifier s'il existe un chemin allant de la converging vers la diverging
						DijkstraShortestPath<JungNode, JungEdge> algo = new DijkstraShortestPath<JungNode, JungEdge>(jung.getGraph());
						List<JungEdge> chemin = algo.getPath(jung.getVertex(converging.getId()), jung.getVertex(diverging.getId()));
						
						if (!chemin.isEmpty())
							count ++;
					}
				}
			}
		}
		return count;
	}
	
	
}
