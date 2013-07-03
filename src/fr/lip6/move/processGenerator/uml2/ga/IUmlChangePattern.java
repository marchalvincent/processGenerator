package fr.lip6.move.processGenerator.uml2.ga;

import java.util.List;
import java.util.Random;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.ga.IChangePattern;
import fr.lip6.move.processGenerator.uml2.UmlProcess;

/**
 * Cette interface définit le comportement des change pattern implémentés pour fonctionner sur les fichiers UML2.0.
 * 
 * @author Vincent
 * 
 */
public interface IUmlChangePattern extends IChangePattern {
	
	/**
	 * Cette méthode permet au change pattern de modifier un candidat.
	 * 
	 * @param oldProcess
	 *            le candidat avant la mutation.
	 * @param rng
	 *            un {@link Random} pour les opérations aléatoires.
	 * @param workflowsConstraints
	 *            la liste des {@link StructuralConstraintChecker} de workflow sélectionnés par l'utilisateur.
	 * @return {@link UmlProcess} le candidat après la mutation.
	 */
	UmlProcess apply (UmlProcess oldProcess, Random rng, List<StructuralConstraintChecker> workflowsConstraints);
}
