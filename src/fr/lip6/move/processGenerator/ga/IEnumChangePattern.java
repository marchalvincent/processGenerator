package fr.lip6.move.processGenerator.ga;

import fr.lip6.move.processGenerator.IHierarchicalEnum;

/**
 * Cette interface permet de définir le comportement que doit avoir chaque énumération représentant les
 * {@link IChangePattern} que peut sélectionner l'utilisateur sur l'interface graphique.
 * 
 * @author Vincent
 * 
 */
public interface IEnumChangePattern<T> extends IHierarchicalEnum {
	
	/**
	 * A chaque énumération est associé un {@link IChangePattern}. C'est pourquoi cette énumération doit être capable
	 * d'instancier le {@link IChangePattern} qui lui est associé.
	 * 
	 * @param proba
	 *            la probabilité que ce pattern soit appliqué sur un candidat à l'algo génétique.
	 * @return un {@link IChangePattern}.
	 * @throws Exception
	 *             une erreur peut survenir lors de l'instanciation dynamique du change pattern.
	 */
	public IChangePattern<T> newInstance(String proba) throws Exception;
}
