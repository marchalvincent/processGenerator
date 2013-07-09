package fr.lip6.move.processGenerator.bpmn2.ga.cp;

import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.List;
import org.eclipse.bpmn2.Gateway;
import org.junit.Test;
import fr.lip6.move.processGenerator.bpmn2.BpmnBuilder;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.bpmn2.utils.BpmnFilter;

public class GatewayManagerTest {
	
	@Test
	public void test() throws IOException {
		BpmnProcess process = BpmnBuilder.instance.getExampleForSESE2();
		List<Gateway> list = BpmnFilter.byType(Gateway.class, process.getProcess().getFlowElements());
		
		int countTwin = 0, countNoTwin = 0;
		for (Gateway gateway : list) {
			Gateway twin = GatewayManager.instance.findTwinGateway(process, gateway);
			if (twin != null) {
				countTwin++;
				System.out.println("The twin of " + gateway.getId() + " is " + twin.getId());
			}
			else {
				countNoTwin++;
				System.out.println("No twin found for " + gateway.getId());
			}
		}
		
		assertTrue(countTwin == 4 && countNoTwin == 0);
	}
}
