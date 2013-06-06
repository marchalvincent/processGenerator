package fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.changePattern;

import java.util.Random;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.Task;
import fr.lip6.move.processGenerator.bpmn2.BpmnException;
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
	public BpmnProcess apply(BpmnProcess oldProcess, Random rng) {

		BpmnProcess process = null;
		try {
			process = new BpmnProcess(oldProcess);
		} catch (BpmnException e) {
			// impossible de copier...
			System.err.println(getClass().getSimpleName() + e.getMessage());
			return oldProcess;
		}
		
		// on en prend un au hasard
		SequenceFlow ancienArc = null;
		try {
			ancienArc = ChangePatternHelper.getInstance().getRandomSequenceFlow(process, rng);
		} catch (GeneticException e) {
			// ici on n'a trouvé aucun arc (ce n'est pas normal, il doit toujours en avoir)
			System.err.println(getClass().getSimpleName() + e.getMessage());
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
