package fr.lip6.move.processGenerator.structuralConstraint.uml;

import fr.lip6.move.processGenerator.structuralConstraint.AbstractOclSolver;
import fr.lip6.move.processGenerator.structuralConstraint.uml.query.UmlQueryReaderHelper;


public class UmlSequence extends AbstractOclSolver {
	
	public UmlSequence() throws UmlException {
		super();
		System.err.println("Attention la requête OCL de UmlSequence n'est pas définie.");
		super.setOclQuery(UmlQueryReaderHelper.read("sequence"));
	}
}
