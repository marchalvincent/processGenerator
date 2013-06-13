package fr.lip6.move.processGenerator.structuralConstraint.bpmn;

import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.bpmn2.EBpmnElement;
import fr.lip6.move.processGenerator.structuralConstraint.AbstractStructuralConstraintFactory;
import fr.lip6.move.processGenerator.structuralConstraint.IStructuralConstraint;


public class StructuralConstraintFactory extends AbstractStructuralConstraintFactory {

	private static StructuralConstraintFactory instance = new StructuralConstraintFactory();
	
	private StructuralConstraintFactory() {}
	
	public static StructuralConstraintFactory getInstance() {
		return instance;
	}
	
	@Override
	public IStructuralConstraint newManualOclConstraint(String query) {
		return new BpmnManualOclConstraint(query);
	}

	@Override
	public IStructuralConstraint newElementConstraint(Object eElement) throws BpmnException {
		if (eElement instanceof EBpmnElement) {
			return new BpmnElementConstraint((EBpmnElement) eElement);
		}
		throw new BpmnException("The parameter of the newElementConstraint method is not a EBpmnElement.");
	}
}
