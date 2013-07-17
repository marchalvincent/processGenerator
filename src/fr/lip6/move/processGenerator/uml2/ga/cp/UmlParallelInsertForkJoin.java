package fr.lip6.move.processGenerator.uml2.ga.cp;

import java.util.List;
import java.util.Random;
import org.eclipse.uml2.uml.Action;
import org.eclipse.uml2.uml.ControlNode;
import org.eclipse.uml2.uml.ForkNode;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.ga.AbstractChangePattern;
import fr.lip6.move.processGenerator.uml2.UmlException;
import fr.lip6.move.processGenerator.uml2.UmlProcess;

/**
 * Permet l'insertion d'une {@link Action} en parallèle sur un couple de {@link ControlNode} fork/join déjà existant dans le 
 * process.
 * 
 * @author Vincent
 *
 * @see UmlParallelInsertAction insertion d'une Action en parallèle à une autre parmis celles du process.
 * @see UmlParallelInsertRandom insertion d'une Action en parallèle au hasard dans le process.
 */
public class UmlParallelInsertForkJoin extends AbstractChangePattern<UmlProcess> {

	// évite trop d'instanciation de cette classe
	public static UmlParallelInsertForkJoin instance = new UmlParallelInsertForkJoin();

	@Override
	public UmlProcess apply(UmlProcess oldProcess, Random rng, List<StructuralConstraintChecker> workflowsConstraints) {

		// on fait la copie du process
		UmlProcess process = new UmlProcess(oldProcess);
		
		// on récupère un ForkNode au hasard ainsi que son JoinNode associé
		ForkNode fork;
		try {
			fork = UmlChangePatternHelper.instance.getRandomForkNodeLinked(process, rng);
		} catch (UmlException e) {
			// une exception ici signifie qu'il n'y a pas de DecisionNode, ce n'est pas une erreur mais il faut gérer le cas
			return process;
		}
		ControlNode join = ControlNodeManager.instance.findControlNodeTwin(process, fork);
		
		// on construit la nouvelle Action
		Action a = process.buildAction();
		
		// puis on fini par les edges
		process.buildControlFlow(fork, a);
		process.buildControlFlow(a, join);
		
		return process;
	}
}
