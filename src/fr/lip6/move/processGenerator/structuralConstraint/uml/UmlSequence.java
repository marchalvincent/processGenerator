package fr.lip6.move.processGenerator.structuralConstraint.uml;

import fr.lip6.move.processGenerator.structuralConstraint.bpmn.AbstractBpmnOclSolver;

/**
 * Représente le WP1 - Sequence.
 * @author Vincent
 *
 */
public class UmlSequence extends AbstractBpmnOclSolver {
	
	public UmlSequence() throws UmlException {
		super();
		System.err.println("Attention la requête OCL de UmlSequence n'est pas définie.");
		super.setOclQuery("");
	}
}
