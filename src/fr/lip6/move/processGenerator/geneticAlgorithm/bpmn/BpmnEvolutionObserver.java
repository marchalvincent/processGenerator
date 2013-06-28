package fr.lip6.move.processGenerator.geneticAlgorithm.bpmn;

import java.text.DecimalFormat;
import org.uncommons.watchmaker.framework.EvolutionObserver;
import org.uncommons.watchmaker.framework.PopulationData;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.views.ProcessGeneratorView;

/**
 * Cet observeur permet d'afficher des informations sur l'interface graphique
 * pendant l'éxecution de l'algorithme génétique.
 * @author Vincent
 *
 */
public class BpmnEvolutionObserver implements EvolutionObserver<BpmnProcess> {

	private ProcessGeneratorView view;
	
	public BpmnEvolutionObserver(ProcessGeneratorView view) {
		super();
		this.view = view;
	}

	@Override
	public void populationUpdate(PopulationData<? extends BpmnProcess> data) {
		Double best = new Double(data.getBestCandidateFitness());
		DecimalFormat df = new DecimalFormat("#.###");
		view.print("Generation " + data.getGenerationNumber() + ": " + df.format(best) + "% matches.");
	}
}
