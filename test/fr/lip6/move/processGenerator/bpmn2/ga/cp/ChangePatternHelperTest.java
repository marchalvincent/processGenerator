package fr.lip6.move.processGenerator.bpmn2.ga.cp;

import static org.junit.Assert.assertTrue;
import java.io.IOException;
import org.junit.Test;
import org.uncommons.maths.random.MersenneTwisterRNG;
import fr.lip6.move.processGenerator.Utils;
import fr.lip6.move.processGenerator.bpmn2.BpmnBuilder;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;

public class ChangePatternHelperTest {
	
	// @Test
	public void test() throws IOException {
		BpmnProcess process = BpmnBuilder.instance.createExampleWithUselessParallel();
		process.save(System.getProperty("user.home") + Utils.projectPath + "gen/vincentAvant.bpmn");
		process = new BpmnRemove().apply(process, new MersenneTwisterRNG(), null);
		process.save(System.getProperty("user.home") + Utils.projectPath + "gen/vincentApres.bpmn");
		assertTrue(true);
	}
	
	// @Test
	public void test2() throws IOException {
		BpmnProcess process = BpmnBuilder.instance.createExampleWithUselessExclusive();
		process.save(System.getProperty("user.home") + Utils.projectPath + "gen/vincentAvant.bpmn");
		process = new BpmnRemove().apply(process, new MersenneTwisterRNG(), null);
		process.save(System.getProperty("user.home") + Utils.projectPath + "gen/vincentApres.bpmn");
		assertTrue(true);
	}
	
	@Test
	public void test3() throws IOException {
		BpmnProcess process = BpmnBuilder.instance.getUselessLoopExample();
		process.save(System.getProperty("user.home") + Utils.projectPath + "gen/vincentAvant.bpmn");
		process = new BpmnRemove().apply(process, new MersenneTwisterRNG(), null);
		process.save(System.getProperty("user.home") + Utils.projectPath + "gen/vincentApres.bpmn");
		assertTrue(true);
	}
}
