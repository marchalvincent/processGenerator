package fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.changePattern;

import java.util.List;
import java.util.Random;
import org.eclipse.bpmn2.Activity;
import org.eclipse.bpmn2.SequenceFlow;
import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.geneticAlgorithm.AbstractChangePattern;
import fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.IBpmnChangePattern;


public class BpmnRemove extends AbstractChangePattern implements IBpmnChangePattern {

	@Override
	public BpmnProcess apply(BpmnProcess oldProcess, Random rng) {

		BpmnProcess process = null;
		try {
			process = new BpmnProcess(oldProcess);
		} catch (BpmnException e) {
			// impossible de copier...
			System.err.println(getClass().getSimpleName() + e.getMessage());
			return oldProcess;
		}
		
		Activity ancienneTask = null;
		try {
			ancienneTask = ChangePatternHelper.getInstance().getRandomActivity(process, rng);
		} catch (Exception e) {
			// on n'a pas trouvé d'activité à supprimer
			return process;
		}

		// on récupère les arc arrivant et partant de cette activity
		List<SequenceFlow> sequencesIn = ancienneTask.getIncoming();
		List<SequenceFlow> sequencesOut = ancienneTask.getOutgoing();
		if (sequencesIn.size() != 1)
			System.err.println(getClass().getSimpleName() + " : The number of incoming sequenceFlows is not correct : " 
					+ sequencesIn.size() + ". " + ancienneTask.getClass());
		if (sequencesOut.size() != 1)
			System.err.println(getClass().getSimpleName() + " : The number of outgoing sequenceFlows is not correct : " 
					+ sequencesIn.size() + ". " + ancienneTask.getClass());

		SequenceFlow arcIn = sequencesIn.get(0);
		SequenceFlow arcOut = sequencesOut.get(0);
		
		// on set la nouvelle cible de l'arc avant l'activité
		arcIn.setTargetRef(arcOut.getTargetRef());
		
		// puis on supprime l'activité et l'arc après cette activité
		process.removeFlowNode(ancienneTask);
		process.removeSequenceFlow(arcOut);

		// on simplifie les fork et decision "vides" si on vient d'en créer
		ChangePatternHelper.getInstance().cleanProcess(process);
		return process;
	}
}
