package fr.lip6.move.processGenerator.bpmn2.ga;

import java.util.Random;
import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;
import fr.lip6.move.processGenerator.bpmn2.BpmnBuilder;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;

/**
 * Représente la factory qui va créer la population initiale de l'algorithme génétique.
 * @author Vincent
 *
 */
public class BpmnCandidateFactory extends AbstractCandidateFactory<BpmnProcess> {

	private BpmnProcess process;
	
	/**
	 * Construit la factory sans process prédéfinit. Le candidat initial sera 
	 * un simple process StartEvent -> EndEvent.
	 */
	public BpmnCandidateFactory() {
		this(null);
	}
	
	/**
	 * Construit la factory avec un process prédéfinit.
	 * @param process
	 */
	public BpmnCandidateFactory(BpmnProcess process) {
		super();
		this.process = process;
	}
	
	@Override
	public BpmnProcess generateRandomCandidate(Random rng) {
		if (process != null)
			return process;
		return BpmnBuilder.instance.initialFinal();
	}
}
