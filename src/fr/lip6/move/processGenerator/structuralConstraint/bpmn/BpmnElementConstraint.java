package fr.lip6.move.processGenerator.structuralConstraint.bpmn;

import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.bpmn2.EBpmnElement;
import fr.lip6.move.processGenerator.structuralConstraint.AbstractOclSolver;


public class BpmnElementConstraint extends AbstractOclSolver {
	
	public BpmnElementConstraint(EBpmnElement data) throws BpmnException {
		super();
		super.setOclQuery(data.toString() + ".allInstances()->size()");
	}
}
