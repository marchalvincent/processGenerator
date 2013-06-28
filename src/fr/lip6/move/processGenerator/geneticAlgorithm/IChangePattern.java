package fr.lip6.move.processGenerator.geneticAlgorithm;

/**
 * Cette interface définie le comportement que doit avoir chaque change pattern.
 * @author Vincent
 *
 */
public interface IChangePattern {

	/**
	 * Setter sur la probabilité que ce change pattern soit exécuté.
	 * @param proba
	 */
	void setProba(String proba);
	
	/**
	 * Getter sur la probabilité que ce change pattern soit exécuté.
	 * @return
	 */
	int getProba();
}
