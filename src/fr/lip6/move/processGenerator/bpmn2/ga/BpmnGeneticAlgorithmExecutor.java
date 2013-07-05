package fr.lip6.move.processGenerator.bpmn2.ga;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.uncommons.watchmaker.framework.FitnessEvaluator;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.ga.CandidatFactory;
import fr.lip6.move.processGenerator.ga.FitnessWeightHelper;
import fr.lip6.move.processGenerator.ga.GeneticAlgorithmExecutor;
import fr.lip6.move.processGenerator.ga.GeneticException;
import fr.lip6.move.processGenerator.ga.IChangePattern;

/**
 * Cette classe se charge de l'éxecution de l'algorithme génétique pour les fichiers candidats de types BPMN2.
 * 
 * @author Vincent
 *
 */
public class BpmnGeneticAlgorithmExecutor extends GeneticAlgorithmExecutor<BpmnProcess> {

	@Override
	protected void saveWinner(BpmnProcess winner, String location) throws IOException {
		int i = 0;
		while (new File(location + "gen" + i + ".bpmn").exists()) {
			i++;
		}
		winner.save(location + "gen" + i + ".bpmn");
	}

	@Override
	protected FitnessEvaluator<BpmnProcess> getFitnessEvaluator(Integer nbNodes, Integer margin,
			List<StructuralConstraintChecker> contraintesElements, List<StructuralConstraintChecker> contraintesWorkflows,
			StructuralConstraintChecker manualOclChecker, FitnessWeightHelper weightHelper) {
		return new BpmnFitnessEvaluator(nbNodes, margin, contraintesElements, contraintesWorkflows, manualOclChecker, weightHelper);
	}

	@Override
	protected EvolutionaryOperator<BpmnProcess> getMutationOperation(List<IChangePattern<BpmnProcess>> changePatterns,
			List<StructuralConstraintChecker> contraintesWorkflows) throws GeneticException {
		return new BpmnMutationOperation(changePatterns, contraintesWorkflows);
	}

	@Override
	protected EvolutionaryOperator<BpmnProcess> getCrossoverOperation() {
		return new BpmnCrossoverOperation();
	}

	@Override
	protected CandidatFactory<BpmnProcess> getCandidateFactory(BpmnProcess process) {
		return new BpmnCandidateFactory(process);
	}
}
