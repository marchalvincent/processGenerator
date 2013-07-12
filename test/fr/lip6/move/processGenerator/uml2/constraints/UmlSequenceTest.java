package fr.lip6.move.processGenerator.uml2.constraints;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import fr.lip6.move.processGenerator.EQuantity;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.uml2.UmlBuilder;
import fr.lip6.move.processGenerator.uml2.UmlProcess;
import fr.lip6.move.processGenerator.uml2.constraints.impl.UmlSequence;

/**
 * Teste le workflow pattern {@link UmlSequence}.
 * 
 * @author Vincent
 *
 */
public class UmlSequenceTest {
	
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
		StructuralConstraintChecker checker = new StructuralConstraintChecker(new UmlSequence(), EQuantity.EQUAL, -1);
		checker.check(process);
	}
	
	@Test
	public void test1() throws Exception {
		
		// init du process
		process = UmlBuilder.instance.initialFinal();
		
		// init du workflow checker
		StructuralConstraintChecker checker = new StructuralConstraintChecker(new UmlSequence(), EQuantity.LESS, 1);
		assertTrue(checker.check(process));
		
		checker = new StructuralConstraintChecker(new UmlSequence(), EQuantity.EQUAL, 0);
		assertTrue(checker.check(process));
		
		checker = new StructuralConstraintChecker(new UmlSequence(), EQuantity.MORE, 1);
		assertFalse(checker.check(process));
		
		checker = new StructuralConstraintChecker(new UmlSequence(), EQuantity.LESS_OR_EQUAL, 0);
		assertTrue(checker.check(process));
		
		checker = new StructuralConstraintChecker(new UmlSequence(), EQuantity.MORE_OR_EQUAL, 1);
		assertFalse(checker.check(process));
	}
	
	@Test
	public void test2() throws Exception {
		
		// init du process
		process = UmlBuilder.instance.initialABFinal();
		
		// init du workflow checker
		StructuralConstraintChecker checker = new StructuralConstraintChecker(new UmlSequence(), EQuantity.EQUAL, 1);
		assertTrue(checker.check(process));
		
		checker = new StructuralConstraintChecker(new UmlSequence(), EQuantity.LESS, 1);
		assertFalse(checker.check(process));
		
		checker = new StructuralConstraintChecker(new UmlSequence(), EQuantity.MORE, 1);
		assertFalse(checker.check(process));
		
		checker = new StructuralConstraintChecker(new UmlSequence(), EQuantity.MORE_OR_EQUAL, 1);
		assertTrue(checker.check(process));
		
		checker = new StructuralConstraintChecker(new UmlSequence(), EQuantity.LESS_OR_EQUAL, 0);
		assertFalse(checker.check(process));
	}
	
	@Test
	public void test3() throws Exception {
		
		process = UmlBuilder.instance.initialABCFinal();
		
		// init du workflow checker
		StructuralConstraintChecker checker = new StructuralConstraintChecker(new UmlSequence(), EQuantity.EQUAL, 2);
		assertTrue(checker.check(process));
		
		checker = new StructuralConstraintChecker(new UmlSequence(), EQuantity.LESS, 2);
		assertFalse(checker.check(process));
		
		checker = new StructuralConstraintChecker(new UmlSequence(), EQuantity.MORE, 2);
		assertFalse(checker.check(process));
		
		checker = new StructuralConstraintChecker(new UmlSequence(), EQuantity.LESS_OR_EQUAL, 2);
		assertTrue(checker.check(process));
		
		checker = new StructuralConstraintChecker(new UmlSequence(), EQuantity.MORE_OR_EQUAL, 3);
		assertFalse(checker.check(process));
	}
}
