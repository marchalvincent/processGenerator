package fr.lip6.move.processGenerator.geneticAlgorithm.bpmn;

import java.util.List;
import java.util.Random;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;

/**
 * Cette opération permet de croiser deux candidats entre eux pour donner lieu
 * à une nouvelle génération de candidat.
 * @author Vincent
 *
 */
public class BpmnCrossoverOperation implements EvolutionaryOperator<BpmnProcess> {

	@Override
	public List<BpmnProcess> apply(List<BpmnProcess> selectedCandidates, Random rng) {

		// TODO Auto-generated method stub
		return selectedCandidates;
	}
}
