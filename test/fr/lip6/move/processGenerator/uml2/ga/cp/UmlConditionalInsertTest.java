package fr.lip6.move.processGenerator.uml2.ga.cp;

import static org.junit.Assert.assertTrue;
import java.util.Random;
import org.junit.Test;
import org.uncommons.maths.random.MersenneTwisterRNG;
import fr.lip6.move.processGenerator.uml2.UmlBuilder;
import fr.lip6.move.processGenerator.uml2.UmlProcess;

/**
 * Teste les 3 type de change pattern "conditional" (random, edge et decision/merge).
 * 
 * @author Vincent
 *
 */
public class UmlConditionalInsertTest {
	
	private Random rng = new MersenneTwisterRNG();
	
	@Test
	public void test() {
		UmlProcess process = UmlBuilder.instance.initialFinal();
		assertTrue(UmlChangePatternHelper.instance.countAction(process) == 0);
		assertTrue(UmlChangePatternHelper.instance.countControlNode(process) == 2);
		assertTrue(process.getActivity().getEdges().size() == 1);
		
		UmlConditionalInsertRandom insert = new UmlConditionalInsertRandom();
		process = insert.apply(process, rng, null);
		
		assertTrue(UmlChangePatternHelper.instance.countAction(process) == 1);
		assertTrue(UmlChangePatternHelper.instance.countControlNode(process) == 4);
		assertTrue(process.getActivity().getEdges().size() == 5);
		
		UmlConditionalInsertDecisionMerge insert2 = new UmlConditionalInsertDecisionMerge();
		process = insert2.apply(process, rng, null);
		
		assertTrue(UmlChangePatternHelper.instance.countAction(process) == 2);
		assertTrue(UmlChangePatternHelper.instance.countControlNode(process) == 4);
		assertTrue(process.getActivity().getEdges().size() == 7);
		
		UmlConditionalInsertEdge insert3 = new UmlConditionalInsertEdge();
		process = insert3.apply(process, rng, null);
		
		assertTrue(UmlChangePatternHelper.instance.countAction(process) == 3);
		assertTrue(UmlChangePatternHelper.instance.countControlNode(process) == 6);
		assertTrue(process.getActivity().getEdges().size() == 11);
	}
}
