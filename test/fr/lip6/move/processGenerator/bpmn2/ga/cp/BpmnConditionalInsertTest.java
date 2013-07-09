package fr.lip6.move.processGenerator.bpmn2.ga.cp;

import static org.junit.Assert.assertTrue;
import java.util.Random;
import org.junit.Test;
import org.uncommons.maths.random.MersenneTwisterRNG;
import fr.lip6.move.processGenerator.bpmn2.BpmnBuilder;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;

/**
 * Teste l'insertion en conditionelle d'une Task. 
 * @author Vincent
 *
 */
public class BpmnConditionalInsertTest {
	
	private Random rng = new MersenneTwisterRNG();
	
	@Test
	public void test() {
		BpmnProcess process = BpmnBuilder.instance.initialABCFinal();
		assertTrue(process.getProcess().getFlowElements().size() == 9);
		assertTrue(BpmnChangePatternHelper.instance.countActivity(process) == 3);
		assertTrue(BpmnChangePatternHelper.instance.countSequenceFlow(process) == 4);
		assertTrue(BpmnChangePatternHelper.instance.countConditionalGateway(process) == 0);
		assertTrue(BpmnChangePatternHelper.instance.countLinkedParallelGateway(process) == 0);
		
		BpmnConditionalInsertRandom insert = new BpmnConditionalInsertRandom();
		process = insert.apply(process, rng, null);

		assertTrue(BpmnChangePatternHelper.instance.countActivity(process) == 4);
		assertTrue(BpmnChangePatternHelper.instance.countSequenceFlow(process) == 8);
		assertTrue(BpmnChangePatternHelper.instance.countConditionalGateway(process) == 2);
		assertTrue(BpmnChangePatternHelper.instance.countLinkedParallelGateway(process) == 0);
	}
	
}
