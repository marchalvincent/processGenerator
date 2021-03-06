package fr.lip6.move.processGenerator.uml2.constraints;

import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import fr.lip6.move.processGenerator.EQuantity;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.uml2.UmlBuilder;
import fr.lip6.move.processGenerator.uml2.UmlProcess;
import fr.lip6.move.processGenerator.uml2.constraints.impl.UmlExplicitTermination;



public class UmlExplicitTerminationTest {
	
	private UmlProcess process;
	
	@Before
	public void before() {}
	
	@After
	public void after() {
		process = null;
	}
	
	@Test(expected = Exception.class)
	public void test0() throws Exception {
		process = new UmlProcess();
		StructuralConstraintChecker checker = new StructuralConstraintChecker(new UmlExplicitTermination(), EQuantity.EQUAL, -1);
		checker.check(process);
	}
	
	@Test
	public void test1() throws Exception {
		
		// init du process
		process = UmlBuilder.instance.initialFinal();
		
		// init du workflow checker
		StructuralConstraintChecker checker = new StructuralConstraintChecker(new UmlExplicitTermination(), EQuantity.EQUAL, 0);
		assertTrue(checker.check(process));
	}
	
	@Test
	public void test2() throws Exception {
		
		// init du process
		process = UmlBuilder.instance.buildExpliciteTermination();
		
		// init du workflow checker
		StructuralConstraintChecker checker = new StructuralConstraintChecker(new UmlExplicitTermination(), EQuantity.EQUAL, 1);
		assertTrue(checker.check(process));
	}
	
	@Test
	public void test3() throws Exception {
		
		// init du process
		process = UmlBuilder.instance.buildImplicitTermination();
		
		// init du workflow checker
		StructuralConstraintChecker checker = new StructuralConstraintChecker(new UmlExplicitTermination(), EQuantity.EQUAL, 0);
		assertTrue(checker.check(process));
	}
}
