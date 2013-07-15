package fr.lip6.move.processGenerator.bpmn2.constraints;

import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import fr.lip6.move.processGenerator.EQuantity;
import fr.lip6.move.processGenerator.bpmn2.BpmnBuilder;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.bpmn2.EBpmnElement;
import fr.lip6.move.processGenerator.bpmn2.constraints.impl.BpmnElementConstraint;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;


public class ElementConstraintTest {
	
	private BpmnProcess process;
	
	@Before
	public void before() {
		process = new BpmnProcess();
	}
	
	@After
	public void after() {
		process = null;
	}
	
	@Test(expected = Exception.class)
	public void test0() throws Exception {
		StructuralConstraintChecker checker = new StructuralConstraintChecker(new BpmnElementConstraint(EBpmnElement.END_EVENT), 
				EQuantity.EQUAL, -1);
		checker.check(process);
	}
	
	@Test
	public void test1() throws Exception {
		
		// init du process
		process = BpmnBuilder.instance.initialFinal();
		
		// init du workflow checker
		StructuralConstraintChecker checker = new StructuralConstraintChecker(new BpmnElementConstraint(EBpmnElement.TASK), 
				EQuantity.EQUAL, 0);
		assertTrue(checker.check(process));
	}
	
	@Test
	public void test2() throws Exception {
		
		// init du process
		process = BpmnBuilder.instance.getLoopExample();
		
		// init du workflow checker
		StructuralConstraintChecker checker = new StructuralConstraintChecker(new BpmnElementConstraint(EBpmnElement.START_EVENT), 
				EQuantity.EQUAL, 1);
		assertTrue(checker.check(process));
	}
	
	@Test
	public void test3() throws Exception {
		
		// init du process
		process = BpmnBuilder.instance.getComplexLoopExample();
		
		// init du workflow checker
		StructuralConstraintChecker checker = new StructuralConstraintChecker(new BpmnElementConstraint(EBpmnElement.EXCLUSIVE_GATEWAY),
				EQuantity.EQUAL, 4);
		assertTrue(checker.check(process));
	}
	
	@Test
	public void test4() throws Exception {
		
		// init du process
		process = BpmnBuilder.instance.buildSpecifiqueTask();
		
		// init du workflow checker
		StructuralConstraintChecker checker = new StructuralConstraintChecker(new BpmnElementConstraint(EBpmnElement.SCRIPT_TASK), 
				EQuantity.EQUAL, 2);
		assertTrue(checker.check(process));

		checker = new StructuralConstraintChecker(new BpmnElementConstraint(EBpmnElement.USER_TASK), 
				EQuantity.EQUAL, 1);
		assertTrue(checker.check(process));

		checker = new StructuralConstraintChecker(new BpmnElementConstraint(EBpmnElement.SERVICE_TASK), 
				EQuantity.EQUAL, 0);
		assertTrue(checker.check(process));
	}
}
