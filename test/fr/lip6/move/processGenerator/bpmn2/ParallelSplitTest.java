package fr.lip6.move.processGenerator.bpmn2;

import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import fr.lip6.move.processGenerator.file.bpmn2.BpmnBuilder;
import fr.lip6.move.processGenerator.file.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.file.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.file.bpmn2.workflowPattern.WPCheckerBpmn;
import fr.lip6.move.processGenerator.workflowPattern.Quantity;


public class ParallelSplitTest {

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
		WPCheckerBpmn sequence = new WPCheckerBpmn(process, "parallelSplit");
		sequence.check(Quantity.EQUAL, -1);
	}

	@Test
	public void test1() throws BpmnException {

		// init du process
		process = BpmnBuilder.initialFinal();

		// init du workflow checker
		WPCheckerBpmn sequence = new WPCheckerBpmn(process, "parallelSplit");
		assertTrue(sequence.check(Quantity.EQUAL, 0));
	}

	@Test
	public void test2() throws BpmnException {

		// init du process
		process = BpmnBuilder.createExampleWithParallel();

		// init du workflow checker
		WPCheckerBpmn sequence = new WPCheckerBpmn(process, "parallelSplit");
		assertTrue(sequence.check(Quantity.EQUAL, 1));
	}
}
