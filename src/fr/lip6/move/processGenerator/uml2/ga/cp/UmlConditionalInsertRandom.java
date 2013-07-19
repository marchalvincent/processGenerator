package fr.lip6.move.processGenerator.uml2.ga.cp;

import java.util.List;
import java.util.Random;
import org.eclipse.uml2.uml.Action;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.ga.AbstractChangePattern;
import fr.lip6.move.processGenerator.uml2.UmlProcess;

/**
 * Insère une {@link Action} en conditionnelle au hasard dans le diagramme. Ce change pattern peut
 * créer les noeuds de décision/merge ou en choisir deux au hasard dans le process pour leur ajouter une branche.
 * 
 * @author Vincent
 * 
 * @see UmlConditionalInsertDecisionMerge insertion conditionelle sur un couple Decision/Merge.
 * @see UmlConditionalInsertEdge insertion conditionnelle sur un ActivityEdge.
 */
public class UmlConditionalInsertRandom extends AbstractChangePattern<UmlProcess> {
	
	@Override
	public UmlProcess apply(UmlProcess oldProcess, Random rng, List<StructuralConstraintChecker> structuralConstraints) {
		
		// on récupère le nombre d'arc et le nombre de conditional node 
		int nbEdges = UmlChangePatternHelper.instance.countEdges(oldProcess);
		int nbConditional = UmlChangePatternHelper.instance.countConditionalLinked(oldProcess);
		
		// si on a aucune chance de procéder à la mutation
		if ((nbEdges + nbConditional) == 0)
			return new UmlProcess(oldProcess);
		
		// on fait un random équitable pour savoir si on applique la condition sur un arc ou sur un Decision/Merge deja existant
		int[] tableau = new int[nbEdges + nbConditional];
		for (int i = 0; i < nbEdges; i++) {
			tableau[i] = 0;
		}
		for (int i = nbEdges; i < nbEdges + nbConditional; i++) {
			tableau[i] = 1;
		}
		
		// on procède au tirage au sort. 0 pour une insertion sur un arc, 1 pour une insertion sur un Decision/Merge existant.
		int tirage = tableau[rng.nextInt(tableau.length)];
		if (tirage == 0)
			return UmlConditionalInsertEdge.instance.apply(oldProcess, rng, structuralConstraints);
		else
			return UmlConditionalInsertDecisionMerge.instance.apply(oldProcess, rng, structuralConstraints);
	}
}
