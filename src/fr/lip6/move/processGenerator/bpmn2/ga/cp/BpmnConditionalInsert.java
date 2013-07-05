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
 * Ce change pattern représente l'insertion conditionnelle d'une activité. Elle peut s'appliquer sur une activité et
 * entrainer la création de gateways de condition, ou tout simplement s'appliquer sur des gateways déjà existantes et
 * ajouter une branche seulement.
 * 
 * @author Vincent
 * 
 */
public class BpmnConditionalInsert extends AbstractChangePattern<BpmnProcess> {
	
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
		
		// on récupère le nombre de séquence et le nombre d'exclusive gateway
		int nbSequence = BpmnChangePatternHelper.instance.countSequenceFlow(process);
		int nbConditional = BpmnChangePatternHelper.instance.countConditionalGateway(process);
		if (nbConditional % 2 != 0) {
			System.err.println("Error, the number of ExclusiveGateway is odd.");
			return process;
		}
		
		// on divise par deux le nombre d'ExclusiveGateway car il y a une ouvrante et une fermante.
		nbConditional = nbConditional / 2;
		
		if ((nbSequence + nbConditional) == 0)
			return process;
		
		// on fait un random équitable pour savoir si on applique la condition sur un arc ou sur une ExclusiveGateway
		// deja existante
		int[] tableau = new int[nbSequence + nbConditional];
		for (int i = 0; i < nbSequence; i++) {
			tableau[i] = 0;
		}
		for (int i = nbSequence; i < nbSequence + nbConditional; i++) {
			tableau[i] = 1;
		}
		
		// on procède au tirage au sort. 0 pour une insertion sur un arc, 1 pour une insertion sur une ExclusiveGateway.
		int tirage = tableau[rng.nextInt(tableau.length)];
		if (tirage == 0) {
			return applyOnSequenceFlow(process, rng);
		} else {
			return applyOnConditional(process, rng);
		}
	}
	
	/**
	 * Applique la modification génétique d'une insertion conditionelle sur une MyExclusiveGateway ou MyInclusiveGateway
	 * déjà existente. Cette modification va entrainer l'ajout d'un chemin supplémentaire à la Gateway tirée au sort.
	 * 
	 * @param process
	 *            le {@link BpmnProcess} à modifier.
	 * @param rng
	 *            une source de {@link Random}.
	 * @return le {@link BpmnProcess} modifié.
	 */
	private BpmnProcess applyOnConditional(BpmnProcess process, Random rng) {
		
		// on récupère une Gateway diverging au hasard
		Gateway gatewayDiverging = null;
		try {
			gatewayDiverging = BpmnChangePatternHelper.instance.getRandomConditionalGatewayDiverging(process, rng);
		} catch (GeneticException e) {
			// si on n'a pas d'activity
			return process;
		}
		
		// on récupère l'exclusive converging
		Gateway gatewayConverging = BpmnGatewayManager.instance.findTwinGateway(process, gatewayDiverging);
		if (gatewayConverging == null)
			return process;
		
		// on créé la nouvelle tache
		Task newTask = process.buildTask();
		
		// et enfin les nouveaux arcs
		process.buildSequenceFlow(gatewayDiverging, newTask);
		process.buildSequenceFlow(newTask, gatewayConverging);
		
		return process;
	}
	
	/**
	 * Applique la modification génétique d'une insertion conditionelle autour d'un arc ({@link SequenceFlow}. Cette
	 * modification va entrainer une création de deux "conditional gateway" qui seront fixées avant et après cet arc.
	 * 
	 * @param process
	 *            le {@link BpmnProcess} à modifier.
	 * @param rng
	 *            une source de {@link Random}.
	 * @return le {@link BpmnProcess} modifié.
	 */
	private BpmnProcess applyOnSequenceFlow(BpmnProcess process, Random rng) {
		
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
