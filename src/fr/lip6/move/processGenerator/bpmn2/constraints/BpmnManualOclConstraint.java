package fr.lip6.move.processGenerator.bpmn2.constraints;

/**
 * Représente une contrainte OCL écrite à la main par l'utilisateur.
 * @author Vincent
 *
 */
public class BpmnManualOclConstraint extends AbstractBpmnOclSolver {
	
	public BpmnManualOclConstraint(String query) {
		super();
		super.setOclQuery(query);
	}
}
