package fr.lip6.move.processGenerator.uml2.ga;

import java.util.List;
import org.uncommons.watchmaker.framework.FitnessEvaluator;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.ga.FitnessWeightHelper;
import fr.lip6.move.processGenerator.uml2.UmlProcess;

/**
 * Cette classe se charge dévaluer les candidats UML2.0 et de dire 
 * @author Vincent
 *
 */
public class UmlFitnessEvaluator implements FitnessEvaluator<UmlProcess> {
	
	private int nbNodes;
	private int margin;
	private List<StructuralConstraintChecker> contraintesElements, contraintesWorkflows;
	private StructuralConstraintChecker manualOclChecker;
	private FitnessWeightHelper weightHelper;
	private boolean bool = false;
	
	public UmlFitnessEvaluator(Integer nbNodes, Integer margin, List<StructuralConstraintChecker> contraintesElements,
			List<StructuralConstraintChecker> contraintesWorkflows, StructuralConstraintChecker manualOclChecker,
			FitnessWeightHelper weightHelper) {
		super();
		this.nbNodes = nbNodes;
		this.margin = margin;
		this.contraintesElements = contraintesElements;
		this.contraintesWorkflows = contraintesWorkflows;
		this.manualOclChecker = manualOclChecker;
		this.weightHelper = weightHelper;
	}
	
	@Override
	public double getFitness (UmlProcess candidate, List<? extends UmlProcess> population) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public boolean isNatural () {
		return true;
	}
	
}
