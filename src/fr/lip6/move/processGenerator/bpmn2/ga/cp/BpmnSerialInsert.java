package fr.lip6.move.processGenerator.bpmn2.ga.cp;

import java.util.List;
import java.util.Random;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.Task;
import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.ga.AbstractChangePattern;
import fr.lip6.move.processGenerator.ga.GeneticException;

/**
 * Insère une {@link Task} au hasard dans le diagramme.
 * 
 * @author Vincent
 * 
 */
public class BpmnSerialInsert extends AbstractChangePattern<BpmnProcess> {
	
	@Override
	public BpmnProcess apply(BpmnProcess oldProcess, Random rng, List<StructuralConstraintChecker> structuralConstraints) {
		
		BpmnProcess process = null;
		try {
			process = new BpmnProcess(oldProcess);
		} catch (BpmnException e) {
			// impossible de copier...
			System.err.println(getClass().getSimpleName() + ". " + e.getMessage());
			return oldProcess;
		}
		
		// on en prend un au hasard
		SequenceFlow ancienArc = null;
		try {
			ancienArc = BpmnChangePatternHelper.instance.getRandomSequenceFlow(process, rng);
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
