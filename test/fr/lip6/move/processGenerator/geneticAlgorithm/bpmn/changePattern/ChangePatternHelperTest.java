package fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.changePattern;

import static org.junit.Assert.assertTrue;
import java.io.IOException;
import org.junit.Test;
import org.uncommons.maths.random.MersenneTwisterRNG;
import fr.lip6.move.processGenerator.bpmn2.BpmnBuilder;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;


public class ChangePatternHelperTest {

	@Test
	public void test() throws IOException {
		BpmnProcess process = BpmnBuilder.createExampleWithParallel3();
		process.save("C:/Users/Vincent/workspace/processGenerator/gen/vincentAvant.bpmn");
		process = new BpmnRemove().apply(process, new MersenneTwisterRNG());
		process.save("C:/Users/Vincent/workspace/processGenerator/gen/vincentApres.bpmn");
		assertTrue(true);
	}
}
