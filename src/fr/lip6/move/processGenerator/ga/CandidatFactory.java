package fr.lip6.move.processGenerator.ga;

import java.util.Random;
import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;

/**
 * Représente la factory qui va créer la population initiale de l'algorithme génétique.
 * 
 * @author Vincent
 * 
 * @param <T> le type de candidat que doit créer cette factory.
 */
public abstract class CandidatFactory<T> extends AbstractCandidateFactory<T> {
	
	public T process;
	
	/**
	 * Construit la factory sans process définit. Le process initial sera simple : initial -> final
	 */
	public CandidatFactory() {
		this(null);
	}
	
	/**
	 * Construit la factory avec un process de départ précis.
	 * @param process
	 */
	public CandidatFactory(T process) {
		super();
		this.process = process;
	}
	
	@Override
	public T generateRandomCandidate(Random rng) {
		if (process != null)
			return process;
		return defaultProcess();
	}

	protected abstract T defaultProcess();
}
