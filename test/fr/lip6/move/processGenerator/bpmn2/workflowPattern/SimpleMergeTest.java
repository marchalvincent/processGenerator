package fr.lip6.move.processGenerator.bpmn2.workflowPattern;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import fr.lip6.move.processGenerator.Quantity;
import fr.lip6.move.processGenerator.bpmn2.BpmnBuilder;
import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.bpmn2.workflowPattern.WPCheckerBpmn;


public class SimpleMergeTest {

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
		WPCheckerBpmn sequence = new WPCheckerBpmn(process, "simpleMerge");
		sequence.check(Quantity.EQUAL, -1);
	}

	@Test
	public void test1() throws BpmnException {

		// init du process
		process = BpmnBuilder.initialFinal();

		// init du workflow checker
		WPCheckerBpmn sequence = new WPCheckerBpmn(process, "simpleMerge");
		assertTrue(sequence.check(Quantity.EQUAL, 0));
	}

	@Test
	public void test2() throws BpmnException {

		// init du process
		process = BpmnBuilder.createExampleWithExclusiveChoice();

		// init du workflow checker
		WPCheckerBpmn sequence = new WPCheckerBpmn(process, "simpleMerge");
		assertTrue(sequence.check(Quantity.EQUAL, 1));
	}
}
