package fr.lip6.move.processGenerator.bpmn2.ga.cp;

import java.util.List;
import java.util.Random;
import org.eclipse.bpmn2.Gateway;
import org.eclipse.bpmn2.Task;
import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.ga.AbstractChangePattern;
import fr.lip6.move.processGenerator.ga.GeneticException;

/**
 * Ce change pattern représente l'insertion conditionnelle d'une activité. Elle ne s'applique que sur des gateways déjà existantes
 * entrainant donc l'ajout d'un chemin supplémentaire à celles ci.
 * 
 * @see BpmnConditionalInsertSequence insertion conditionnelle sur un SequenceFlow.
 * @see BpmnConditionalInsertRandom insertion conditionnelle au hasard.
 * 
 * @author Vincent
 *
 */
public class BpmnConditionalInsertGateway extends AbstractChangePattern<BpmnProcess> {
	
	// évite trop d'instanciations d'un même objet
	public static final BpmnConditionalInsertGateway instance = new BpmnConditionalInsertGateway();

	@Override
	public BpmnProcess apply(BpmnProcess oldProcess, Random rng, List<StructuralConstraintChecker> workflowsConstraints) {
		
		// on copie le candidat
		BpmnProcess process = null;
		try {
			process = new BpmnProcess(oldProcess);
		} catch (BpmnException e) {
			// impossible de copier...
			System.err.println(getClass().getSimpleName() + e.getMessage());
			return oldProcess;
		}
		
		// on récupère une Gateway diverging au hasard
		Gateway gatewayDiverging = null;
		try {
			gatewayDiverging = BpmnChangePatternHelper.instance.getRandomConditionalGatewayDiverging(process, rng);
		} catch (GeneticException e) {
			// si on n'a pas d'activity
			return process;
		}
		
		// on récupère l'exclusive converging
		Gateway gatewayConverging = GatewayManager.instance.findTwinGateway(process, gatewayDiverging);
		if (gatewayConverging == null)
			return process;
		
		// on créé la nouvelle tache
		Task newTask = process.buildTask();
		
		// et enfin les nouveaux arcs
		process.buildSequenceFlow(gatewayDiverging, newTask);
		process.buildSequenceFlow(newTask, gatewayConverging);
		
		return process;
	}
}
