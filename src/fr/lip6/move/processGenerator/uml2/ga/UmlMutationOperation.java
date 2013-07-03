package fr.lip6.move.processGenerator.uml2.ga;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.ga.GeneticException;
import fr.lip6.move.processGenerator.ga.IChangePattern;
import fr.lip6.move.processGenerator.uml2.UmlProcess;

/**
 * Cette classe se charge de la mutation des candidats entre chaque génération. Elle appliquera un change pattern au
 * hasard selon leurs probabilités.
 * 
 * @author Vincent
 * 
 */
public class UmlMutationOperation implements EvolutionaryOperator<UmlProcess> {
	
	private List<IUmlChangePattern> changePatterns;
	private List<StructuralConstraintChecker> workflowsConstraints;
	private Random rng;
	
	public UmlMutationOperation(List<IChangePattern> changePatterns, List<StructuralConstraintChecker> contraintesWorkflows)
			throws GeneticException {
		super();
		this.changePatterns = new ArrayList<>();
		for (IChangePattern iChangePattern : changePatterns) {
			if (iChangePattern instanceof IUmlChangePattern)
				this.changePatterns.add((IUmlChangePattern) iChangePattern);
			else
				throw new GeneticException("BpmnMutationOperation constructor : one change pattern is not implementing "
						+ IUmlChangePattern.class.getSimpleName() + ".");
		}
		this.rng = new MersenneTwisterRNG();
		this.workflowsConstraints = contraintesWorkflows;
	}
	
	@Override
	public List<UmlProcess> apply (List<UmlProcess> selectedCandidates, Random rng) {
		
		List<UmlProcess> newGeneration = new ArrayList<>();
		// pour chaque candidat...
		for (UmlProcess umlProcess : selectedCandidates) {
			// on prend un changePattern au hasard
			IUmlChangePattern changePattern = this.getRandomChangePattern();
			// et on applique la mutation
			newGeneration.add(changePattern.apply(umlProcess, rng, workflowsConstraints));
		}
		return newGeneration;
	}
	
	/**
	 * Sélectionne un {@link IUmlChangePattern} au hasard selon leurs poids. Plus le poids d'un change pattern est
	 * élevé, plus il a de chance d'être sélectionné. En revanche, la sélection se fait quand même à l'aide d'un objet
	 * Random.
	 * 
	 * @return {@link IUmlChangePattern}.
	 */
	private IUmlChangePattern getRandomChangePattern () {
		
		// on initialise un tableau d'entier pour donner un poids à chaque probabilité
		int totalPoid = 0;
		for (IUmlChangePattern changeP : this.changePatterns) {
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
