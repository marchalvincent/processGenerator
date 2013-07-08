package fr.lip6.move.processGenerator.bpmn2;

import org.eclipse.bpmn2.EndEvent;
import org.eclipse.bpmn2.ExclusiveGateway;
import org.eclipse.bpmn2.InclusiveGateway;
import org.eclipse.bpmn2.ParallelGateway;
import org.eclipse.bpmn2.StartEvent;
import org.eclipse.bpmn2.Task;
import fr.lip6.move.processGenerator.bpmn2.ga.cp.BpmnChangePatternHelper;
import fr.lip6.move.processGenerator.bpmn2.ga.cp.GatewayManager;

/**
 * Cette classe permet de construire rapidement des {@link BpmnProcess}.
 * 
 * @author Vincent
 * 
 */
public class BpmnBuilder {
	
	public static final BpmnBuilder instance = new BpmnBuilder();
	
	private BpmnBuilder() {}
	
	/**
	 * Construit un process simple : StartEvent -> EndEvent.
	 * 
	 * @return
	 */
	public BpmnProcess initialFinal() {
		BpmnProcess process = new BpmnProcess();
		
		// les noeuds
		StartEvent start = process.buildStartEvent();
		EndEvent end = process.buildEndEvent();
		
		// les arcs
		process.buildSequenceFlow(start, end);
		
		return process;
	}
	
	/**
	 * Construit un process simple : StartEvent -> Task -> Task -> EndEvent.
	 * 
	 * @return
	 */
	public BpmnProcess initialABFinal() {
		BpmnProcess process = new BpmnProcess();
		
		// les noeuds
		StartEvent start = process.buildStartEvent();
		Task a = process.buildTask();
		Task b = process.buildTask();
		EndEvent end = process.buildEndEvent();
		
		// les arcs
		process.buildSequenceFlow(start, a);
		process.buildSequenceFlow(a, b);
		process.buildSequenceFlow(b, end);
		
		return process;
	}
	
	/**
	 * Construit un process simple : StartEvent -> Task -> Task -> Task -> EndEvent.
	 * 
	 * @return
	 */
	public BpmnProcess initialABCFinal() {
		BpmnProcess process = new BpmnProcess();
		
		// les noeuds
		StartEvent start = process.buildStartEvent();
		Task a = process.buildTask();
		Task b = process.buildTask();
		Task c = process.buildTask();
		EndEvent end = process.buildEndEvent();
		
		// les arcs
		process.buildSequenceFlow(start, a);
		process.buildSequenceFlow(a, b);
		process.buildSequenceFlow(b, c);
		process.buildSequenceFlow(c, end);
		
		return process;
	}
	
	/**
	 * Construit un process avec : init -> a -> parallel -> (b & c) -> join -> d -> e -> f -> final
	 * 
	 * @return
	 */
	public BpmnProcess createExampleWithParallel() {
		BpmnProcess process = new BpmnProcess();
		
		// les noeuds
		StartEvent start = process.buildStartEvent();
		Task a = process.buildTask();
		ParallelGateway decision = process.buildParallelGatewayDiverging();
		Task b = process.buildTask();
		Task c = process.buildTask();
		ParallelGateway merge = process.buildParallelGatewayConverging();
		Task d = process.buildTask();
		Task e = process.buildTask();
		Task f = process.buildTask();
		EndEvent end = process.buildEndEvent();
		
		process.linkGateways(decision, merge);
		
		// les arcs
		process.buildSequenceFlow(start, a);
		process.buildSequenceFlow(a, decision);
		process.buildSequenceFlow(decision, b);
		process.buildSequenceFlow(decision, c);
		process.buildSequenceFlow(b, merge);
		process.buildSequenceFlow(c, merge);
		process.buildSequenceFlow(merge, d);
		process.buildSequenceFlow(d, e);
		process.buildSequenceFlow(e, f);
		process.buildSequenceFlow(f, end);
		
		return process;
	}
	
	/**
	 * Construit un process avec : init -> parallel -> (b & c) -> join -> final
	 * 
	 * @return
	 */
	public BpmnProcess createExampleWithParallel2() {
		
		BpmnProcess process = new BpmnProcess();
		
		// init du process
		StartEvent start = process.buildStartEvent();
		ParallelGateway parallel = process.buildParallelGatewayDiverging();
		Task b = process.buildTask();
		Task c = process.buildTask();
		ParallelGateway join = process.buildParallelGatewayConverging();
		EndEvent end = process.buildEndEvent();
		
		process.linkGateways(parallel, join);
		
		process.buildSequenceFlow(start, parallel);
		process.buildSequenceFlow(parallel, b);
		process.buildSequenceFlow(parallel, c);
		process.buildSequenceFlow(b, join);
		process.buildSequenceFlow(c, join);
		process.buildSequenceFlow(join, end);
		
		return process;
	}
	
	/**
	 * Créé un exemple avec une parallel gateway qui peut être simplifiée et supprimée par le
	 * {@link BpmnChangePatternHelper}.
	 * 
	 * @return
	 */
	public BpmnProcess createExampleWithUselessParallel() {
		
		BpmnProcess process = new BpmnProcess();
		
		// init du process
		StartEvent start = process.buildStartEvent();
		ParallelGateway parallel = process.buildParallelGatewayDiverging();
		Task c = process.buildTask();
		ParallelGateway join = process.buildParallelGatewayConverging();
		EndEvent end = process.buildEndEvent();
		
		process.linkGateways(parallel, join);
		
		process.buildSequenceFlow(start, parallel);
		process.buildSequenceFlow(parallel, c);
		process.buildSequenceFlow(parallel, join);
		process.buildSequenceFlow(c, join);
		process.buildSequenceFlow(join, end);
		
		return process;
	}
	
	/**
	 * Créé un exemple avec une exclusive gateway qui peut être simplifiée et supprimée par le
	 * {@link BpmnChangePatternHelper}.
	 * 
	 * @return
	 */
	public BpmnProcess createExampleWithUselessExclusive() {
		
		BpmnProcess process = new BpmnProcess();
		
		// init du process
		StartEvent start = process.buildStartEvent();
		ExclusiveGateway parallel = process.buildExclusiveGatewayDiverging();
		Task c = process.buildTask();
		ExclusiveGateway join = process.buildExclusiveGatewayConverging();
		EndEvent end = process.buildEndEvent();
		
		process.linkGateways(parallel, join);
		
		process.buildSequenceFlow(start, parallel);
		process.buildSequenceFlow(parallel, c);
		process.buildSequenceFlow(parallel, join);
		process.buildSequenceFlow(c, join);
		process.buildSequenceFlow(join, end);
		
		return process;
	}
	
	/**
	 * Créé un exemple avec une parallel gateway et une exclusive gateway qui peut être simplifiée et supprimée par le
	 * {@link BpmnChangePatternHelper}.
	 * 
	 * @return
	 */
	public BpmnProcess createExampleWithUselessParallelAndExclusive() {
		
		BpmnProcess process = new BpmnProcess();
		
		// init du process
		StartEvent start = process.buildStartEvent();
		ParallelGateway parallel = process.buildParallelGatewayDiverging();
		Task c = process.buildTask();
		ParallelGateway join = process.buildParallelGatewayConverging();
		ExclusiveGateway exclusive = process.buildExclusiveGatewayDiverging();
		Task d = process.buildTask();
		ExclusiveGateway merge = process.buildExclusiveGatewayConverging();
		EndEvent end = process.buildEndEvent();
		
		process.linkGateways(parallel, join);
		
		process.buildSequenceFlow(start, parallel);
		process.buildSequenceFlow(parallel, c);
		process.buildSequenceFlow(parallel, exclusive);
		process.buildSequenceFlow(exclusive, merge);
		process.buildSequenceFlow(exclusive, d);
		process.buildSequenceFlow(d, merge);
		process.buildSequenceFlow(merge, join);
		process.buildSequenceFlow(c, join);
		process.buildSequenceFlow(join, end);
		
		return process;
	}
	
	/**
	 * Construit un process avec : init -> exclusiveChoice -> (b & c) -> merge -> final
	 * 
	 * @return
	 */
	public BpmnProcess createExampleWithExclusiveChoice() {
		
		BpmnProcess process = new BpmnProcess();
		
		// init du process
		StartEvent start = process.buildStartEvent();
		ExclusiveGateway choice = process.buildExclusiveGatewayDiverging();
		Task b = process.buildTask();
		Task c = process.buildTask();
		ExclusiveGateway merge = process.buildExclusiveGatewayConverging();
		EndEvent end = process.buildEndEvent();
		
		process.linkGateways(choice, merge);
		
		process.buildSequenceFlow(start, choice);
		process.buildSequenceFlow(choice, b);
		process.buildSequenceFlow(choice, c);
		process.buildSequenceFlow(b, merge);
		process.buildSequenceFlow(c, merge);
		process.buildSequenceFlow(merge, end);
		
		return process;
	}
	
	/**
	 * Construit un process avec : init -> multiChoice -> (b & c) -> multiMerge -> final
	 * 
	 * @return
	 */
	public BpmnProcess createExampleWithStructuredSynchronizingMerge() {
		
		BpmnProcess process = new BpmnProcess();
		
		// init du process
		StartEvent start = process.buildStartEvent();
		InclusiveGateway choice = process.buildInclusiveGatewayDiverging();
		Task b = process.buildTask();
		Task c = process.buildTask();
		InclusiveGateway merge = process.buildInclusiveGatewayConverging();
		EndEvent end = process.buildEndEvent();
		
		process.linkGateways(choice, merge);
		
		process.buildSequenceFlow(start, choice);
		process.buildSequenceFlow(choice, b);
		process.buildSequenceFlow(choice, c);
		process.buildSequenceFlow(b, merge);
		process.buildSequenceFlow(c, merge);
		process.buildSequenceFlow(merge, end);
		
		return process;
	}
	
	/**
	 * Construit un process avec : init -> multiChoice -> (b & c) -> multiMerge -> final
	 * 
	 * @return
	 */
	public BpmnProcess createExampleWithMultiChoiceMultiMerge() {
		
		BpmnProcess process = new BpmnProcess();
		
		// init du process
		StartEvent start = process.buildStartEvent();
		InclusiveGateway choice = process.buildInclusiveGatewayDiverging();
		Task b = process.buildTask();
		Task c = process.buildTask();
		ExclusiveGateway merge = process.buildExclusiveGatewayConverging();
		EndEvent end = process.buildEndEvent();
		
		process.linkGateways(choice, merge);
		
		process.buildSequenceFlow(start, choice);
		process.buildSequenceFlow(choice, b);
		process.buildSequenceFlow(choice, c);
		process.buildSequenceFlow(b, merge);
		process.buildSequenceFlow(c, merge);
		process.buildSequenceFlow(merge, end);
		
		return process;
	}
	
	/**
	 * Créé un process avec un certains nombre de noeud.
	 * 
	 * @param number
	 * @return
	 */
	public BpmnProcess numberNodes(int number) {
		
		BpmnProcess process = new BpmnProcess();
		for (int i = 0; i < number; i++) {
			process.buildTask();
		}
		return process;
	}
	
	/**
	 * Créé un exemple pour les tests du SESE.
	 * 
	 * @return
	 */
	public BpmnProcess getExampleForSESE() {
		
		BpmnProcess process = new BpmnProcess();
		
		// init du process
		StartEvent start = process.buildStartEvent();
		Task a = process.buildTask();
		ExclusiveGateway choice = process.buildExclusiveGatewayDiverging();
		Task b = process.buildTask();
		Task c = process.buildTask();
		Task d = process.buildTask();
		Task e = process.buildTask();
		ExclusiveGateway merge = process.buildExclusiveGatewayConverging();
		Task f = process.buildTask();
		Task g = process.buildTask();
		EndEvent end = process.buildEndEvent();
		
		process.linkGateways(choice, merge);
		
		process.buildSequenceFlow(start, a);
		process.buildSequenceFlow(a, choice);
		process.buildSequenceFlow(choice, b);
		process.buildSequenceFlow(choice, d);
		process.buildSequenceFlow(b, c);
		process.buildSequenceFlow(d, e);
		process.buildSequenceFlow(c, merge);
		process.buildSequenceFlow(e, merge);
		process.buildSequenceFlow(merge, f);
		process.buildSequenceFlow(f, g);
		process.buildSequenceFlow(g, end);
		
		return process;
	}
	
	/**
	 * Créé un exemple avec une boucle.
	 * 
	 * @return
	 */
	public BpmnProcess getLoopExample() {
		
		BpmnProcess process = new BpmnProcess();
		
		StartEvent start = process.buildStartEvent();
		Task a = process.buildTask();
		ExclusiveGateway merge = process.buildExclusiveGatewayConverging();
		Task b = process.buildTask();
		Task c = process.buildTask();
		ExclusiveGateway choice = process.buildExclusiveGatewayDiverging();
		Task d = process.buildTask();
		EndEvent end = process.buildEndEvent();
		
		process.linkGateways(choice, merge);
		
		process.buildSequenceFlow(start, a);
		process.buildSequenceFlow(a, merge);
		process.buildSequenceFlow(merge, b);
		process.buildSequenceFlow(b, c);
		process.buildSequenceFlow(c, choice);
		process.buildSequenceFlow(choice, merge);
		process.buildSequenceFlow(choice, d);
		process.buildSequenceFlow(d, end);
		
		return process;
	}
	
	/**
	 * Créé un exemple avec une boucle complexe.
	 * 
	 * @return
	 */
	public BpmnProcess getComplexLoopExample() {
		
		BpmnProcess process = new BpmnProcess();
		
		StartEvent start = process.buildStartEvent();
		ExclusiveGateway merge1 = process.buildExclusiveGatewayConverging();
		Task a = process.buildTask();
		ExclusiveGateway choice2 = process.buildExclusiveGatewayDiverging();
		Task b = process.buildTask();
		Task c = process.buildTask();
		ExclusiveGateway merge2 = process.buildExclusiveGatewayConverging();
		ExclusiveGateway choice1 = process.buildExclusiveGatewayDiverging();
		Task d = process.buildTask();
		EndEvent end = process.buildEndEvent();
		
		process.linkGateways(choice1, merge1);
		process.linkGateways(choice2, merge2);
		
		process.buildSequenceFlow(start, merge1);
		process.buildSequenceFlow(merge1, a);
		process.buildSequenceFlow(a, choice2);
		process.buildSequenceFlow(choice2, b);
		process.buildSequenceFlow(choice2, c);
		process.buildSequenceFlow(b, merge2);
		process.buildSequenceFlow(c, merge2);
		process.buildSequenceFlow(merge2, choice1);
		process.buildSequenceFlow(choice1, d);
		process.buildSequenceFlow(choice1, merge1);
		process.buildSequenceFlow(d, end);
		
		return process;
	}
	
	/**
	 * Créé un exemple avec deux boucles, l'une a la suite de l'autre.
	 * 
	 * @return
	 */
	public BpmnProcess getDoubleLoopExample() {
		
		BpmnProcess process = new BpmnProcess();
		
		StartEvent start = process.buildStartEvent();
		ExclusiveGateway merge1 = process.buildExclusiveGatewayConverging();
		Task a = process.buildTask();
		ExclusiveGateway choice1 = process.buildExclusiveGatewayDiverging();
		
		ExclusiveGateway merge2 = process.buildExclusiveGatewayConverging();
		Task b = process.buildTask();
		ExclusiveGateway choice2 = process.buildExclusiveGatewayDiverging();
		EndEvent end = process.buildEndEvent();
		
		process.linkGateways(choice1, merge1);
		process.linkGateways(choice2, merge2);
		
		process.buildSequenceFlow(start, merge1);
		process.buildSequenceFlow(merge1, a);
		process.buildSequenceFlow(a, choice1);
		process.buildSequenceFlow(choice1, merge1);
		
		process.buildSequenceFlow(choice1, merge2);
		process.buildSequenceFlow(merge2, b);
		process.buildSequenceFlow(b, choice2);
		process.buildSequenceFlow(choice2, merge2);
		process.buildSequenceFlow(choice2, end);
		
		return process;
	}
	
	/**
	 * Créé un exemple avec une boucle qui peut être simplifiée et supprimée par le {@link BpmnChangePatternHelper}.
	 * 
	 * @return
	 */
	public BpmnProcess getUselessLoopExample() {
		
		BpmnProcess process = new BpmnProcess();
		
		StartEvent start = process.buildStartEvent();
		Task a = process.buildTask();
		ExclusiveGateway merge = process.buildExclusiveGatewayConverging();
		ExclusiveGateway choice = process.buildExclusiveGatewayDiverging();
		Task d = process.buildTask();
		EndEvent end = process.buildEndEvent();
		
		process.linkGateways(choice, merge);
		
		process.buildSequenceFlow(start, a);
		process.buildSequenceFlow(a, merge);
		process.buildSequenceFlow(merge, choice);
		process.buildSequenceFlow(choice, merge);
		process.buildSequenceFlow(choice, d);
		process.buildSequenceFlow(d, end);
		
		return process;
	}
	
	/**
	 * Créé un exemple pour les tests sur le {@link GatewayManager}.
	 * 
	 * @return
	 */
	public BpmnProcess getExampleForSESE2() {
		
		BpmnProcess process = new BpmnProcess();
		
		StartEvent start = process.buildStartEvent();
		ExclusiveGateway choice1 = process.buildExclusiveGatewayDiverging();
		ExclusiveGateway merge1 = process.buildExclusiveGatewayConverging();
		
		ExclusiveGateway choice2 = process.buildExclusiveGatewayDiverging();
		ExclusiveGateway merge2 = process.buildExclusiveGatewayConverging();
		
		Task a = process.buildTask();
		Task b = process.buildTask();
		Task c = process.buildTask();
		Task d = process.buildTask();
		Task e = process.buildTask();
		EndEvent end = process.buildEndEvent();
		
		// process.linkGateways(choice1, merge1);
		// process.linkGateways(choice2, merge2);
		
		process.buildSequenceFlow(start, choice1);
		process.buildSequenceFlow(choice1, choice2);
		process.buildSequenceFlow(choice1, c);
		process.buildSequenceFlow(c, d);
		process.buildSequenceFlow(d, e);
		process.buildSequenceFlow(e, merge1);
		process.buildSequenceFlow(choice2, a);
		process.buildSequenceFlow(choice2, b);
		process.buildSequenceFlow(a, merge2);
		process.buildSequenceFlow(b, merge2);
		process.buildSequenceFlow(merge2, merge1);
		process.buildSequenceFlow(merge1, end);
		
		return process;
	}
}
