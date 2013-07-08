package fr.lip6.move.processGenerator.uml2.ga;

import fr.lip6.move.processGenerator.ga.CandidatFactory;
import fr.lip6.move.processGenerator.uml2.UmlBuilder;
import fr.lip6.move.processGenerator.uml2.UmlProcess;

/**
 * La factory des candidats initiaux pour le type de fichier UML2.0.
 * 
 * @author Vincent
 * 
 */
public class UmlCandidatFactory extends CandidatFactory<UmlProcess> {
	
	/**
	 * Le process initial sera ainsi : InitialNode -> ActivityFinalNode
	 */
	public UmlCandidatFactory() {
		this(null);
	}
	
	/**
	 * Le process initial sera celui passé en paramètre.
	 * 
	 * @param process
	 */
	public UmlCandidatFactory(UmlProcess process) {
		super(process);
	}
	
	@Override
	protected UmlProcess defaultProcess() {
		return UmlBuilder.instance.initialFinal();
	}
}
