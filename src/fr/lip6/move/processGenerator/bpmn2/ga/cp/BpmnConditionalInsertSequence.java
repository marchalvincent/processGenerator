package fr.lip6.move.processGenerator.bpmn2.ga.cp;

import java.util.List;
import java.util.Random;
import org.eclipse.bpmn2.Gateway;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.Task;
import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.ga.AbstractChangePattern;
import fr.lip6.move.processGenerator.ga.GeneticException;

/**
 * Ce change pattern représente l'insertion conditionnelle d'une activité. Elle ne s'applique que sur des SequenceFlow,
 * entrainant ainsi la création de Gateways.
 * 
 * @see BpmnConditionalInsertGateway insertion conditionnelle sur des gateways déjà existantes.
 * @see BpmnConditionalInsertRandom insertion conditionnelle au hasard.
 * 
 * @author Vincent
 *
 */
public class BpmnConditionalInsertSequence extends AbstractChangePattern<BpmnProcess> {

	// évite trop d'instanciations d'un même objet
	public static final BpmnConditionalInsertSequence instance = new BpmnConditionalInsertSequence();
	
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
		
		// on récupère un arc au hasard
		SequenceFlow ancienArc = null;
		try {
			ancienArc = BpmnChangePatternHelper.instance.getRandomSequenceFlow(process, rng);
		} catch (GeneticException e) {
			// ici on n'a trouvé aucun arc (ce n'est pas normal, il doit toujours en avoir)
			System.err.println(getClass().getSimpleName() + e.getMessage());
			return process;
		}
		
		// les nouveaux noeuds on tire au hasard si on met exclusive ou inclusive
		int rand = rng.nextInt(3);
		Gateway choice, merge;
		if (rand == 0) {
			// le cas WP4 & WP5 - exclusiveChoice (XOR) - simpleMerge (XOR)
			choice = process.buildExclusiveGatewayDiverging();
			merge = process.buildExclusiveGatewayConverging();
		} else if (rand == 1) {
			// le cas WP6 & WP8 - multiChoice (OR) - multiMerge (XOR)
			choice = process.buildInclusiveGatewayDiverging();
			merge = process.buildExclusiveGatewayConverging();
		} else {
			// le cas WP7 - Structured Synchronizing Merge (multiChoice (OR) - synchronizingMerge (OR))
			choice = process.buildInclusiveGatewayDiverging();
			merge = process.buildInclusiveGatewayConverging();
		}
		Task task = process.buildTask();
		
		process.linkGateways(choice, merge);
		
		// les nouveaux arcs
		process.buildSequenceFlow(choice, task);
		process.buildSequenceFlow(task, merge);
		process.buildSequenceFlow(choice, merge);
		process.buildSequenceFlow(merge, ancienArc.getTargetRef());
		
		// l'ancien arc a maintenant une nouvelle destination
		ancienArc.setTargetRef(choice);
		
		return process;
	}
}
