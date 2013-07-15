package fr.lip6.move.processGenerator.bpmn2.ga.cp;

import java.util.List;
import java.util.Random;
import org.eclipse.bpmn2.ParallelGateway;
import org.eclipse.bpmn2.Task;
import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.ga.AbstractChangePattern;
import fr.lip6.move.processGenerator.ga.GeneticException;

/**
 * Ce change pattern représente l'ajout en parallèle d'une Task. L'ajout s'applique sur deux gateways déjà existante
 * dans le process.
 * 
 * @see BpmnParallelInsertActivity insertion d'une Task en parallèle avec une autre.
 * @see BpmnParallelInsertRandom insertion d'une Task au hasard dans le process.
 * 
 * @author Vincent
 * 
 */
public class BpmnParallelInsertGateway extends AbstractChangePattern<BpmnProcess> {
	
	// pour éviter trop d'instanciation d'un même objet
	public static final BpmnParallelInsertGateway instance = new BpmnParallelInsertGateway();
	
	@Override
	public BpmnProcess apply(BpmnProcess oldProcess, Random rng, List<StructuralConstraintChecker> workflowsConstraints) {
		
		BpmnProcess process = null;
		try {
			process = new BpmnProcess(oldProcess);
		} catch (BpmnException e) {
			// impossible de copier...
			System.err.println(getClass().getSimpleName() + e.getMessage());
			return oldProcess;
		}
		
		// on récupère une ParallelGateway diverging au hasard
		ParallelGateway parallelDiverging = null;
		try {
			parallelDiverging = BpmnChangePatternHelper.instance.getRandomParallelGatewayDiverging(process, rng);
		} catch (GeneticException e) {
			// si on n'a pas d'activity
			return process;
		}
		
		// on récupère la parallelConverging
		ParallelGateway parallelConverging = (ParallelGateway) GatewayManager.instance
				.findTwinGateway(process, parallelDiverging);
		if (parallelConverging == null)
			return process;
		
		// on créé la nouvelle tache
		Task newTask = process.buildTask();
		
		// et enfin les nouveaux arcs
		process.buildSequenceFlow(parallelDiverging, newTask);
		process.buildSequenceFlow(newTask, parallelConverging);
		
		return process;
	}
	
}
