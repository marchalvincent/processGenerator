package fr.lip6.move.processGenerator.ga;

import java.text.DecimalFormat;
import org.uncommons.watchmaker.framework.EvolutionObserver;
import org.uncommons.watchmaker.framework.PopulationData;
import fr.lip6.move.processGenerator.Benchmarker;
import fr.lip6.move.processGenerator.views.ProcessGeneratorView;

/**
 * Cet observeur permet d'afficher des informations sur l'interface graphique pendant l'éxecution de l'algorithme
 * génétique.
 * 
 * @author Vincent
 * 
 */
public class MyEvolutionObserver<T> implements EvolutionObserver<T> {
	
	private ProcessGeneratorView view;
	private Benchmarker bench;
	
	public MyEvolutionObserver(ProcessGeneratorView view, Benchmarker bench) {
		super();
		this.view = view;
		this.bench = bench;
	}
	
	@Override
	public void populationUpdate(PopulationData<? extends T> data) {
		Double best = new Double(data.getBestCandidateFitness());
		DecimalFormat df = new DecimalFormat("#.##");
		String bestS = df.format(best);
		view.print("Generation " + data.getGenerationNumber() + ": " + bestS + "% matches.");
		bench.tic(bestS, data.getGenerationNumber());
	}
}
