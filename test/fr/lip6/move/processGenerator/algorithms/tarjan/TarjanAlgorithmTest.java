package fr.lip6.move.processGenerator.algorithms.tarjan;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import fr.lip6.move.processGenerator.bpmn2.BpmnBuilder;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;


public class TarjanAlgorithmTest {

//	@Test
	public void test() throws IOException {
		BpmnProcess process = BpmnBuilder.instance.initialABFinal();
		process.save(System.getProperty("user.home") + File.separator + "./workspace/processGenerator/gen/vincent.bpmn");
		TarjanAlgorithm algo = new TarjanAlgorithm();
		Graph graph = new Graph(process);
		
		List<Vertex> liste = algo.tarjan(graph);
		
		System.out.println(liste);
		
		assertTrue(liste.isEmpty());
	}
	
	@Test
	public void test1() throws IOException {
		BpmnProcess process = BpmnBuilder.instance.getLoopExample();
		process.save(System.getProperty("user.home") + File.separator + "./workspace/processGenerator/gen/vincent.bpmn");
		TarjanAlgorithm algo = new TarjanAlgorithm();
		Graph graph = new Graph(process);
		
		List<Vertex> liste = algo.tarjan(graph);
		
		System.out.println(liste);
		
		assertTrue(liste.isEmpty());
	}
}
