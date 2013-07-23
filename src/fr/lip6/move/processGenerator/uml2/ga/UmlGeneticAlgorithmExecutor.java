package fr.lip6.move.processGenerator.uml2.ga;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.uncommons.watchmaker.framework.FitnessEvaluator;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.dot.DotGenerator;
import fr.lip6.move.processGenerator.ga.CandidateFactory;
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
	
	@Override
	protected String saveWinner(UmlProcess winner, String location) throws IOException {
		int i = 0;
		while (new File(location + "uGen" + i + ".uml").exists())
			i++;
		winner.save(location + "uGen" + i + ".uml");
		return location + "uGen" + i + ".uml";
	}

	@Override
	protected String saveDigraph(UmlProcess winner, String location) throws IOException {
		DotGenerator generator = new DotGenerator(winner);
		generator.generateDot(location, "digraph");
		return location + "digraph";
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
	protected CandidateFactory<UmlProcess> getCandidateFactory(UmlProcess process) {
		return new UmlCandidateFactory(process);
	}
}
