package fr.lip6.move.processGenerator.bpmn2.constraints;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import fr.lip6.move.processGenerator.EQuantity;
import fr.lip6.move.processGenerator.bpmn2.BpmnBuilder;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.bpmn2.constraints.impl.BpmnSequence;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;

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
	
	@Test(expected = Exception.class)
	public void test0() throws Exception {
		StructuralConstraintChecker checker = new StructuralConstraintChecker(new BpmnSequence(), EQuantity.EQUAL, -1);
		checker.check(process);
	}
	
	@Test
	public void test1() throws Exception {
		
		// init du process
		process = BpmnBuilder.instance.initialFinal();
		
		// init du workflow checker
		StructuralConstraintChecker checker = new StructuralConstraintChecker(new BpmnSequence(), EQuantity.LESS, 1);
		assertTrue(checker.check(process));
		
		checker = new StructuralConstraintChecker(new BpmnSequence(), EQuantity.EQUAL, 0);
		assertTrue(checker.check(process));
		
		checker = new StructuralConstraintChecker(new BpmnSequence(), EQuantity.MORE, 1);
		assertFalse(checker.check(process));
		
		checker = new StructuralConstraintChecker(new BpmnSequence(), EQuantity.LESS_OR_EQUAL, 0);
		assertTrue(checker.check(process));
		
		checker = new StructuralConstraintChecker(new BpmnSequence(), EQuantity.MORE_OR_EQUAL, 1);
		assertFalse(checker.check(process));
	}
	
	@Test
	public void test2() throws Exception {
		
		// init du process
		process = BpmnBuilder.instance.initialABFinal();
		
		// init du workflow checker
		StructuralConstraintChecker checker = new StructuralConstraintChecker(new BpmnSequence(), EQuantity.EQUAL, 1);
		assertTrue(checker.check(process));
		
		checker = new StructuralConstraintChecker(new BpmnSequence(), EQuantity.LESS, 1);
		assertFalse(checker.check(process));
		
		checker = new StructuralConstraintChecker(new BpmnSequence(), EQuantity.MORE, 1);
		assertFalse(checker.check(process));
		
		checker = new StructuralConstraintChecker(new BpmnSequence(), EQuantity.MORE_OR_EQUAL, 1);
		assertTrue(checker.check(process));
		
		checker = new StructuralConstraintChecker(new BpmnSequence(), EQuantity.LESS_OR_EQUAL, 0);
		assertFalse(checker.check(process));
	}
	
	@Test
	public void test3() throws Exception {
		
		BpmnProcess process = BpmnBuilder.instance.createExampleWithParallel();
		
		// init du workflow checker
		StructuralConstraintChecker checker = new StructuralConstraintChecker(new BpmnSequence(), EQuantity.EQUAL, 2);
		assertTrue(checker.check(process));
		
		checker = new StructuralConstraintChecker(new BpmnSequence(), EQuantity.LESS, 2);
		assertFalse(checker.check(process));
		
		checker = new StructuralConstraintChecker(new BpmnSequence(), EQuantity.MORE, 2);
		assertFalse(checker.check(process));
		
		checker = new StructuralConstraintChecker(new BpmnSequence(), EQuantity.LESS_OR_EQUAL, 2);
		assertTrue(checker.check(process));
		
		checker = new StructuralConstraintChecker(new BpmnSequence(), EQuantity.MORE_OR_EQUAL, 3);
		assertFalse(checker.check(process));
	}
}
