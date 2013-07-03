package fr.lip6.move.processGenerator.uml2.constraints;

import fr.lip6.move.processGenerator.bpmn2.constraints.AbstractBpmnOclSolver;

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
