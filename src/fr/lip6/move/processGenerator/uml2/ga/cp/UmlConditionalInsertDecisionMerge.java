package fr.lip6.move.processGenerator.uml2.ga.cp;

import java.util.List;
import java.util.Random;
import org.eclipse.uml2.uml.Action;
import org.eclipse.uml2.uml.ControlNode;
import org.eclipse.uml2.uml.DecisionNode;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.ga.AbstractChangePattern;
import fr.lip6.move.processGenerator.uml2.UmlException;
import fr.lip6.move.processGenerator.uml2.UmlProcess;

/**
 * Insère une {@link Action} en conditionnelle en choisissant un couple Decision/Merge au hasard dans le process pour leur ajouter
 * une branche.
 * 
 * @author Vincent
 * 
 * @see UmlConditionalInsertRandom insertion conditionelle au hasard dans le process.
 * @see UmlConditionalInsertEdge insertion conditionnelle sur un ActivityEdge.
 */
public class UmlConditionalInsertDecisionMerge extends AbstractChangePattern<UmlProcess> {

	// cela évite trop d'instanciation de la part du change pattern UmlConditionalInsertRandom
	public static UmlConditionalInsertDecisionMerge instance = new UmlConditionalInsertDecisionMerge();
	
	@Override
	public UmlProcess apply(UmlProcess oldProcess, Random rng, List<StructuralConstraintChecker> workflowsConstraints) {

		// on fait la copie du process
		UmlProcess process = new UmlProcess(oldProcess);
		
		// on récupère un DecisionNode au hasard ainsi que son MergeNode associé
		DecisionNode decision;
		try {
			decision = UmlChangePatternHelper.instance.getRandomDecisionNodeLinked(process, rng);
		} catch (UmlException e) {
			// une exception ici signifie qu'il n'y a pas de DecisionNode, ce n'est pas une erreur mais il faut gérer le cas
			return process;
		}
		ControlNode merge = ControlNodeManager.instance.findControlNodeTwin(process, decision);
		
		// on construit la nouvelle Action
		Action a = process.buildAction();
		
		// puis on fini par les edges
		process.buildControlFlow(decision, a);
		process.buildControlFlow(a, merge);
		
		return process;
	}
}
