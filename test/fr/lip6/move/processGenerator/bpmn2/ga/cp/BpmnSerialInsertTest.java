package fr.lip6.move.processGenerator.bpmn2.ga.cp;

import static org.junit.Assert.*;
import java.util.Random;
import org.junit.Test;
import org.uncommons.maths.random.MersenneTwisterRNG;
import fr.lip6.move.processGenerator.bpmn2.BpmnBuilder;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;

/**
 * Teste l'insertion "serial" d'une Task
 * 
 * @author Vincent
 * 
 */
public class BpmnSerialInsertTest {
	
	private Random rng = new MersenneTwisterRNG();
	
	@Test
	public void test() {
		BpmnProcess process = BpmnBuilder.instance.initialABCFinal();
		assertTrue(BpmnChangePatternHelper.instance.countActivity(process) == 3);
		assertTrue(BpmnChangePatternHelper.instance.countSequenceFlow(process) == 4);
		assertTrue(BpmnChangePatternHelper.instance.countConditionalGateway(process) == 0);
		assertTrue(BpmnChangePatternHelper.instance.countLinkedParallelGateway(process) == 0);
		
		BpmnSerialInsert insert = new BpmnSerialInsert();
		process = insert.apply(process, rng, null);
		
		assertTrue(BpmnChangePatternHelper.instance.countActivity(process) == 4);
		assertTrue(BpmnChangePatternHelper.instance.countSequenceFlow(process) == 5);
		assertTrue(BpmnChangePatternHelper.instance.countConditionalGateway(process) == 0);
		assertTrue(BpmnChangePatternHelper.instance.countLinkedParallelGateway(process) == 0);
	}
}
