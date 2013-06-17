package fr.lip6.move.processGenerator.bpmn2;

import org.eclipse.bpmn2.EndEvent;
import org.eclipse.bpmn2.StartEvent;
import org.eclipse.bpmn2.Task;


public class BpmnBuilder {

	/**
	 * Construit un process simple : 
	 * StartEvent -> EndEvent.
	 * @return
	 */
	public static BpmnProcess initialFinal() {
		BpmnProcess process = new BpmnProcess();

		// les noeuds
		StartEvent start = process.buildStartEvent();
		EndEvent end = process.buildEndEvent();

		// les arcs
		process.buildSequenceFlow(start, end);

		return process;
	}

	/**
	 * Construit un process simple : 
	 * StartEvent -> Task -> Task -> EndEvent.
	 * @return
	 */
	public static BpmnProcess initialABFinal() {
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
	 * Construit un process simple : 
	 * StartEvent -> Task -> Task -> Task -> EndEvent.
	 * @return
	 */
	public static BpmnProcess initialABCFinal() {
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
	 * Construit un process avec : 
	 *  init -> a -> parallel -> (b & c) -> join -> d -> e -> f -> final
	 *  @return
	 */
	public static BpmnProcess createExampleWithParallel() {
		BpmnProcess process = new BpmnProcess();
		
		// les noeuds
		StartEvent start = process.buildStartEvent();
		Task a = process.buildTask();
		MyParallelGateway decision = process.buildParallelGatewayDiverging();
		Task b = process.buildTask();
		Task c = process.buildTask();
		MyParallelGateway merge = process.buildParallelGatewayConverging();
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
	 * Construit un process avec : 
	 *  init -> parallel -> (b & c) -> join -> final
	 * @return
	 */
	public static BpmnProcess createExampleWithParallel2() {

		BpmnProcess process = new BpmnProcess();
		
		// init du process
		StartEvent start = process.buildStartEvent();
		MyParallelGateway parallel = process.buildParallelGatewayDiverging();
		Task b = process.buildTask();
		Task c = process.buildTask();
		MyParallelGateway join = process.buildParallelGatewayConverging();
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

	public static BpmnProcess createExampleWithUselessParallel() {

		BpmnProcess process = new BpmnProcess();
		
		// init du process
		StartEvent start = process.buildStartEvent();
		MyParallelGateway parallel = process.buildParallelGatewayDiverging();
		Task c = process.buildTask();
		MyParallelGateway join = process.buildParallelGatewayConverging();
		EndEvent end = process.buildEndEvent();

		process.linkGateways(parallel, join);
		
		process.buildSequenceFlow(start, parallel);
		process.buildSequenceFlow(parallel, c);
		process.buildSequenceFlow(parallel, join);
		process.buildSequenceFlow(c, join);
		process.buildSequenceFlow(join, end);
		
		return process;
	}
	
	public static BpmnProcess createExampleWithUselessExclusive() {

		BpmnProcess process = new BpmnProcess();
		
		// init du process
		StartEvent start = process.buildStartEvent();
		MyExclusiveGateway parallel = process.buildExclusiveGatewayDiverging();
		Task c = process.buildTask();
		MyExclusiveGateway join = process.buildExclusiveGatewayConverging();
		EndEvent end = process.buildEndEvent();
		
		process.linkGateways(parallel, join);

		process.buildSequenceFlow(start, parallel);
		process.buildSequenceFlow(parallel, c);
		process.buildSequenceFlow(parallel, join);
		process.buildSequenceFlow(c, join);
		process.buildSequenceFlow(join, end);
		
		return process;
	}
	
	public static BpmnProcess createExampleWithUselessParallelAndExclusive() {

		BpmnProcess process = new BpmnProcess();
		
		// init du process
		StartEvent start = process.buildStartEvent();
		MyParallelGateway parallel = process.buildParallelGatewayDiverging();
		Task c = process.buildTask();
		MyParallelGateway join = process.buildParallelGatewayConverging();
		MyExclusiveGateway exclusive = process.buildExclusiveGatewayDiverging();
		Task d = process.buildTask();
		MyExclusiveGateway merge = process.buildExclusiveGatewayConverging();
		EndEvent end = process.buildEndEvent();

		process.linkGateways(parallel, join);
		process.linkGateways(exclusive, merge);
		
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
	 * Construit un process avec : 
	 *  init -> exclusiveChoice -> (b & c) -> merge -> final
	 * @return
	 */
	public static BpmnProcess createExampleWithExclusiveChoice() {

		BpmnProcess process = new BpmnProcess();
		
		// init du process
		StartEvent start = process.buildStartEvent();
		MyExclusiveGateway choice = process.buildExclusiveGatewayDiverging();
		Task b = process.buildTask();
		Task c = process.buildTask();
		MyExclusiveGateway merge = process.buildExclusiveGatewayConverging();
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
	 * Construit un process avec : 
	 *  init -> multiChoice -> (b & c) -> multiMerge -> final
	 * @return
	 */
	public static BpmnProcess createExampleWithStructuredSynchronizingMerge() {

		BpmnProcess process = new BpmnProcess();
		
		// init du process
		StartEvent start = process.buildStartEvent();
		MyInclusiveGateway choice = process.buildInclusiveGatewayDiverging();
		Task b = process.buildTask();
		Task c = process.buildTask();
		MyInclusiveGateway merge = process.buildInclusiveGatewayConverging();
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
	 * Construit un process avec : 
	 *  init -> multiChoice -> (b & c) -> multiMerge -> final
	 * @return
	 */
	public static BpmnProcess createExampleWithMultiChoiceMultiMerge() {

		BpmnProcess process = new BpmnProcess();
		
		// init du process
		StartEvent start = process.buildStartEvent();
		MyInclusiveGateway choice = process.buildInclusiveGatewayDiverging();
		Task b = process.buildTask();
		Task c = process.buildTask();
		MyExclusiveGateway merge = process.buildExclusiveGatewayConverging();
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
	
	public static BpmnProcess numberNodes(int number) {

		BpmnProcess process = new BpmnProcess();
		for (int i = 0; i < number; i++) {
			process.buildTask();
		}
		return process;
	}
	
	public static BpmnProcess getExampleForSESE() {

		BpmnProcess process = new BpmnProcess();
		
		// init du process
		StartEvent start = process.buildStartEvent();
		Task a = process.buildTask();
		MyExclusiveGateway choice = process.buildExclusiveGatewayDiverging();
		Task b = process.buildTask();
		Task c = process.buildTask();
		Task d = process.buildTask();
		Task e = process.buildTask();
		MyExclusiveGateway merge = process.buildExclusiveGatewayConverging();
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
}
