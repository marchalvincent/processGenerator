package fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.changePattern;

import static org.junit.Assert.*;
import java.io.IOException;
import java.util.List;
import org.junit.Test;
import fr.lip6.move.processGenerator.bpmn2.BpmnBuilder;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;


public class SESESplitterTest {

	@Test
	public void test() throws IOException {

		SingleEntrySingleExitManager splitter = new SingleEntrySingleExitManager();
		BpmnProcess process = BpmnBuilder.getExampleForSESE();
//		process.save("C:/Users/Vincent/workspace/processGenerator/gen/vincent.bpmn");
		
		List<SingleEntrySingleExit> liste = splitter.getAllSESEs(process);
		
		System.out.println(liste.size());
		for (SingleEntrySingleExit singleEntrySingleExit : liste) {
			System.out.println(singleEntrySingleExit);
		}
		
		assertTrue(true);
	}
}