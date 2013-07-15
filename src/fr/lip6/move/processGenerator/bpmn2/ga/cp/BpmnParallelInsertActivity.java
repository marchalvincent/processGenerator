package fr.lip6.move.processGenerator.bpmn2.ga.cp;

import java.util.List;
import java.util.Random;
import org.eclipse.bpmn2.Activity;
import org.eclipse.bpmn2.ParallelGateway;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.Task;
import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.ga.AbstractChangePattern;
import fr.lip6.move.processGenerator.ga.GeneticException;

/**
 * Ce change pattern représente l'ajout d'une Task en parallèle à une autre entrainant donc la création de deux gateways
 * parallèles.
 * 
 * @see BpmnParallelInsertGateway insertion d'une Task sur des gateways déjà existantes.
 * @see BpmnParallelInsertRandom insertion d'une Task au hasard dans le process.
 * 
 * @author Vincent
 * 
 */
public class BpmnParallelInsertActivity extends AbstractChangePattern<BpmnProcess> {
	
	// pour éviter trop d'instanciation d'un même objet
	public static final BpmnParallelInsertActivity instance = new BpmnParallelInsertActivity();
	
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
		
		// on récupère une activité au hasard
		Activity activity = null;
		try {
			activity = BpmnChangePatternHelper.instance.getRandomActivity(process, rng);
		} catch (GeneticException e) {
			// si on n'a pas d'activity
			return process;
		}
		
		// on récupère les arc arrivant et partant de cette activity
		List<SequenceFlow> sequencesIn = activity.getIncoming();
		List<SequenceFlow> sequencesOut = activity.getOutgoing();
		if (sequencesIn.size() != 1)
			System.err.println(getClass().getSimpleName() + " : The number of incoming sequenceFlows is not correct : "
					+ sequencesIn.size() + ". " + activity.getClass());
		if (sequencesOut.size() > 1)
			System.err.println(getClass().getSimpleName() + " : The number of outgoing sequenceFlows is not correct : "
					+ sequencesOut.size() + ". " + activity.getClass());
		
		/*
		 * pour des raisons de simplicité dans les diagrammes, on ne faire pas l'insertion de boucle lorsque l'activité
		 * est en fin de process (sans arc sortant)
		 */
		if (sequencesOut.size() == 0)
			return process;
		
		SequenceFlow arcIn = sequencesIn.get(0);
		SequenceFlow arcOut = sequencesOut.get(0);
		
		// on créé les nouveaux noeuds
		ParallelGateway fork = process.buildParallelGatewayDiverging();
		ParallelGateway join = process.buildParallelGatewayConverging();
		Task newTask = process.buildTask();
		
		process.linkGateways(fork, join);
		
		// modification des arcs
		arcIn.setTargetRef(fork);
		process.buildSequenceFlow(fork, activity);
		process.buildSequenceFlow(fork, newTask);
		process.buildSequenceFlow(newTask, join);
		process.buildSequenceFlow(activity, join);
		arcOut.setSourceRef(join);
		
		return process;
	}
}
