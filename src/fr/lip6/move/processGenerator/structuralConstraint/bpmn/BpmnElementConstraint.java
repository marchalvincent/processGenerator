package fr.lip6.move.processGenerator.structuralConstraint.bpmn;

import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.bpmn2.EBpmnElement;

/**
 * Cette contrainte compte le nombre d'élément donné qu'il y a dans un process.
 * Par exemple : le nombre de Task.
 * @author Vincent
 *
 */
public class BpmnElementConstraint extends AbstractBpmnOclSolver {
	
	public BpmnElementConstraint(EBpmnElement data) throws BpmnException {
		super();
		super.setOclQuery(data.toString() + ".allInstances()->size()");
	}
}
