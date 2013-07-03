package fr.lip6.move.processGenerator.uml2.ga;

import java.util.Random;
import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;
import fr.lip6.move.processGenerator.uml2.UmlBuilder;
import fr.lip6.move.processGenerator.uml2.UmlProcess;

/**
 * Représente la factory qui va créer la population initiale de l'algorithme génétique.
 * 
 * @author Vincent
 * 
 */
public class UmlCandidatFactory extends AbstractCandidateFactory<UmlProcess> {
	
	private UmlProcess process;
	
	/**
	 * Construit la factory sans process définit. Le process initial sera simple : initia
	 */
	public UmlCandidatFactory() {
		this(null);
	}
	
	/**
	 * Construit la factory avec un process de départ précis.
	 * 
	 * @param process
	 */
	public UmlCandidatFactory(UmlProcess process) {
		super();
		this.process = process;
	}
	
	@Override
	public UmlProcess generateRandomCandidate (Random rng) {
		if (process != null)
			return process;
		return UmlBuilder.instance.initialFinal();
	}
}
