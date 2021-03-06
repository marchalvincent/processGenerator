package fr.lip6.move.processGenerator.uml2.constraints;

import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import fr.lip6.move.processGenerator.EQuantity;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.uml2.UmlBuilder;
import fr.lip6.move.processGenerator.uml2.UmlProcess;
import fr.lip6.move.processGenerator.uml2.constraints.impl.UmlSynchronization;

/**
 * Teste le workflow pattern {@link UmlSynchronization}.
 * 
 * @author Vincent
 * 
 */
public class UmlSynchronizationTest {
	
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
		StructuralConstraintChecker checker = new StructuralConstraintChecker(new UmlSynchronization(), EQuantity.EQUAL, -1);
		checker.check(process);
	}
	
	@Test
	public void test1() throws Exception {
		
		// init du process
		process = UmlBuilder.instance.initialFinal();
		
		// init du workflow checker
		StructuralConstraintChecker checker = new StructuralConstraintChecker(new UmlSynchronization(), EQuantity.EQUAL, 0);
		assertTrue(checker.check(process));
	}
	
	@Test
	public void test2() throws Exception {
		
		// init du process
		process = UmlBuilder.instance.buildForkExample();
		
		// init du workflow checker
		StructuralConstraintChecker checker = new StructuralConstraintChecker(new UmlSynchronization(), EQuantity.EQUAL, 1);
		assertTrue(checker.check(process));
	}
	
	@Test
	public void test3() throws Exception {
		
		// init du process
		process = UmlBuilder.instance.buildDecisionExample();
		
		// init du workflow checker
		StructuralConstraintChecker checker = new StructuralConstraintChecker(new UmlSynchronization(), EQuantity.EQUAL, 0);
		assertTrue(checker.check(process));
	}
}
