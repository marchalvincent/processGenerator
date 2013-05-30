package fr.lip6.move.processGenerator.structuralConstraint.bpmn;

import fr.lip6.move.processGenerator.structuralConstraint.IStructuralConstraint;


public class BpmnStructuralConstraintFactory {

	private static BpmnStructuralConstraintFactory instance = new BpmnStructuralConstraintFactory();
	
	private BpmnStructuralConstraintFactory() {}
	
	public static BpmnStructuralConstraintFactory getInstance() {
		return instance;
	}
	
	public IStructuralConstraint newWorkflowPatternConstraint(String name) {
		
		if (name.toLowerCase().equals("sequence")) {
			return new BpmnSequence();
		} else if (name.toLowerCase().equals("parallelsplit")) {
			return new BpmnParallelSplit();
		} else if (name.toLowerCase().equals("synchronization")) {
			return new BpmnSynchronization();
		} else if (name.toLowerCase().equals("exclusivechoice")) {
			return new BpmnExclusiveChoice();
		} else if (name.toLowerCase().equals("simplemerge")) {
			return new BpmnSimpleMerge();
		}
		return null;
	}
	
	public IStructuralConstraint newElementConstraint(String nameElement) {
		return new BpmnElementConstraint(nameElement);
	}
	
	public IStructuralConstraint newManualOclConstraint(String query) {
		return new BpmnManualOclConstraint(query);
	}
}
