package fr.lip6.move.processGenerator.bpmn2.constraints.impl;

import fr.lip6.move.processGenerator.bpmn2.constraints.AbstractBpmnOclSolver;

/**
 * Représente une contrainte OCL écrite à la main par l'utilisateur.
 * 
 * @author Vincent
 * 
 */
public class BpmnManualOclConstraint extends AbstractBpmnOclSolver {
	
	public BpmnManualOclConstraint(String query) {
		super();
		super.setOclQuery(query);
	}
}
