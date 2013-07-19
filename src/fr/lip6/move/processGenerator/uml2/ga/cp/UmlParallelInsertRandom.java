package fr.lip6.move.processGenerator.uml2.ga.cp;

import java.util.List;
import java.util.Random;
import org.eclipse.uml2.uml.Action;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.ga.AbstractChangePattern;
import fr.lip6.move.processGenerator.uml2.UmlProcess;

/**
 * Permet l'insertion en parallèle d'une {@link Action} dans le process au hasard. Cette méthode peut très bien ajouter l'Action
 * en parallèle par rapport à une autre (cela entrainera la création de ForkNode et JoinNode) mais aussi ajouter l'Action à un 
 * couple ForkNode/JoinNode déjà existant.
 * 
 * @author Vincent
 * 
 * @see UmlParallelInsertAction insertion d'une Action en parallèle à une autre parmis celles du process.
 * @see UmlParallelInsertForkJoin insertion d'une Action en parallèle à un couple Fork/Join déjà existant.
 */
public class UmlParallelInsertRandom extends AbstractChangePattern<UmlProcess> {

	@Override
	public UmlProcess apply(UmlProcess oldProcess, Random rng, List<StructuralConstraintChecker> structuralConstraints) {
		
		// on récupère le nombre d'Action et le nombre de ForkNode
		int nbAction = UmlChangePatternHelper.instance.countAction(oldProcess);
		int nbFork = UmlChangePatternHelper.instance.countForkLinked(oldProcess);
		
		// si on a aucune chance de procéder à la mutation
		if ((nbAction + nbFork) == 0)
			return new UmlProcess(oldProcess);
		
		// on fait un random équitable pour savoir si on applique le parallèle sur une Action ou sur un Fork/Join deja existant
		int[] tableau = new int[nbAction + nbFork];
		for (int i = 0; i < nbAction; i++) {
			tableau[i] = 0;
		}
		for (int i = nbAction; i < nbAction + nbFork; i++) {
			tableau[i] = 1;
		}
		
		// on procède au tirage au sort. 0 pour une insertion sur un Action, 1 pour une insertion sur un Fork/Join existant.
		int tirage = tableau[rng.nextInt(tableau.length)];
		if (tirage == 0)
			return UmlParallelInsertAction.instance.apply(oldProcess, rng, structuralConstraints);
		else
			return UmlParallelInsertForkJoin.instance.apply(oldProcess, rng, structuralConstraints);
	}
}
