package fr.lip6.move.processGenerator.geneticAlgorithm.bpmn;

import org.uncommons.watchmaker.framework.EvolutionObserver;
import org.uncommons.watchmaker.framework.PopulationData;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.views.ProcessGeneratorView;


public class BpmnEvolutionObserver implements EvolutionObserver<BpmnProcess> {

	private ProcessGeneratorView view;
	
	public BpmnEvolutionObserver(ProcessGeneratorView view) {
		super();
		this.view = view;
	}

	@Override
	public void populationUpdate(PopulationData<? extends BpmnProcess> data) {
		view.print("Generation " + data.getGenerationNumber() + ": " + data.getBestCandidateFitness());
	}
}
