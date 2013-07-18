package fr.lip6.move.processGenerator.uml2.ga.cp;

import static org.junit.Assert.assertTrue;
import java.util.Random;
import org.junit.Test;
import org.uncommons.maths.random.MersenneTwisterRNG;
import fr.lip6.move.processGenerator.EQuantity;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.uml2.UmlBuilder;
import fr.lip6.move.processGenerator.uml2.UmlProcess;
import fr.lip6.move.processGenerator.uml2.constraints.impl.UmlArbitraryCycle;


public class UmlLoopInsertTest {
	
	private Random rng = new MersenneTwisterRNG();
	
	@Test
	public void test() throws Exception {
		UmlProcess process = UmlBuilder.instance.initialABFinal();
		assertTrue(UmlChangePatternHelper.instance.countAction(process) == 2);
		assertTrue(UmlChangePatternHelper.instance.countControlNode(process) == 2);
		assertTrue(process.getActivity().getEdges().size() == 3);
		
		UmlLoopInsert insert = new UmlLoopInsert();
		process = insert.apply(process, rng, null);
		
		assertTrue(UmlChangePatternHelper.instance.countAction(process) == 2);
		assertTrue(UmlChangePatternHelper.instance.countControlNode(process) == 4);
		assertTrue(process.getActivity().getEdges().size() == 6);
		
		StructuralConstraintChecker checker = new StructuralConstraintChecker(new UmlArbitraryCycle(), EQuantity.EQUAL, 1);
		assertTrue(checker.check(process));
	}
}
