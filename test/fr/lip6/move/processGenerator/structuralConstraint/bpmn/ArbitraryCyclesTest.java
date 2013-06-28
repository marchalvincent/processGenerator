package fr.lip6.move.processGenerator.structuralConstraint.bpmn;

import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import fr.lip6.move.processGenerator.EQuantity;
import fr.lip6.move.processGenerator.bpmn2.BpmnBuilder;
import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.structuralConstraint.StructuralConstraintChecker;


public class ArbitraryCyclesTest {

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
		StructuralConstraintChecker checker = new StructuralConstraintChecker(new BpmnArbitraryCycle(), EQuantity.EQUAL, -1);
		checker.check(process);
	}

	@Test
	public void test1() throws Exception {

		// init du process
		process = BpmnBuilder.instance.initialFinal();

		// init du workflow checker
		StructuralConstraintChecker checker = new StructuralConstraintChecker(new BpmnArbitraryCycle(), EQuantity.EQUAL, 0);
		assertTrue(checker.check(process));
	}

	@Test
	public void test2() throws Exception {

		// init du process
		process = BpmnBuilder.instance.getLoopExample();

		// init du workflow checker
		StructuralConstraintChecker checker = new StructuralConstraintChecker(new BpmnArbitraryCycle(), EQuantity.EQUAL, 1);
		assertTrue(checker.check(process));
	}

	@Test
	public void test3() throws Exception {

		// init du process
		process = BpmnBuilder.instance.getComplexLoopExample();
//		process.save(System.getProperty("user.home") + File.separator + "./workspace/processGenerator/gen/vincent.bpmn");

		// init du workflow checker
		StructuralConstraintChecker checker = new StructuralConstraintChecker(new BpmnArbitraryCycle(), EQuantity.EQUAL, 1);
		assertTrue(checker.check(process));
	}

	@Test
	public void test4() throws Exception {

		// init du process
		process = BpmnBuilder.instance.getDoubleLoopExample();
		process.save(System.getProperty("user.home") + File.separator + "./workspace/processGenerator/gen/vincent.bpmn");

		// init du workflow checker
		StructuralConstraintChecker checker = new StructuralConstraintChecker(new BpmnArbitraryCycle(), EQuantity.EQUAL, 2);
		assertTrue(checker.check(process));
	}
}