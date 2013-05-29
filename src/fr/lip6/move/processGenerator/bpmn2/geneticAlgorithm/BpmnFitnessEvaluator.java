package fr.lip6.move.processGenerator.bpmn2.geneticAlgorithm;

import java.util.List;
import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.FlowNode;
import org.uncommons.watchmaker.framework.FitnessEvaluator;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;

/**
 * L'évaluateur d'un candidat. C'est une fonction de fitness "naturelle", c'est à dire
 * que plus la valeur de la fitness est élevée, plus le candidat correspond aux attentes.
 * @author Vincent
 *
 */
public class BpmnFitnessEvaluator implements FitnessEvaluator<BpmnProcess> {

	@Override
	public double getFitness(BpmnProcess candidate, List<? extends BpmnProcess> population) {

		int i = 0;
		for (FlowElement elem : candidate.getProcess().getFlowElements()) {
			if (elem instanceof FlowNode)
				i++;
		}
		// TODO Auto-generated method stub
		return i;
	}

	@Override
	public boolean isNatural() {
		return true;
	}
}
