package fr.lip6.move.processGenerator.bpmn2.ga;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import fr.lip6.move.processGenerator.bpmn2.BpmnBuilder;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.bpmn2.ga.BpmnFitnessEvaluator;

public class BpmnFitnessEvaluatorTest {
	
	@Test
	public void testSizeFitness() {
		
		int size = 100;
		int margin = 10;
		
		BpmnFitnessEvaluator evaluator = new BpmnFitnessEvaluator(size, margin, null, null, null, null);
		double precedentFitness = 0, fitness;
		for (int i = 0; i < 202; i++) {
			BpmnProcess process = BpmnBuilder.instance.numberNodes(i);
			fitness = evaluator.getSizeFitness(evaluator.getSizeCandidate(process));
//			System.out.println(i + ";" + fitness*100);
			
			if (i < 100) {
				// ici la fonction de fitness est croissante
				assertTrue(precedentFitness <= fitness);
			} else {
				// ici la fonction de fitness est décroissante
				assertTrue(fitness <= precedentFitness);
			}
			precedentFitness = fitness;
		}
	}
	
	/**
	 * Permet de test le cas ou la margin est supérieure à 100%
	 */
	@Test
	public void testSizeFitness2() {
		
		int size = 10;
		int margin = 200;
		
		BpmnFitnessEvaluator evaluator = new BpmnFitnessEvaluator(size, margin, null, null, null, null);
		double precedentFitness = 0, fitness;
		for (int i = 0; i < 35; i++) {
			BpmnProcess process = BpmnBuilder.instance.numberNodes(i);
			fitness = evaluator.getSizeFitness(evaluator.getSizeCandidate(process));
			
			if (i < 10) {
				// ici la fonction de fitness est croissante
				assertTrue(precedentFitness <= fitness);
			} else {
				// ici la fonction de fitness est décroissante
				assertTrue(fitness <= precedentFitness);
			}
			precedentFitness = fitness;
		}
	}
	
	@Test
	public void test() {
		
	}
}
