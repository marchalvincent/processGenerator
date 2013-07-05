package fr.lip6.move.processGenerator.ga;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;

/**
 * Cette classe se charge de la mutation des candidats entre chaque génération. Elle appliquera un change pattern selon
 * leurs probabilités.
 * 
 * @author Vincent
 *
 * @param <T> le type de candidat que doit faire évoluer cette classe.
 */
public abstract class AbstractMutationOperation<T> implements EvolutionaryOperator<T> {
	
	private List<IChangePattern<T>> changePatterns;
	private List<StructuralConstraintChecker> workflowsConstraints;
	private Random rng;
	
	protected AbstractMutationOperation(List<IChangePattern<T>> changePatterns, List<StructuralConstraintChecker> contraintesWorkflows)
			throws GeneticException {
		super();
		this.changePatterns = new ArrayList<>();
		for (IChangePattern<T> iChangePattern : changePatterns)
				this.changePatterns.add(iChangePattern);
		this.rng = new MersenneTwisterRNG();
		this.workflowsConstraints = contraintesWorkflows;
	}
	
	@Override
	public List<T> apply(List<T> selectedCandidates, Random rng) {
		
		List<T> newGeneration = new ArrayList<>();
		// pour chaque candidat...
		for (T process : selectedCandidates) {
			// on prend un changePattern au hasard
			IChangePattern<T> changePattern = this.getRandomChangePattern();
			// et on applique la mutation
			newGeneration.add(changePattern.apply(process, rng, workflowsConstraints));
		}
		return newGeneration;
	}
	
	/**
	 * Sélectionne un {@link IChangePattern} au hasard selon leurs poids. Plus le poids d'un change pattern est
	 * élevé, plus il a de chance d'être sélectionné. En revanche, la sélection se fait quand même à l'aide d'un objet
	 * Random.
	 * 
	 * @return {@link IChangePattern}.
	 */
	private IChangePattern<T> getRandomChangePattern() {
		
		// on initialise un tableau d'entier pour donner un poids à chaque probabilité
		int totalPoid = 0;
		for (IChangePattern<T> changeP : this.changePatterns) {
			totalPoid = totalPoid + changeP.getProba();
		}
		
		int cpt = 0;
		int[] randomTable = new int[totalPoid];
		// pour chaque change pattern
		for (int i = 0; i < this.changePatterns.size(); i++) {
			// on remplit le tableau selon son poid
			for (int j = 0; j < this.changePatterns.get(i).getProba(); j++) {
				randomTable[cpt] = i;
				cpt++;
			}
		}
		
		// petite vérification au cas ou
		if (cpt != totalPoid)
			System.err.println("getRandomChangePattern : the random table is not correctly created.");
		
		// on peut enfin faire notre random
		return this.changePatterns.get(randomTable[rng.nextInt(totalPoid)]);
	}
}
