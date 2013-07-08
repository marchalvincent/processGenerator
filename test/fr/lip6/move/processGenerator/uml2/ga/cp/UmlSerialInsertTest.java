package fr.lip6.move.processGenerator.uml2.ga.cp;

import static org.junit.Assert.assertTrue;
import java.util.Random;
import org.junit.Test;
import org.uncommons.maths.random.MersenneTwisterRNG;
import fr.lip6.move.processGenerator.uml2.UmlBuilder;
import fr.lip6.move.processGenerator.uml2.UmlProcess;

/**
 * Teste l'insertion d'une Action dans le process.
 * @author Vincent
 *
 */
public class UmlSerialInsertTest {
	
	private Random rng = new MersenneTwisterRNG();
	
	@Test
	public void test() {
		UmlProcess process = UmlBuilder.instance.initialFinal();
		assertTrue(UmlChangePatternHelper.instance.countAction(process) == 0);
		assertTrue(UmlChangePatternHelper.instance.countControlNode(process) == 2);
		assertTrue(process.getActivity().getEdges().size() == 1);
		
		UmlSerialInsert insert = new UmlSerialInsert();
		process = insert.apply(process, rng, null);

		assertTrue(UmlChangePatternHelper.instance.countAction(process) == 1);
		assertTrue(UmlChangePatternHelper.instance.countControlNode(process) == 2);
		assertTrue(process.getActivity().getEdges().size() == 2);

		process = insert.apply(process, rng, null);

		assertTrue(UmlChangePatternHelper.instance.countAction(process) == 2);
		assertTrue(UmlChangePatternHelper.instance.countControlNode(process) == 2);
		assertTrue(process.getActivity().getEdges().size() == 3);
	}
	
	@Test
	public void t() {
		UmlBuilder.instance.initialFinal().save(System.getProperty("user.home") + "/documents/workspace/processGenerator/gen/vincent.uml");
	}
}
