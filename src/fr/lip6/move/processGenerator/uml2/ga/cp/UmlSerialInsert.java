package fr.lip6.move.processGenerator.uml2.ga.cp;

import java.util.List;
import java.util.Random;
import org.eclipse.uml2.uml.Activity;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.ga.AbstractChangePattern;
import fr.lip6.move.processGenerator.uml2.UmlProcess;

/**
 * Ce change pattern insert une {@link Activity} au hasard dans le process.
 * 
 * @author Vincent
 * 
 */
public class UmlSerialInsert extends AbstractChangePattern<UmlProcess> {

	@Override
	public UmlProcess apply(UmlProcess oldProcess, Random rng, List<StructuralConstraintChecker> workflowsConstraints) {
		// TODO Auto-generated method stub
		return null;
	}
}
