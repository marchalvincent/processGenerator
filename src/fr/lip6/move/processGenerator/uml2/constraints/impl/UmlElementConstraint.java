package fr.lip6.move.processGenerator.uml2.constraints.impl;

import fr.lip6.move.processGenerator.uml2.EUmlElement;
import fr.lip6.move.processGenerator.uml2.constraints.AbstractUmlOclSolver;

/**
 * Cette contrainte compte le nombre d'élément donné qu'il y a dans un process. Par exemple : le nombre d'OpaqueAction.
 * 
 * @author Vincent
 * 
 */
public class UmlElementConstraint extends AbstractUmlOclSolver {
	
	public UmlElementConstraint(EUmlElement data) {
		super();
		super.setOclQuery(data.toString() + ".allInstances()->size()");
	}
}
