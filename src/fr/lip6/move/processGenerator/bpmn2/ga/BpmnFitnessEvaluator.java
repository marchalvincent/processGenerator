package fr.lip6.move.processGenerator.bpmn2.ga;

import java.util.List;
import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.FlowNode;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.ga.AbstractFitnessEvaluator;
import fr.lip6.move.processGenerator.ga.FitnessWeightHelper;

/**
 * Cette classe se charge d'évaluer la fitness des candidats BPMN2.
 * 
 * @author Vincent
 * 
 */
public class BpmnFitnessEvaluator extends AbstractFitnessEvaluator<BpmnProcess> {
	
	public BpmnFitnessEvaluator(Integer nbNodes, Integer margin, List<StructuralConstraintChecker> contraintesElements,
			List<StructuralConstraintChecker> contraintesWorkflows, StructuralConstraintChecker manualOclChecker,
			FitnessWeightHelper weightHelper) {
		super(nbNodes, margin, contraintesElements, contraintesWorkflows, manualOclChecker, weightHelper);
	}

	@Override
	public double getSizeCandidate (BpmnProcess candidate) {
		double size = 0;
		for (FlowElement element : candidate.getProcess().getFlowElements()) {
			if (element instanceof FlowNode) size ++;
		}
		return size;
	}
}
