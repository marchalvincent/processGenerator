package fr.lip6.move.processGenerator.uml2.ga.cp;

import java.util.List;
import java.util.Random;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Action;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ControlNode;
import org.eclipse.uml2.uml.ForkNode;
import org.eclipse.uml2.uml.JoinNode;
import org.eclipse.uml2.uml.MergeNode;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.ga.AbstractChangePattern;
import fr.lip6.move.processGenerator.uml2.UmlException;
import fr.lip6.move.processGenerator.uml2.UmlProcess;

/**
 * Permet l'insertion d'une {@link Action} en parallèle à une autre. Ce change pattern va entrainer la création des deux
 * {@link ControlNode}s suivants : {@link ForkNode} et {@link MergeNode} respectivement avant et après une Action choisie au 
 * hasard.
 * 
 * @author Vincent
 * 
 * @see UmlParallelInsertRandom insertion d'une Action en parallèle au hasard dans le process.
 * @see UmlParallelInsertForkJoin insertion d'une Action en parallèle à un couple Fork/Join déjà existant.
 */
public class UmlParallelInsertAction extends AbstractChangePattern<UmlProcess> {
	
	// évite trop d'instanciation de cette classe
	public static UmlParallelInsertAction instance = new UmlParallelInsertAction();

	@Override
	public UmlProcess apply(UmlProcess oldProcess, Random rng, List<StructuralConstraintChecker> workflowsConstraints) {
		
		// on fait la copie du process
		UmlProcess process = new UmlProcess(oldProcess);
		
		// on récupère une Action au hasard
		Action action = null;
		try {
			action = UmlChangePatternHelper.instance.getRandomAction(process, rng);
		} catch (UmlException e) {
			// une exception est levée si aucune action n'existe. Mais ce n'est pas une erreur, donc pas de message à print.
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
		
		// on créé les nouveaux noeuds
		ForkNode fork = process.buildForkNode();
		Action a = process.buildAction();
		JoinNode join = process.buildJoinNode();
		
		process.linkControlNodes(fork, join);
		
		// puis les arcs
		process.buildControlFlow(fork, action);
		process.buildControlFlow(fork, a);
		process.buildControlFlow(action, join);
		process.buildControlFlow(a, join);
		
		// et on fini par changer les arcs avant et après l'action
		before.setTarget(fork);
		after.setSource(join);
		
		return process;
	}
}
