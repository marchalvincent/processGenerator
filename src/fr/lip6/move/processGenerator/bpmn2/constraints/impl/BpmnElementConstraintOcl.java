package fr.lip6.move.processGenerator.bpmn2.constraints.impl;

import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.bpmn2.EBpmnElement;
import fr.lip6.move.processGenerator.bpmn2.constraints.AbstractBpmnOclSolver;

/**
 * Cette contrainte compte le nombre d'élément donné qu'il y a dans un process. Par exemple : le nombre de Task.
 * 
 * @author Vincent
 * @deprecated Use {@link BpmnElementConstraint}.
 */
public class BpmnElementConstraintOcl extends AbstractBpmnOclSolver {
	
	public BpmnElementConstraintOcl(EBpmnElement data) throws BpmnException {
		super();
		super.setOclQuery(data.toString() + ".allInstances()->size()");
	}
}
