package fr.lip6.move.processGenerator.uml2.ga.cp;

import static org.junit.Assert.assertTrue;
import java.util.Random;
import org.junit.Test;
import org.uncommons.maths.random.MersenneTwisterRNG;
import fr.lip6.move.processGenerator.uml2.UmlBuilder;
import fr.lip6.move.processGenerator.uml2.UmlProcess;


public class UmlParallelInsertTest {
	
	private Random rng = new MersenneTwisterRNG();
	
	@Test
	public void test() {
		UmlProcess process = UmlBuilder.instance.initialABFinal();
		assertTrue(UmlChangePatternHelper.instance.countAction(process) == 2);
		assertTrue(UmlChangePatternHelper.instance.countControlNode(process) == 2);
		assertTrue(process.getActivity().getEdges().size() == 3);
		
		// applique forcement la règle insertion sur une action
		UmlParallelInsertRandom insert = new UmlParallelInsertRandom();
		process = insert.apply(process, rng, null);
		
		assertTrue(UmlChangePatternHelper.instance.countAction(process) == 3);
		assertTrue(UmlChangePatternHelper.instance.countControlNode(process) == 4);
		assertTrue(process.getActivity().getEdges().size() == 7);
		
		UmlParallelInsertForkJoin insert2 = new UmlParallelInsertForkJoin();
		process = insert2.apply(process, rng, null);
		
		assertTrue(UmlChangePatternHelper.instance.countAction(process) == 4);
		assertTrue(UmlChangePatternHelper.instance.countControlNode(process) == 4);
		assertTrue(process.getActivity().getEdges().size() == 9);
		
		UmlParallelInsertAction insert3 = new UmlParallelInsertAction();
		process = insert3.apply(process, rng, null);
		
		assertTrue(UmlChangePatternHelper.instance.countAction(process) == 5);
		assertTrue(UmlChangePatternHelper.instance.countControlNode(process) == 6);
		assertTrue(process.getActivity().getEdges().size() == 13);
	}
}
