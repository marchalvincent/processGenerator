package fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.changePattern;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.eclipse.bpmn2.Activity;
import org.eclipse.bpmn2.ExclusiveGateway;
import org.eclipse.bpmn2.FlowNode;
import org.eclipse.bpmn2.GatewayDirection;
import org.eclipse.bpmn2.SequenceFlow;

import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.AbstractBpmnChangePattern;
import fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.IBpmnChangePattern;
import fr.lip6.move.processGenerator.structuralConstraint.StructuralConstraintChecker;

/**
 * Ce change pattern se charge de supprimer une activité puis de simplifier le process lorsqu'il
 * contient des données incohérentes.
 * @author Vincent
 *
 */
public class BpmnRemove extends AbstractBpmnChangePattern implements IBpmnChangePattern {

	@Override
	public BpmnProcess apply(BpmnProcess oldProcess, Random rng, List<StructuralConstraintChecker> structuralConstraints) {

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
			ancienneTask = ChangePatternHelper.instance.getRandomActivity(process, rng);
		} catch (Exception e) {
			// on n'a pas trouvé d'activité à supprimer
			return process;
		}
		
		// on récupère les arc arrivant et partant de cette activity
		List<SequenceFlow> sequencesIn = ancienneTask.getIncoming();
		List<SequenceFlow> sequencesOut = ancienneTask.getOutgoing();
		
		if (sequencesIn == null || sequencesOut == null)
			return process;
		
		if (sequencesIn.size() != 1) {
			System.err.println(getClass().getSimpleName() + " : The number of incoming sequenceFlows is not correct : " 
					+ sequencesIn.size() + ". " + ancienneTask.getClass());
			try {
				process.save(System.getProperty("user.home") + "/workspace/processGenerator/gen/bug.bpmn");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (sequencesOut.size() > 1)
			System.err.println(getClass().getSimpleName() + " : The number of outgoing sequenceFlows is not correct : " 
					+ sequencesOut.size() + ". " + ancienneTask.getClass());

		SequenceFlow arcIn = sequencesIn.get(0);
		
		// si la tache est en fin de process
		if (sequencesOut.size() == 0) {
			// il faut quand même éviter qu'une boucle ne se retrouve en fin de process
			FlowNode nodeBefore = arcIn.getSourceRef();
			if (
					nodeBefore instanceof ExclusiveGateway && 
					((ExclusiveGateway) nodeBefore).getGatewayDirection().equals(GatewayDirection.DIVERGING)
				)
				return process;
			
			process.removeSequenceFlow(arcIn);
			process.removeFlowNode(ancienneTask);
		} else {
			SequenceFlow arcOut = sequencesOut.get(0);

			// on set la nouvelle cible de l'arc avant l'activité
			arcIn.setTargetRef(arcOut.getTargetRef());

			// puis on supprime l'activité et l'arc après cette activité
			process.removeFlowNode(ancienneTask);
			process.removeSequenceFlow(arcOut);
		}
		
		// on simplifie les fork et decision "vides" si on vient d'en créer
		ChangePatternHelper.instance.cleanProcess(process);
		return process;
	}
}
