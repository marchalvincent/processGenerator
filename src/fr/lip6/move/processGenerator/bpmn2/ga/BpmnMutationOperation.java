package fr.lip6.move.processGenerator.bpmn2.ga;

import java.util.List;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.ga.AbstractMutationOperation;
import fr.lip6.move.processGenerator.ga.GeneticException;
import fr.lip6.move.processGenerator.ga.IChangePattern;

/**
 * Cette classe se charge de la mutation des candidats BPMN2 entre chaque génération.
 * 
 * @author Vincent
 * 
 */
public class BpmnMutationOperation extends AbstractMutationOperation<BpmnProcess> {
	
	public BpmnMutationOperation(List<IChangePattern<BpmnProcess>> changePatterns,
			List<StructuralConstraintChecker> contraintesWorkflows) throws GeneticException {
		super(changePatterns, contraintesWorkflows);
	}
}
