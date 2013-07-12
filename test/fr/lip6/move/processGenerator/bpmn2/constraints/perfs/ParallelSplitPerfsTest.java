package fr.lip6.move.processGenerator.bpmn2.constraints.perfs;

import static org.junit.Assert.assertTrue;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import fr.lip6.move.processGenerator.EQuantity;
import fr.lip6.move.processGenerator.bpmn2.BpmnBuilder;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.bpmn2.constraints.impl.BpmnParallelSplit;
import fr.lip6.move.processGenerator.bpmn2.constraints.impl.BpmnParallelSplitOcl;
import fr.lip6.move.processGenerator.constraint.IStructuralConstraint;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;


@SuppressWarnings("deprecation")
public class ParallelSplitPerfsTest {
	
	private BpmnProcess process;
	
	public void test(IStructuralConstraint constraint, String name, boolean bool) throws Exception {
		long startTime = System.nanoTime();

		// init du process
		process = BpmnBuilder.instance.initialFinal();
		StructuralConstraintChecker checker = new StructuralConstraintChecker(constraint, EQuantity.EQUAL, 0);
		assertTrue(checker.check(process));
		
		process = BpmnBuilder.instance.initialABFinal();
		checker = new StructuralConstraintChecker(constraint, EQuantity.EQUAL, 0);
		assertTrue(checker.check(process));
		
		process = BpmnBuilder.instance.initialABCFinal();
		checker = new StructuralConstraintChecker(constraint, EQuantity.EQUAL, 0);
		assertTrue(checker.check(process));
		
		process = BpmnBuilder.instance.createExampleWithExclusiveChoice();
		checker = new StructuralConstraintChecker(constraint, EQuantity.EQUAL, 0);
		assertTrue(checker.check(process));
		
		process = BpmnBuilder.instance.createExampleWithMultiChoiceMultiMerge();
		checker = new StructuralConstraintChecker(constraint, EQuantity.EQUAL, 0);
		assertTrue(checker.check(process));
		
		process = BpmnBuilder.instance.createExampleWithParallel();
		checker = new StructuralConstraintChecker(constraint, EQuantity.EQUAL, 1);
		assertTrue(checker.check(process));
		
		process = BpmnBuilder.instance.createExampleWithParallel2();
		checker = new StructuralConstraintChecker(constraint, EQuantity.EQUAL, 1);
		assertTrue(checker.check(process));
		
		process = BpmnBuilder.instance.createExampleWithStructuredSynchronizingMerge();
		checker = new StructuralConstraintChecker(constraint, EQuantity.EQUAL, 0);
		assertTrue(checker.check(process));
		
		process = BpmnBuilder.instance.createExampleWithUselessExclusive();
		checker = new StructuralConstraintChecker(constraint, EQuantity.EQUAL, 0);
		assertTrue(checker.check(process));
		
		process = BpmnBuilder.instance.createExampleWithUselessParallel();
		checker = new StructuralConstraintChecker(constraint, EQuantity.EQUAL, 1);
		assertTrue(checker.check(process));
		
		process = BpmnBuilder.instance.createExampleWithUselessParallelAndExclusive();
		checker = new StructuralConstraintChecker(constraint, EQuantity.EQUAL, 1);
		assertTrue(checker.check(process));
		
		process = BpmnBuilder.instance.getComplexLoopExample();
		checker = new StructuralConstraintChecker(constraint, EQuantity.EQUAL, 0);
		assertTrue(checker.check(process));
		
		process = BpmnBuilder.instance.getDoubleLoopExample();
		checker = new StructuralConstraintChecker(constraint, EQuantity.EQUAL, 0);
		assertTrue(checker.check(process));
		
		process = BpmnBuilder.instance.getExampleForSESE();
		checker = new StructuralConstraintChecker(constraint, EQuantity.EQUAL, 0);
		assertTrue(checker.check(process));
		
		process = BpmnBuilder.instance.getExampleForSESE2();
		checker = new StructuralConstraintChecker(constraint, EQuantity.EQUAL, 0);
		assertTrue(checker.check(process));
		
		process = BpmnBuilder.instance.getLoopExample();
		checker = new StructuralConstraintChecker(constraint, EQuantity.EQUAL, 0);
		assertTrue(checker.check(process));
		
		process = BpmnBuilder.instance.getUselessLoopExample();
		checker = new StructuralConstraintChecker(constraint, EQuantity.EQUAL, 0);
		assertTrue(checker.check(process));
		
		long estimatedTime = System.nanoTime() - startTime;
		if (bool)
			System.out.print(estimatedTime + ";");
	}

	@Test
	public void testOCL() throws Exception {
		// on fait 2 exec pour que les objets soient initialisés correctement et ne fausse pas les test par rapport au Java
		IStructuralConstraint c1 = new BpmnParallelSplitOcl();
		this.test(c1, "OCL", false);
		this.test(c1, "OCL", true);

		IStructuralConstraint c2 = new BpmnParallelSplit();
		this.test(c2, "Java", false);
		this.test(c2, "Java", true);
	}
	
	@BeforeClass
	public static void before() {
		System.out.print("ParallelSplit;");
	}
	
	@AfterClass
	public static void after() {
		System.out.println();
	}
}
