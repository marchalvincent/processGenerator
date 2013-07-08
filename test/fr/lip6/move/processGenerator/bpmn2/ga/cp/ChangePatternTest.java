package fr.lip6.move.processGenerator.bpmn2.ga.cp;

import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.uncommons.maths.random.MersenneTwisterRNG;
import fr.lip6.move.processGenerator.EQuantity;
import fr.lip6.move.processGenerator.bpmn2.BpmnBuilder;
import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.bpmn2.constraints.BpmnArbitraryCycle;
import fr.lip6.move.processGenerator.bpmn2.constraints.BpmnExclusiveChoice;
import fr.lip6.move.processGenerator.bpmn2.ga.BpmnMutationOperation;
import fr.lip6.move.processGenerator.constraint.IStructuralConstraint;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.ga.IChangePattern;

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
	
	private void test(IChangePattern<BpmnProcess> changePattern) throws IOException {
		
		BpmnProcess process = BpmnBuilder.instance.createExampleWithParallel();
		process.save(System.getProperty("user.home") + File.separator + "./workspace/processGenerator/gen/vincent.bpmn");
		process = changePattern.apply(process, rng, null);
		process.save(System.getProperty("user.home") + File.separator + "./workspace/processGenerator/gen/test.bpmn");
		process = changePattern.apply(process, rng, null);
		process.save(System.getProperty("user.home") + File.separator + "./workspace/processGenerator/gen/test2.bpmn");
		process = changePattern.apply(process, rng, null);
		process.save(System.getProperty("user.home") + File.separator + "./workspace/processGenerator/gen/test3.bpmn");
		assertTrue(true);
	}
	
	// @Test
	public void Appliquer3fois() throws IOException {
		// test(new BpmnSerialInsert());
		// test(new BpmnConditionalInsert());
		test(new BpmnParallelInsert());
	}
	
	@Test
	public void test1() throws IOException {
		IChangePattern<BpmnProcess> parallel = new BpmnParallelInsert();
		IChangePattern<BpmnProcess> serial = new BpmnSerialInsert();
		IChangePattern<BpmnProcess> thread = new BpmnThreadInsert();
		
		BpmnProcess process = BpmnBuilder.instance.initialFinal();
		process = serial.apply(process, rng, null);
		process.save(System.getProperty("user.home") + File.separator + "./workspace/processGenerator/gen/test1.bpmn");
		process = parallel.apply(process, rng, null);
		process.save(System.getProperty("user.home") + File.separator + "./workspace/processGenerator/gen/test2.bpmn");
		process = thread.apply(process, rng, null);
		process.save(System.getProperty("user.home") + File.separator + "./workspace/processGenerator/gen/test3.bpmn");
		assertTrue(true);
	}
	
	// @Test
	public void test2() throws Exception {
		
		List<IChangePattern<BpmnProcess>> listeChangePattern = new ArrayList<>();
		listeChangePattern.add(EBpmnChangePattern.PARALLEL_INSERT.newInstance("2"));
		listeChangePattern.add(EBpmnChangePattern.CONDITIONAL_INSERT.newInstance("2"));
		listeChangePattern.add(EBpmnChangePattern.SERIAL_INSERT.newInstance("2"));
		
		BpmnMutationOperation mutation = new BpmnMutationOperation(listeChangePattern, null);
		List<BpmnProcess> listeCandidat = new ArrayList<BpmnProcess>();
		listeCandidat.add(BpmnBuilder.instance.initialFinal());
		listeCandidat = mutation.apply(listeCandidat, rng);
		listeCandidat = mutation.apply(listeCandidat, rng);
		listeCandidat = mutation.apply(listeCandidat, rng);
		listeCandidat = mutation.apply(listeCandidat, rng);
		listeCandidat = mutation.apply(listeCandidat, rng);
		
		listeCandidat.get(0)
				.save(System.getProperty("user.home") + File.separator + "./workspace/processGenerator/gen/test.bpmn");
	}
	
	// @Test
	public void testWorkflowInsert() throws IOException, BpmnException {
		
		BpmnProcess process = BpmnBuilder.instance.initialABFinal();
		process.save(System.getProperty("user.home") + File.separator + "./workspace/processGenerator/gen/test.bpmn");
		
		IStructuralConstraint contrainte = new BpmnArbitraryCycle();
		StructuralConstraintChecker checker = new StructuralConstraintChecker(contrainte, EQuantity.EQUAL, 1, 1);
		IStructuralConstraint contrainte2 = new BpmnExclusiveChoice();
		StructuralConstraintChecker checker2 = new StructuralConstraintChecker(contrainte2, EQuantity.EQUAL, 1, 2);
		
		List<StructuralConstraintChecker> list = new ArrayList<>();
		list.add(checker);
		list.add(checker2);
		
		process = new BpmnWorkflowInsert().apply(process, rng, list);
		process.save(System.getProperty("user.home") + File.separator + "./workspace/processGenerator/gen/test2.bpmn");
		assertTrue(true);
	}
}
