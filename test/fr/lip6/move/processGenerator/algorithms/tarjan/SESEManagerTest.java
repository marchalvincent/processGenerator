package fr.lip6.move.processGenerator.algorithms.tarjan;

import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.List;
import org.eclipse.bpmn2.Gateway;
import org.junit.Test;
import fr.lip6.move.processGenerator.bpmn2.BpmnBuilder;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.bpmn2.utils.Filter;
import fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.changePattern.SESEManager;


public class SESEManagerTest {

	@Test
	public void test() throws IOException {
		BpmnProcess process = BpmnBuilder.getExampleForSESE2();
		process.save("C:/Users/Vincent/workspace/processGenerator/gen/vincent.bpmn");
		List<Gateway> list = Filter.byType(Gateway.class, process.getProcess().getFlowElements());
		for (Gateway gateway : list) {
			Gateway twin = SESEManager.instance.findTwinGateway(process, gateway);
			if (twin != null)
				System.out.println("The twin of " + gateway.getId() + " is " + twin.getId());
			else 
				System.out.println("No twin found for " + gateway.getId());
		}

		assertTrue(true);
	}
}
