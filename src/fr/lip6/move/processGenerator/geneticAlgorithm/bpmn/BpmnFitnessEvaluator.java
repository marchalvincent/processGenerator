package fr.lip6.move.processGenerator.geneticAlgorithm.bpmn;

import java.util.List;
import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.FlowNode;
import org.uncommons.watchmaker.framework.FitnessEvaluator;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.structuralConstraint.StructuralConstraintChecker;

/**
 * L'évaluateur d'un candidat. C'est une fonction de fitness "naturelle", c'est à dire
 * que plus la valeur de la fitness est élevée, plus le candidat correspond aux attentes.
 * @author Vincent
 *
 */
public class BpmnFitnessEvaluator implements FitnessEvaluator<BpmnProcess> {

	private int nbNodes;
	private int margin;
	private List<StructuralConstraintChecker> contraintesElements, contraintesWorkflows;
	private StructuralConstraintChecker manualOclChecker;
	
	public BpmnFitnessEvaluator(Integer nbNodes, Integer margin,
			List<StructuralConstraintChecker> contraintesElements,
			List<StructuralConstraintChecker> contraintesWorkflows,
			StructuralConstraintChecker manualOclChecker) {
		super();
		this.nbNodes = nbNodes;
		this.margin = margin;
		this.contraintesElements = contraintesElements;
		this.contraintesWorkflows = contraintesWorkflows;
		this.manualOclChecker = manualOclChecker;
	}

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
