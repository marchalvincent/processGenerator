package fr.lip6.move.processGenerator.geneticAlgorithm.bpmn;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.eclipse.bpmn2.FlowNode;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.Task;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;


public class BpmnMutationOperation implements EvolutionaryOperator<BpmnProcess> {

	// TODO lancer les change patterns en random
	@SuppressWarnings("unused")
	private List<IBpmnChangePatterns> changePatterns;
	
	public BpmnMutationOperation(List<IBpmnChangePatterns> changePatterns) {
		super();
		this.changePatterns = changePatterns;
	}
	
	@Override
	public List<BpmnProcess> apply(List<BpmnProcess> selectedCandidates, Random rng) {
		
		List<BpmnProcess> newGeneration = new ArrayList<BpmnProcess>();
		for (BpmnProcess bpmnProcess : selectedCandidates) {
			newGeneration.add(this.mutate(bpmnProcess, rng));
		}
		return newGeneration;
	}

	
	/**
	 * Temporaire fait une insertion la plus basique qu'elle soit
	 * @param bpmnProcess
	 * @param rng
	 * @return
	 */
	private BpmnProcess mutate(BpmnProcess bpmnProcess, Random rng) {
		
		if (rng.nextInt(100) < 90) {
			return bpmnProcess;
		}
		
		BpmnProcess newProcess = null;
		try {
			newProcess = new BpmnProcess(bpmnProcess);
		} catch (BpmnException e) {
			// en cas d'echec de la copie, on renvoie le candidat tel qu'il est
			return bpmnProcess;
		}
		
		// on prend le StartEvent
		FlowNode startEvent = (FlowNode) newProcess.getProcess().getFlowElements().get(0);
		SequenceFlow arc = startEvent.getOutgoing().get(0);
		
		Task t = newProcess.buildTask();
		newProcess.buildSequenceFlow(t, arc.getTargetRef());
		
		arc.setTargetRef(t);
		
		return newProcess;
	}
}
