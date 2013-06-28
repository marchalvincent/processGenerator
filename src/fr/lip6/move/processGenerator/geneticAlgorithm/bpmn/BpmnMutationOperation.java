package fr.lip6.move.processGenerator.geneticAlgorithm.bpmn;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.geneticAlgorithm.GeneticException;
import fr.lip6.move.processGenerator.geneticAlgorithm.IChangePattern;
import fr.lip6.move.processGenerator.structuralConstraint.StructuralConstraintChecker;

/**
 * Cette classe se charge de la mutation des candidats entre chaque génération.
 * Elle appliquera un change pattern selon leurs probabilités.
 * @author Vincent
 *
 */
public class BpmnMutationOperation implements EvolutionaryOperator<BpmnProcess> {

	private List<IBpmnChangePattern> changePatterns;
	private List<StructuralConstraintChecker> workflowsConstraints;
	private Random rng;
	
	public BpmnMutationOperation(List<IChangePattern> changePatterns, List<StructuralConstraintChecker> contraintesWorkflows) 
			throws GeneticException {
		super();
		this.changePatterns = new ArrayList<IBpmnChangePattern>();
		for (IChangePattern iChangePattern : changePatterns) {
			if (iChangePattern instanceof IBpmnChangePattern)
				this.changePatterns.add((IBpmnChangePattern) iChangePattern);
			else 
				throw new GeneticException("BpmnMutationOperation constructor : one change pattern is not implementing IBpmnChangePattern.");
		}
		this.rng = new MersenneTwisterRNG();
		this.workflowsConstraints = contraintesWorkflows;
	}
	
	@Override
	public List<BpmnProcess> apply(List<BpmnProcess> selectedCandidates, Random rng) {
		
		List<BpmnProcess> newGeneration = new ArrayList<BpmnProcess>();
		// pour chaque candidat...
		for (BpmnProcess bpmnProcess : selectedCandidates) {
			// on prend un changePattern au hasard
			IBpmnChangePattern changePattern = this.getRandomChangePattern();
			// et on applique la mutation
			newGeneration.add(changePattern.apply(bpmnProcess, rng, workflowsConstraints));
		}
		return newGeneration;
	}
	
	/**
	 * Sélectionne un {@link IBpmnChangePattern} au hasard selon leurs poids. Plus le poids d'un change pattern
	 * est élevé, plus il a de chance d'être sélectionné. En revanche, la sélection se fait quand même à l'aide 
	 * d'un objet Random.
	 * @return {@link IBpmnChangePattern}.
	 */
	private IBpmnChangePattern getRandomChangePattern() {
		
		// on initialise un tableau d'entier pour donner un poids à chaque probabilité
		int totalPoid = 0;
		for (IBpmnChangePattern changeP : this.changePatterns) {
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
