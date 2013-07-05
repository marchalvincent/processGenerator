package fr.lip6.move.processGenerator.uml2.ga;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.uncommons.watchmaker.framework.FitnessEvaluator;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.ga.CandidatFactory;
import fr.lip6.move.processGenerator.ga.FitnessWeightHelper;
import fr.lip6.move.processGenerator.ga.GeneticAlgorithmExecutor;
import fr.lip6.move.processGenerator.ga.GeneticException;
import fr.lip6.move.processGenerator.ga.IChangePattern;
import fr.lip6.move.processGenerator.uml2.UmlProcess;

/**
 * Cette classe se charge de l'éxecution de l'algorithme génétique pour les fichiers candidats de types UML2.0.
 * 
 * @author Vincent
 *
 */
public class UmlGeneticAlgorithmExecutor extends GeneticAlgorithmExecutor<UmlProcess> {

	public UmlGeneticAlgorithmExecutor() {
		super();
	}

	@Override
	protected void saveWinner(UmlProcess winner, String location) throws IOException {
		int i = 0;
		while (new File(location + "gen" + i + ".uml").exists()) {
			i++;
		}
		winner.save(location + "gen" + i + ".uml");
	}
	
	@Override
	protected FitnessEvaluator<UmlProcess> getFitnessEvaluator(Integer nbNodes, Integer margin,
			List<StructuralConstraintChecker> contraintesElements, List<StructuralConstraintChecker> contraintesWorkflows,
			StructuralConstraintChecker manualOclChecker, FitnessWeightHelper weightHelper) {
		return new UmlFitnessEvaluator(nbNodes, margin, contraintesElements, contraintesWorkflows, manualOclChecker, weightHelper);
	}

	@Override
	protected EvolutionaryOperator<UmlProcess> getMutationOperation(List<IChangePattern<UmlProcess>> changePatterns,
			List<StructuralConstraintChecker> contraintesWorkflows) throws GeneticException {
		return new UmlMutationOperation(changePatterns, contraintesWorkflows);
	}

	@Override
	protected EvolutionaryOperator<UmlProcess> getCrossoverOperation() {
		return new UmlCrossoverOperation();
	}

	@Override
	protected CandidatFactory<UmlProcess> getCandidateFactory(UmlProcess process) {
		return new UmlCandidatFactory(process);
	}
}
