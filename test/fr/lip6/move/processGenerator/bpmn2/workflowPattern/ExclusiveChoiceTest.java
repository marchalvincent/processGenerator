package fr.lip6.move.processGenerator.bpmn2.workflowPattern;

import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import fr.lip6.move.processGenerator.EQuantity;
import fr.lip6.move.processGenerator.bpmn2.BpmnBuilder;
import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.structuralConstraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.structuralConstraint.bpmn.BpmnExclusiveChoice;


public class ExclusiveChoiceTest {

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
	public void test0() throws Exception {
		StructuralConstraintChecker checker = new StructuralConstraintChecker(new BpmnExclusiveChoice(), EQuantity.EQUAL, -1);
		checker.check(process.getProcess());
	}

	@Test
	public void test1() throws Exception {

		// init du process
		process = BpmnBuilder.initialFinal();

		// init du workflow checker
		StructuralConstraintChecker checker = new StructuralConstraintChecker(new BpmnExclusiveChoice(), EQuantity.EQUAL, 0);
		assertTrue(checker.check(process.getProcess()));
	}

	@Test
	public void test2() throws Exception {

		// init du process
		process = BpmnBuilder.createExampleWithExclusiveChoice();

		// init du workflow checker
		StructuralConstraintChecker checker = new StructuralConstraintChecker(new BpmnExclusiveChoice(), EQuantity.EQUAL, 1);
		assertTrue(checker.check(process.getProcess()));
	}
}
