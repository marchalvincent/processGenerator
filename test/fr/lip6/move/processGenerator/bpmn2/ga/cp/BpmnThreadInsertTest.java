package fr.lip6.move.processGenerator.bpmn2.ga.cp;

import static org.junit.Assert.*;
import java.util.Random;
import org.junit.Test;
import org.uncommons.maths.random.MersenneTwisterRNG;
import fr.lip6.move.processGenerator.bpmn2.BpmnBuilder;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;

/**
 * Teste l'insertion d'un nouveau thread.
 * 
 * @author Vincent
 * 
 */
public class BpmnThreadInsertTest {
	
	private Random rng = new MersenneTwisterRNG();
	
	@Test
	public void test() {
		BpmnProcess process = BpmnBuilder.instance.initialABCFinal();
		assertTrue(process.getProcess().getFlowElements().size() == 9);
		assertTrue(BpmnChangePatternHelper.instance.countActivity(process) == 3);
		assertTrue(BpmnChangePatternHelper.instance.countSequenceFlow(process) == 4);
		assertTrue(BpmnChangePatternHelper.instance.countConditionalGateway(process) == 0);
		assertTrue(BpmnChangePatternHelper.instance.countParallelGateway(process) == 0);
		
		BpmnThreadInsertRandom insert = new BpmnThreadInsertRandom();
		process = insert.apply(process, rng, null);
		
		assertTrue(process.getProcess().getFlowElements().size() == 15);
		assertTrue(BpmnChangePatternHelper.instance.countActivity(process) == 4);
		assertTrue(BpmnChangePatternHelper.instance.countSequenceFlow(process) == 7);
		assertTrue(BpmnChangePatternHelper.instance.countConditionalGateway(process) == 0);
		assertTrue(BpmnChangePatternHelper.instance.countParallelGateway(process) == 1);
	}
}
