package fr.lip6.move.processGenerator.file.bpmn2;

import org.eclipse.bpmn2.EndEvent;
import org.eclipse.bpmn2.ExclusiveGateway;
import org.eclipse.bpmn2.ParallelGateway;
import org.eclipse.bpmn2.StartEvent;
import org.eclipse.bpmn2.Task;


public class BpmnBuilder {

	public static BpmnProcess initialFinal() {
		BpmnProcess process = new BpmnProcess();

		// les noeuds
		StartEvent start = process.buildStartEvent();
		EndEvent end = process.buildEndEvent();

		// les arcs
		process.buildSequenceFlow(start, end);

		return process;
	}

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
		ParallelGateway decision = process.buildParallelGatewayDiverging();
		Task b = process.buildTask();
		Task c = process.buildTask();
		ParallelGateway merge = process.buildParallelGatewayConverging();
		Task d = process.buildTask();
		Task e = process.buildTask();
		Task f = process.buildTask();
		EndEvent end = process.buildEndEvent();

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
		ParallelGateway parallel = process.buildParallelGatewayDiverging();
		Task b = process.buildTask();
		Task c = process.buildTask();
		ParallelGateway join = process.buildParallelGatewayConverging();
		EndEvent end = process.buildEndEvent();

		process.buildSequenceFlow(start, parallel);
		process.buildSequenceFlow(parallel, b);
		process.buildSequenceFlow(parallel, c);
		process.buildSequenceFlow(b, join);
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
		ExclusiveGateway choice = process.buildExclusiveGatewayDiverging();
		Task b = process.buildTask();
		Task c = process.buildTask();
		ExclusiveGateway merge = process.buildExclusiveGatewayConverging();
		EndEvent end = process.buildEndEvent();

		process.buildSequenceFlow(start, choice);
		process.buildSequenceFlow(choice, b);
		process.buildSequenceFlow(choice, c);
		process.buildSequenceFlow(b, merge);
		process.buildSequenceFlow(c, merge);
		process.buildSequenceFlow(merge, end);
		
		return process;
	}
}
