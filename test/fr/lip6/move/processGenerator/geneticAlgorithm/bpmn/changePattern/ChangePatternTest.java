package fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.changePattern;

import static org.junit.Assert.assertTrue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.uncommons.maths.random.MersenneTwisterRNG;
import fr.lip6.move.processGenerator.bpmn2.BpmnBuilder;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.geneticAlgorithm.IChangePattern;
import fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.BpmnMutationOperation;
import fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.IBpmnChangePattern;


public class ChangePatternTest {
	
	private Random rng;
	
	@Before
	public void before() {
		rng = new MersenneTwisterRNG();
	}
	
	@After
	public void after() {
		rng = null;
	}

	public void test(IBpmnChangePattern changePattern) throws IOException {

		BpmnProcess process = BpmnBuilder.initialFinal();
		changePattern.apply(process, rng);
		process.save("C:\\Users\\Vincent\\workspace\\processGenerator\\test.bpmn");
		changePattern.apply(process, rng);
		process.save("C:\\Users\\Vincent\\workspace\\processGenerator\\test2.bpmn");
		changePattern.apply(process, rng);
		process.save("C:\\Users\\Vincent\\workspace\\processGenerator\\test3.bpmn");
		assertTrue(true);
	}
	
	@Test
	public void test0() throws IOException {
//		test(new BpmnSerialInsert());
//		test(new BpmnConditionalInsert());
	}
	
	@Test
	public void test1() throws IOException {
//		IBpmnChangePattern parallel = new BpmnParallelInsert();
//		IBpmnChangePattern serial = new BpmnSerialInsert();
//		BpmnProcess process = BpmnBuilder.initialFinal();
//		serial.apply(process, rng);
//		process.save("C:\\Users\\Vincent\\workspace\\processGenerator\\test.bpmn");
//		parallel.apply(process, rng);
//		process.save("C:\\Users\\Vincent\\workspace\\processGenerator\\test2.bpmn");
//		parallel.apply(process, rng);
//		process.save("C:\\Users\\Vincent\\workspace\\processGenerator\\test3.bpmn");
//		assertTrue(true);
	}

	@Test
	public void test2() throws Exception {
		
		List<IChangePattern> listeChangePattern = new ArrayList<IChangePattern>();
		listeChangePattern.add(EBpmnChangePattern.PARALLEL_INSERT.newInstance("2"));
		listeChangePattern.add(EBpmnChangePattern.CONDITIONAL_INSERT.newInstance("2"));
		listeChangePattern.add(EBpmnChangePattern.SERIAL_INSERT.newInstance("2"));
		
		BpmnMutationOperation mutation = new BpmnMutationOperation(listeChangePattern);
		List<BpmnProcess> listeCandidat = new ArrayList<BpmnProcess>();
		listeCandidat.add(BpmnBuilder.initialFinal());
		listeCandidat = mutation.apply(listeCandidat , rng);
		listeCandidat = mutation.apply(listeCandidat , rng);
		listeCandidat = mutation.apply(listeCandidat , rng);
		listeCandidat = mutation.apply(listeCandidat , rng);
		listeCandidat = mutation.apply(listeCandidat , rng);
		
		listeCandidat.get(0).save("C:\\Users\\Vincent\\workspace\\processGenerator\\test.bpmn");
	}
}
