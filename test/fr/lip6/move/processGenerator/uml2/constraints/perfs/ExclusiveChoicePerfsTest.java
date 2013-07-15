package fr.lip6.move.processGenerator.uml2.constraints.perfs;

import static org.junit.Assert.assertTrue;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import fr.lip6.move.processGenerator.EQuantity;
import fr.lip6.move.processGenerator.constraint.IStructuralConstraint;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.uml2.UmlBuilder;
import fr.lip6.move.processGenerator.uml2.UmlProcess;
import fr.lip6.move.processGenerator.uml2.constraints.impl.UmlExclusiveChoice;
import fr.lip6.move.processGenerator.uml2.constraints.impl.UmlExclusiveChoiceOcl;

@SuppressWarnings("deprecation")
public class ExclusiveChoicePerfsTest {
	
	private UmlProcess process;
	
	public void test(IStructuralConstraint constraint, String name, boolean bool) throws Exception {
		long startTime = System.nanoTime();
		
		// init du process
		process = UmlBuilder.instance.initialFinal();
		StructuralConstraintChecker checker = new StructuralConstraintChecker(constraint, EQuantity.EQUAL, 0);
		assertTrue(checker.check(process));
		
		process = UmlBuilder.instance.initialABFinal();
		checker = new StructuralConstraintChecker(constraint, EQuantity.EQUAL, 0);
		assertTrue(checker.check(process));
		
		process = UmlBuilder.instance.initialABCFinal();
		checker = new StructuralConstraintChecker(constraint, EQuantity.EQUAL, 0);
		assertTrue(checker.check(process));
		
		process = UmlBuilder.instance.buildDecisionExample();
		checker = new StructuralConstraintChecker(constraint, EQuantity.EQUAL, 1);
		assertTrue(checker.check(process));
		
		process = UmlBuilder.instance.buildForkExample();
		checker = new StructuralConstraintChecker(constraint, EQuantity.EQUAL, 0);
		assertTrue(checker.check(process));
		
		long estimatedTime = System.nanoTime() - startTime;
		if (bool)
			System.out.print(estimatedTime + ";");
	}
	
	@Test
	public void testOCL() throws Exception {
		// on fait 2 exec pour que les objets soient initialisés correctement et ne fausse pas les test par rapport au
		// Java
		IStructuralConstraint c1 = new UmlExclusiveChoiceOcl();
		this.test(c1, "OCL", false);
		this.test(c1, "OCL", true);
		
		IStructuralConstraint c2 = new UmlExclusiveChoice();
		this.test(c2, "Java", false);
		this.test(c2, "Java", true);
	}
	
	@BeforeClass
	public static void before() {
		System.out.print("ExclusiveChoice;");
	}
	
	@AfterClass
	public static void after() {
		System.out.println();
	}
}
