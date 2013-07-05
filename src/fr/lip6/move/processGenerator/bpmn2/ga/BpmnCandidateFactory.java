package fr.lip6.move.processGenerator.bpmn2.ga;

import fr.lip6.move.processGenerator.bpmn2.BpmnBuilder;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.ga.CandidatFactory;

/**
 * Représente la factory qui va créer la population initiale de l'algorithme génétique pour le type de fichier BPMN2.
 * 
 * @author Vincent
 * 
 */
public class BpmnCandidateFactory extends CandidatFactory<BpmnProcess> {

	/**
	 * Le process initial sera ainsi : InitialNode -> FinalNode
	 */
	public BpmnCandidateFactory() {
		this(null);
	}
	
	/**
	 * Le process initial sera celui passé en paramètre.
	 * @param process
	 */
	public BpmnCandidateFactory(BpmnProcess process) {
		super(process);
	}
	
	@Override
	protected BpmnProcess defaultProcess() {
		return BpmnBuilder.instance.initialFinal();
	}
}
