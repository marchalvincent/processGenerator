package fr.lip6.move.processGenerator.uml2.ga;

import java.util.List;
import java.util.Random;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import fr.lip6.move.processGenerator.uml2.UmlProcess;

/**
 * Cette opération permet de croiser deux candidats entre eux pour donner lieu à une nouvelle génération de candidat.
 * 
 * @author Vincent
 * 
 */
public class UmlCrossoverOperation implements EvolutionaryOperator<UmlProcess> {
	
	@Override
	public List<UmlProcess> apply (List<UmlProcess> selectedCandidates, Random rng) {
		// TODO Auto-generated method stub
		return selectedCandidates;
	}
}
