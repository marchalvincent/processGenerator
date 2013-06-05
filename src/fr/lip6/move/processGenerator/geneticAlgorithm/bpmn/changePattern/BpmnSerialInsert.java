package fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.changePattern;

import java.util.Random;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.Task;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.geneticAlgorithm.AbstractChangePattern;
import fr.lip6.move.processGenerator.geneticAlgorithm.GeneticException;
import fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.IBpmnChangePattern;

/**
 * Insère une {@link Task} au hasard dans le diagramme.
 * @author Vincent
 *
 */
public class BpmnSerialInsert extends AbstractChangePattern implements IBpmnChangePattern {

	
	@Override
	public BpmnProcess apply(BpmnProcess process, Random rng) {

		// on en prend un au hasard
		SequenceFlow ancienArc = null;
		try {
			ancienArc = ChangePatternHelper.getInstance().getRandomSequenceFlow(process, rng);
		} catch (GeneticException e) {
			// si on n'a trouvé aucun arc
			return process;
		}
		
		// on construit la nouvelle tache et un nouveau sequenceFlow
		Task task = process.buildTask();
		process.buildSequenceFlow(task, ancienArc.getTargetRef());
		
		// on modifie la destination de l'ancien arc
		ancienArc.setTargetRef(task);
		
		return process;
	}
}
