package fr.lip6.move.processGenerator.uml2.ga.cp;

import java.util.List;
import java.util.Random;
import org.eclipse.bpmn2.Task;
import org.eclipse.uml2.uml.ControlNode;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.ga.AbstractChangePattern;
import fr.lip6.move.processGenerator.uml2.UmlProcess;

/**
 * Insère une {@link Task} au hasard dans le diagramme avec deux {@link ControlNode} de décision. Ce change pattern peut
 * créer les noeuds de décision ou en choisir deux au hasard dans le process pour leur ajouter une branche.
 * 
 * @author Vincent
 * 
 */
public class UmlConditionalInsert extends AbstractChangePattern<UmlProcess> {
	
	@Override
	public UmlProcess apply(UmlProcess oldProcess, Random rng, List<StructuralConstraintChecker> workflowsConstraints) {
		// TODO Auto-generated method stub
		return null;
	}
}
