package fr.lip6.move.processGenerator.uml2.constraints;

/**
 * Représente une contrainte OCL écrite à la main par l'utilisateur.
 * 
 * @author Vincent
 * 
 */
public class UmlManualOclConstraint extends AbstractUmlOclSolver {
	
	public UmlManualOclConstraint(String query) {
		super();
		super.setOclQuery(query);
	}
}
