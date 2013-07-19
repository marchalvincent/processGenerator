package fr.lip6.move.processGenerator.uml2.ga.cp;

import java.util.List;
import java.util.Random;
import org.eclipse.uml2.uml.Action;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityFinalNode;
import org.eclipse.uml2.uml.ForkNode;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.ga.AbstractChangePattern;
import fr.lip6.move.processGenerator.uml2.UmlException;
import fr.lip6.move.processGenerator.uml2.UmlProcess;

/**
 * Permet l'insertion d'un nouveau thread de terminaision explicite au process. Le thread se termine par un 
 * {@link ActivityFinalNode}.
 * 
 * @author Vincent
 * 
 * @see UmlThreadInsertRandom insertion d'un thread implicite ou explicite.
 * @see UmlThreadInsertImplicit insertion d'un thread implicite.
 */
public class UmlThreadInsertExplicit extends AbstractChangePattern<UmlProcess> {
	
	// pour éviter trop d'instance de cette même classe
	public static UmlThreadInsertExplicit instance = new UmlThreadInsertExplicit();

	@Override
	public UmlProcess apply(UmlProcess oldProcess, Random rng, List<StructuralConstraintChecker> workflowsConstraints) {
		
		// on copie le process
		UmlProcess process = new UmlProcess(oldProcess);
		
		// on récupère un arc au hasard
		ActivityEdge edge;
		try {
			edge = UmlChangePatternHelper.instance.getRandomActivityEdge(process, rng);
		} catch (UmlException e) {
			// si on a pas d'arc, c'est une erreur
			e.printStackTrace();
			return process;
		}
		
		// on créé les nouveaux noeuds
		ForkNode fork = process.buildForkNode();
		Action a = process.buildAction();
		ActivityFinalNode finalNode = process.buildActivityFinalNode();
		
		// et enfin les arcs
		process.buildControlFlow(fork, edge.getTarget());
		process.buildControlFlow(fork, a);
		process.buildControlFlow(a, finalNode);
		
		edge.setTarget(fork);
		
		return process;
	}
}
