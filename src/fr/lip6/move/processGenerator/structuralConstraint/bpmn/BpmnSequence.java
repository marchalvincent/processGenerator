package fr.lip6.move.processGenerator.structuralConstraint.bpmn;

import org.eclipse.bpmn2.Task;

import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.structuralConstraint.AbstractOclSolver;
import fr.lip6.move.processGenerator.structuralConstraint.IConstraintRepresentation;
import fr.lip6.move.processGenerator.structuralConstraint.bpmn.query.BpmnQueryReaderHelper;

/**
 * Représente le WP1 - Sequence.
 * @author Vincent
 *
 */
public class BpmnSequence extends AbstractOclSolver {

	public BpmnSequence() throws BpmnException {
		super();
		super.setOclQuery(BpmnQueryReaderHelper.read("sequence"));
	}
	
	@Override
	public IConstraintRepresentation getRepresentation() {
		ConstraintRepresentation representation = new ConstraintRepresentation();
		
		// on construit les noeuds 
		Task a = representation.buildTask();
		Task b = representation.buildTask();
		
		// puis les arcs
		representation.buildSequenceFlow(a, b);
		
		// on définit le début et la fin
		representation.setBegin(a);
		representation.setEnd(b);
		
		return representation;
	}
}
