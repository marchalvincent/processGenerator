package fr.lip6.move.processGenerator;

/**
 * Cette interface permet d'ordonner les énumérations qui en héritent selon une hiérarchie.
 * 
 * @author Vincent
 * 
 */
public interface IHierarchicalEnum {
	
	/**
	 * Renvoie l'énumération parente à celle dont on fait appel ou null si il n'y en a pas.
	 * 
	 * @return {@link IHierarchicalEnum} ou null.
	 */
	IHierarchicalEnum getParent();
}
