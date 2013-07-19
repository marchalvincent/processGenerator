package fr.lip6.move.processGenerator.uml2.ga.cp;

import static org.junit.Assert.assertTrue;
import java.util.Random;
import org.junit.Test;
import org.uncommons.maths.random.MersenneTwisterRNG;
import fr.lip6.move.processGenerator.EQuantity;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.uml2.UmlBuilder;
import fr.lip6.move.processGenerator.uml2.UmlProcess;
import fr.lip6.move.processGenerator.uml2.constraints.impl.UmlExplicitTermination;
import fr.lip6.move.processGenerator.uml2.constraints.impl.UmlImplicitTermination;


public class UmlThreadInsertTest {
	
	private Random rng = new MersenneTwisterRNG();
	
	@Test
	public void testRandom() throws Exception {
		UmlProcess process = UmlBuilder.instance.initialABFinal();
		assertTrue(UmlChangePatternHelper.instance.countAction(process) == 2);
		assertTrue(UmlChangePatternHelper.instance.countControlNode(process) == 2);
		assertTrue(process.getActivity().getEdges().size() == 3);
		
		UmlThreadInsertRandom insert = new UmlThreadInsertRandom();
		process = insert.apply(process, rng, null);
		
		assertTrue(UmlChangePatternHelper.instance.countAction(process) == 3);
		assertTrue(UmlChangePatternHelper.instance.countControlNode(process) == 4);
		assertTrue(process.getActivity().getEdges().size() == 6);
		
		StructuralConstraintChecker checker = new StructuralConstraintChecker(new UmlExplicitTermination(), EQuantity.EQUAL, 1);
		// si ce n'est pas une terminaison explicit alors elle est implicite
		if (!checker.check(process)) {
			checker = new StructuralConstraintChecker(new UmlImplicitTermination(), EQuantity.EQUAL, 1);
		}
		assertTrue(checker.check(process));
	}
	
	@Test
	public void testImplicit() throws Exception {
		UmlProcess process = UmlBuilder.instance.initialABFinal();
		assertTrue(UmlChangePatternHelper.instance.countAction(process) == 2);
		assertTrue(UmlChangePatternHelper.instance.countControlNode(process) == 2);
		assertTrue(process.getActivity().getEdges().size() == 3);
		
		UmlThreadInsertImplicit insert = new UmlThreadInsertImplicit();
		process = insert.apply(process, rng, null);
		
		assertTrue(UmlChangePatternHelper.instance.countAction(process) == 3);
		assertTrue(UmlChangePatternHelper.instance.countControlNode(process) == 4);
		assertTrue(process.getActivity().getEdges().size() == 6);
		
		StructuralConstraintChecker checker = new StructuralConstraintChecker(new UmlImplicitTermination(), EQuantity.EQUAL, 1);
		assertTrue(checker.check(process));
	}
	
	@Test
	public void testExplicit() throws Exception {
		UmlProcess process = UmlBuilder.instance.initialABFinal();
		assertTrue(UmlChangePatternHelper.instance.countAction(process) == 2);
		assertTrue(UmlChangePatternHelper.instance.countControlNode(process) == 2);
		assertTrue(process.getActivity().getEdges().size() == 3);
		
		UmlThreadInsertExplicit insert = new UmlThreadInsertExplicit();
		process = insert.apply(process, rng, null);
		
		assertTrue(UmlChangePatternHelper.instance.countAction(process) == 3);
		assertTrue(UmlChangePatternHelper.instance.countControlNode(process) == 4);
		assertTrue(process.getActivity().getEdges().size() == 6);
		
		StructuralConstraintChecker checker = new StructuralConstraintChecker(new UmlExplicitTermination(), EQuantity.EQUAL, 1);
		assertTrue(checker.check(process));
	}
}
