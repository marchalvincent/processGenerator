package fr.lip6.move.processGenerator.uml2.constraints.impl;

import fr.lip6.move.processGenerator.uml2.EUmlElement;
import fr.lip6.move.processGenerator.uml2.constraints.AbstractUmlOclSolver;

/**
 * Cette contrainte compte le nombre d'élément donné qu'il y a dans un process. Par exemple : le nombre d'OpaqueAction.
 * 
 * @author Vincent
 * @deprecated {@link UmlElementConstraint} est plus performante.
 */
public class UmlElementConstraintOcl extends AbstractUmlOclSolver {
	
	public UmlElementConstraintOcl(EUmlElement data) {
		super();
		super.setOclQuery(data.toString() + ".allInstances()->size()");
	}
}
