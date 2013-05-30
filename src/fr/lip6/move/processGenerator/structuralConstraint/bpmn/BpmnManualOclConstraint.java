package fr.lip6.move.processGenerator.structuralConstraint.bpmn;

import fr.lip6.move.processGenerator.structuralConstraint.AbstractOclSolver;


public class BpmnManualOclConstraint extends AbstractOclSolver {
	
	public BpmnManualOclConstraint(String query) {
		super();
		super.setOclQuery(query);
	}
}
