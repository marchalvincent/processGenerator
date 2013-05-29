package fr.lip6.move.processGenerator.bpmn2;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import fr.lip6.move.processGenerator.Quantity;
import fr.lip6.move.processGenerator.bpmn2.BpmnBuilder;
import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.bpmn2.workflowPattern.WPCheckerBpmn;


public class SequenceTest {

	private BpmnProcess process;
	
	@Before
	public void before() {
		process = new BpmnProcess();
	}
	
	@After
	public void after() {
		process = null;
	}
	
	@Test(expected=BpmnException.class)
	public void test0() throws BpmnException {
		WPCheckerBpmn sequence = new WPCheckerBpmn(process, "sequence");
		sequence.check(Quantity.EQUAL, -1);
	}

	@Test
	public void test1() throws BpmnException {
		
		// init du process
		process = BpmnBuilder.initialFinal();
		
		// init du workflow checker
		WPCheckerBpmn sequence = new WPCheckerBpmn(process, "sequence");
		assertTrue(sequence.check(Quantity.LESS, 1));
		assertTrue(sequence.check(Quantity.EQUAL, 0));
		assertFalse(sequence.check(Quantity.MORE, 1));
		assertTrue(sequence.check(Quantity.LESS_OR_EQUAL, 0));
		assertFalse(sequence.check(Quantity.MORE_OR_EQUAL, 1));
	}

	@Test
	public void test2() throws BpmnException {
		
		// init du process
		process = BpmnBuilder.initialABFinal();
		
		// init du workflow checker
		WPCheckerBpmn sequence = new WPCheckerBpmn(process, "sequence");
		assertTrue(sequence.check(Quantity.EQUAL, 1));
		assertFalse(sequence.check(Quantity.LESS, 1));
		assertFalse(sequence.check(Quantity.MORE, 1));
		assertTrue(sequence.check(Quantity.MORE_OR_EQUAL, 1));
		assertFalse(sequence.check(Quantity.LESS_OR_EQUAL, 0));
	}
	
	@Test
	public void test3() throws BpmnException {

		BpmnProcess process = BpmnBuilder.createExampleWithParallel();
		
		// init du workflow checker
		WPCheckerBpmn sequence = new WPCheckerBpmn(process, "sequence");
		assertTrue(sequence.check(Quantity.EQUAL, 2));
		assertFalse(sequence.check(Quantity.LESS, 2));
		assertFalse(sequence.check(Quantity.MORE, 2));
		assertTrue(sequence.check(Quantity.LESS_OR_EQUAL, 2));
		assertFalse(sequence.check(Quantity.MORE_OR_EQUAL, 3));
	}
}
