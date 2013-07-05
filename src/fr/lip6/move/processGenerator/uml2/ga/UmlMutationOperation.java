package fr.lip6.move.processGenerator.uml2.ga;

import java.util.List;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.ga.AbstractMutationOperation;
import fr.lip6.move.processGenerator.ga.GeneticException;
import fr.lip6.move.processGenerator.ga.IChangePattern;
import fr.lip6.move.processGenerator.uml2.UmlProcess;

/**
 * Cette classe se charge de la mutation des candidats UML2.0 entre chaque génération.
 * 
 * @author Vincent
 * 
 */
public class UmlMutationOperation extends AbstractMutationOperation<UmlProcess> {
	
	public UmlMutationOperation(List<IChangePattern<UmlProcess>> changePatterns, 
			List<StructuralConstraintChecker> contraintesWorkflows) throws GeneticException {
		super(changePatterns, contraintesWorkflows);
	}
}
