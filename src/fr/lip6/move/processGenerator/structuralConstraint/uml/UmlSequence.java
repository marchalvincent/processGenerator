package fr.lip6.move.processGenerator.structuralConstraint.uml;

import fr.lip6.move.processGenerator.structuralConstraint.AbstractOclSolver;
import fr.lip6.move.processGenerator.structuralConstraint.uml.query.UmlQueryReaderHelper;


public class UmlSequence extends AbstractOclSolver {
	
	public UmlSequence() {
		super();
		// TODO définir la requête
		System.err.println("Attention la requête OCL de UmlSequence n'est pas définie.");
		try {
			super.setOclQuery(UmlQueryReaderHelper.read("sequence"));
		} catch (UmlException e) {
			e.printStackTrace();
		}
	}
}
