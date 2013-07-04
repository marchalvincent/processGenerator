package fr.lip6.move.processGenerator.uml2.ga;

import java.util.List;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.ga.AbstractFitnessEvaluator;
import fr.lip6.move.processGenerator.ga.FitnessWeightHelper;
import fr.lip6.move.processGenerator.uml2.UmlProcess;

/**
 * Cette classe se charge dévaluer la fitness des candidats UML2.0.
 * 
 * @author Vincent
 *
 */
public class UmlFitnessEvaluator extends AbstractFitnessEvaluator<UmlProcess> {
	
	public UmlFitnessEvaluator(Integer nbNodes, Integer margin, List<StructuralConstraintChecker> contraintesElements,
			List<StructuralConstraintChecker> contraintesWorkflows, StructuralConstraintChecker manualOclChecker,
			FitnessWeightHelper weightHelper) {
		super(nbNodes, margin, contraintesElements, contraintesWorkflows, manualOclChecker, weightHelper);
	}

	@Override
	public double getSizeCandidate (UmlProcess candidate) {
		try {
			return candidate.getActivity().getNodes().size();
		} catch (NullPointerException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
