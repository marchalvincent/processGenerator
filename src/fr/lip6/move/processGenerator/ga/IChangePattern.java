package fr.lip6.move.processGenerator.ga;

import java.util.List;
import java.util.Random;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;

/**
 * Cette interface définie le comportement que doit avoir chaque change pattern.
 * 
 * @author Vincent
 * 
 */
public interface IChangePattern<T> {
	
	/**
	 * Setter sur la probabilité que ce change pattern soit exécuté.
	 * 
	 * @param proba
	 */
	void setProba(String proba);
	
	/**
	 * Getter sur la probabilité que ce change pattern soit exécuté.
	 * 
	 * @return
	 */
	int getProba();
	
	/**
	 * Cette méthode permet au change pattern de modifier un candidat.
	 * 
	 * @param oldProcess
	 *            le candidat avant la mutation.
	 * @param rng
	 *            un {@link Random} pour les opérations aléatoires.
	 * @param workflowsConstraints
	 *            la liste des {@link StructuralConstraintChecker} de workflow sélectionnés par l'utilisateur.
	 * @return T, le candidat après la mutation.
	 */
	T apply(T oldProcess, Random rng, List<StructuralConstraintChecker> workflowsConstraints);
}
