package fr.lip6.move.processGenerator.uml2.ga.cp;

import java.util.List;
import java.util.Random;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Action;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.DecisionNode;
import org.eclipse.uml2.uml.MergeNode;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.ga.AbstractChangePattern;
import fr.lip6.move.processGenerator.uml2.UmlException;
import fr.lip6.move.processGenerator.uml2.UmlProcess;

/**
 * Permet l'insertion d'une boucle dans le process.
 * 
 * @author Vincent
 *
 */
public class UmlLoopInsert extends AbstractChangePattern<UmlProcess> {
	
	@Override
	public UmlProcess apply(UmlProcess oldProcess, Random rng, List<StructuralConstraintChecker> workflowsConstraints) {
		
		// on copie le process
		UmlProcess process = new UmlProcess(oldProcess);
		
		// on récupère une Action au hasard dans le process
		Action action;
		try {
			action = UmlChangePatternHelper.instance.getRandomAction(process, rng);
		} catch (UmlException e) {
			// s'il n'y a pas d'Action, ce n'est pas une erreur
			return process;
		}
		
		// on récupère les arcs avant et après l'action
		EList<ActivityEdge> incomings = action.getIncomings();
		if (incomings.size() == 0) {
			System.err.println("The following Action has no incoming edge : " + action.getName());
			return process;
		}
		if (incomings.size() > 1)
			System.err.println("The Action has more than only one incoming edge.");
		ActivityEdge before = incomings.get(0);
		
		EList<ActivityEdge> outgoings = action.getOutgoings();
		if (outgoings.size() == 0) {
			System.err.println("The following Action has no outgoings edge : " + action.getName());
			return process;
		}
		if (outgoings.size() > 1)
			System.err.println("The Action has more than only one outgoings edge.");
		ActivityEdge after = outgoings.get(0);
		
		// on créé les Decision/Merge
		DecisionNode decision = process.buildDecisionNode();
		MergeNode merge = process.buildMergeNode();
		process.linkControlNodes(decision, merge);
		
		// puis on créé les arcs
		process.buildControlFlow(decision, merge);
		process.buildControlFlow(merge, action);
		process.buildControlFlow(action, decision);
		
		before.setTarget(merge);
		after.setSource(decision);
		
		return process;
	}
}
