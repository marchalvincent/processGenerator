package fr.lip6.move.processGenerator.bpmn2.jung;

import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.List;
import org.eclipse.bpmn2.ExclusiveGateway;
import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.GatewayDirection;
import org.junit.Test;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import fr.lip6.move.processGenerator.bpmn2.BpmnBuilder;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;


public class JungManagerTest {

	@Test
	public void test() throws IOException {
		BpmnProcess process = BpmnBuilder.createExampleWithParallel();
		JungProcess jung = new JungProcess(process);
		
		DijkstraShortestPath<JungNode, JungEdge> alg = new DijkstraShortestPath<JungNode, JungEdge>(jung.getGraph());
		JungNode n1 = (JungNode) jung.getGraph().getVertices().toArray()[0];
		JungNode n4 = (JungNode) jung.getGraph().getVertices().toArray()[3];
	    @SuppressWarnings("unused")
		List<JungEdge> l = alg.getPath(n1, (JungNode) n4);
	    
//	    System.out.println("The shortest unweighted path from" + n1 + " to " + n4 + " is:");
//	    System.out.println(l.toString());
		assertTrue(true);
	}
	
	@Test
	public void test1() throws IOException {
		BpmnProcess process = BpmnBuilder.getLoopExample();
		process.save("C:\\Users\\Vincent\\workspace\\processGenerator\\gen\\vincent.bpmn");
		
		JungProcess jung = new JungProcess(process);
		for (FlowElement element : process.getProcess().getFlowElements()) {
			if (element instanceof ExclusiveGateway && ((ExclusiveGateway) element).getGatewayDirection().equals(GatewayDirection.CONVERGING)) {
				System.out.println("on a trouvé une porte fermante");
				DijkstraShortestPath<JungNode, JungEdge> algo = new DijkstraShortestPath<JungNode, JungEdge>(jung.getGraph());
				List<JungEdge> liste = algo.getPath(jung.getVertex(element.getId()), jung.getVertex(process.getTwin(element.getId()).getId()));
				System.out.println("il y a un chemin du merge vers le choice ? " + !liste.isEmpty());
				
			}
		}
		
	}
}
