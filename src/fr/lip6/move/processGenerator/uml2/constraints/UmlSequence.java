package fr.lip6.move.processGenerator.uml2.constraints;

import fr.lip6.move.processGenerator.uml2.UmlException;

/**
 * Représente le WP1 - Sequence.
 * 
 * @author Vincent
 * 
 */
public class UmlSequence extends AbstractUmlOclSolver {
	
	public UmlSequence() throws UmlException {
		super();
		// TODO
		System.err.println("Attention la requête OCL de UmlSequence n'est pas définie.");
		super.setOclQuery("");
	}
}
