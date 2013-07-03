package fr.lip6.move.processGenerator.structuralConstraint.bpmn;

import fr.lip6.move.processGenerator.IEnumElement;
import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.bpmn2.EBpmnElement;
import fr.lip6.move.processGenerator.structuralConstraint.AbstractStructuralConstraintFactory;
import fr.lip6.move.processGenerator.structuralConstraint.IStructuralConstraint;

/**
 * Représente la factory des contraintes structurelles BPMN.
 * @author Vincent
 *
 */
public class BpmnStructuralConstraintFactory extends AbstractStructuralConstraintFactory {

	public static final BpmnStructuralConstraintFactory instance = new BpmnStructuralConstraintFactory();
	private BpmnStructuralConstraintFactory() {}
	
	@Override
	public IStructuralConstraint newManualOclConstraint(String query) {
		return new BpmnManualOclConstraint(query);
	}

	@Override
	public IStructuralConstraint newElementConstraint(IEnumElement eElement) throws BpmnException {
		if (eElement instanceof EBpmnElement) {
			return new BpmnElementConstraint((EBpmnElement) eElement);
		}
		throw new BpmnException("The parameter of the newElementConstraint method is not a EBpmnElement.");
	}
}
