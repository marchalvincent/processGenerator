package fr.lip6.move.processGenerator.bpmn2.workflowPattern;

import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;


public class WPFactoyBpmn {

	private static WPFactoyBpmn instance = new WPFactoyBpmn();
	
	private WPFactoyBpmn() {}
	
	public static WPFactoyBpmn getInstance() {
		return instance;
	}
	
	public AbstractBpmnWorkflowPattern newInstance(BpmnProcess process, String name) {
		if (name.toLowerCase().equals("sequence")) {
			return this.newSequence(process);
		} else if (name.toLowerCase().equals("parallelsplit")) {
			return this.newParallelSplit(process);
		} else if (name.toLowerCase().equals("synchronization")) {
			return this.newSynchronization(process);
		} else if (name.toLowerCase().equals("exclusivechoice")) {
			return this.newExclusiveChoice(process);
		} else if (name.toLowerCase().equals("simplemerge")) {
			return this.newSimpleMerge(process);
		}
		return null;
	}
	
	private AbstractBpmnWorkflowPattern newSimpleMerge(BpmnProcess process) {
		return new BpmnSimpleMerge(process);
	}

	private AbstractBpmnWorkflowPattern newExclusiveChoice(BpmnProcess process) {
		return new BpmnExclusiveChoice(process);
	}

	private AbstractBpmnWorkflowPattern newSynchronization(BpmnProcess process) {
		return new BpmnSynchronization(process);
	}

	private AbstractBpmnWorkflowPattern newSequence(BpmnProcess process) {
		return new BpmnSequence(process);
	}

	private AbstractBpmnWorkflowPattern newParallelSplit(BpmnProcess process) {
		return new BpmnParallelSplit(process);
	}
}
