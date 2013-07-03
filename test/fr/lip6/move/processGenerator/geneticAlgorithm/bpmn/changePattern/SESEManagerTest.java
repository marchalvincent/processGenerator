package fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.changePattern;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.eclipse.bpmn2.Gateway;
import org.junit.Test;

import fr.lip6.move.processGenerator.bpmn2.BpmnBuilder;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.bpmn2.ga.cp.GatewayManager;
import fr.lip6.move.processGenerator.bpmn2.utils.BpmnFilter;


public class SESEManagerTest {

	@Test
	public void test() throws IOException {
		BpmnProcess process = BpmnBuilder.instance.getExampleForSESE2();
		process.save(System.getProperty("user.home") + File.separator + "./workspace/processGenerator/gen/vincent.bpmn");
		List<Gateway> list = BpmnFilter.byType(Gateway.class, process.getProcess().getFlowElements());
		for (Gateway gateway : list) {
			Gateway twin = GatewayManager.instance.findTwinGateway(process, gateway);
			if (twin != null)
				System.out.println("The twin of " + gateway.getId() + " is " + twin.getId());
			else 
				System.out.println("No twin found for " + gateway.getId());
		}

		assertTrue(true);
	}
}
