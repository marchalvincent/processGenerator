package fr.lip6.move.processGenerator.bpmn2.jung;

import static org.junit.Assert.assertFalse;
import java.io.IOException;
import java.util.List;
import org.eclipse.bpmn2.ExclusiveGateway;
import org.eclipse.bpmn2.GatewayDirection;
import org.junit.Test;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import fr.lip6.move.processGenerator.bpmn2.BpmnBuilder;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.bpmn2.utils.Filter;


public class JungManagerTest {

	@Test
	public void test1() throws IOException {
		BpmnProcess process = BpmnBuilder.instance.getLoopExample();
		//		process.save(System.getProperty("user.home") + File.separator + "./workspace/processGenerator/gen/vincent.bpmn");

		JungProcess jung = new JungProcess(process);

		List<ExclusiveGateway> list = Filter.byType(ExclusiveGateway.class, process.getProcess().getFlowElements(), GatewayDirection.CONVERGING);
		for (ExclusiveGateway exclusive : list) {
			DijkstraShortestPath<JungVertex, JungEdge> algo = new DijkstraShortestPath<JungVertex, JungEdge>(jung.getGraph());

			String convergeId = exclusive.getId();
			String divergeId = exclusive.getOutgoing().get(0).getTargetRef().getId();
			List<JungEdge> liste = algo.getPath(jung.getVertex(divergeId), jung.getVertex(convergeId));

			assertFalse(liste.isEmpty());
		}

	}
}
