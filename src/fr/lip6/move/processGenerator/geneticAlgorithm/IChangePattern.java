package fr.lip6.move.processGenerator.geneticAlgorithm;


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
