package fr.lip6.move.processGenerator.geneticAlgorithm.bpmn;

import org.uncommons.watchmaker.framework.EvolutionObserver;
import org.uncommons.watchmaker.framework.PopulationData;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;


public class BpmnEvolutionObserver implements EvolutionObserver<BpmnProcess> {

	@Override
	public void populationUpdate(PopulationData<? extends BpmnProcess> data) {
		System.out.printf("Generation %d: %s\n",
                data.getGenerationNumber(),
                data.getBestCandidateFitness());
	}
}
