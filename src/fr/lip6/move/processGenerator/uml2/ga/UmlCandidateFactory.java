package fr.lip6.move.processGenerator.uml2.ga;

import fr.lip6.move.processGenerator.ga.CandidateFactory;
import fr.lip6.move.processGenerator.uml2.UmlBuilder;
import fr.lip6.move.processGenerator.uml2.UmlProcess;

/**
 * La factory des candidats initiaux pour le type de fichier UML2.0.
 * 
 * @author Vincent
 * 
 */
public class UmlCandidateFactory extends CandidateFactory<UmlProcess> {
	
	/**
	 * Le process initial sera ainsi : InitialNode -> FlowFinalNode
	 */
	public UmlCandidateFactory() {
		this(null);
	}
	
	/**
	 * Le process initial sera celui passé en paramètre.
	 * 
	 * @param process
	 */
	public UmlCandidateFactory(UmlProcess process) {
		super(process);
	}
	
	@Override
	protected UmlProcess defaultProcess() {
		return UmlBuilder.instance.initialFinal();
	}
}
