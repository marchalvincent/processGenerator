package fr.lip6.move.processGenerator.uml2.ga.cp;

import java.util.List;
import java.util.Random;
import org.eclipse.uml2.uml.ActivityFinalNode;
import org.eclipse.uml2.uml.FlowFinalNode;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.ga.AbstractChangePattern;
import fr.lip6.move.processGenerator.uml2.UmlProcess;

/**
 * Permet l'insertion d'un nouveau thread de terminaision implicite ou explicite au process. Le thread se termine par un 
 * {@link FlowFinalNode} ou un {@link ActivityFinalNode}.
 * 
 * @author Vincent
 *
 * @see UmlThreadInsertImplicit insertion d'un thread implicite.
 * @see UmlThreadInsertExplicit insertion d'un thread explicite.
 */
public class UmlThreadInsertRandom extends AbstractChangePattern<UmlProcess> {
	
	@Override
	public UmlProcess apply(UmlProcess oldProcess, Random rng, List<StructuralConstraintChecker> workflowsConstraints) {
		// une fois sur deux on faire appel au CP implicite / explicite.
		if (rng.nextBoolean()) 
			return UmlThreadInsertImplicit.instance.apply(oldProcess, rng, workflowsConstraints);
		else 
			return UmlThreadInsertExplicit.instance.apply(oldProcess, rng, workflowsConstraints);
	}
}
