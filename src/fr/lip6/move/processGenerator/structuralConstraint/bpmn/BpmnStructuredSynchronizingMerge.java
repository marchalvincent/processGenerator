package fr.lip6.move.processGenerator.structuralConstraint.bpmn;

import java.util.List;

import org.eclipse.bpmn2.Gateway;
import org.eclipse.bpmn2.GatewayDirection;
import org.eclipse.bpmn2.InclusiveGateway;
import org.eclipse.bpmn2.Task;

import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.bpmn2.utils.Filter;
import fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.changePattern.SESEManager;
import fr.lip6.move.processGenerator.structuralConstraint.AbstractJavaSolver;
import fr.lip6.move.processGenerator.structuralConstraint.IConstraintRepresentation;

/**
 * Représente le WP7 - Structured Synchronizing Merge.
 * @author Vincent
 *
 */
public class BpmnStructuredSynchronizingMerge extends AbstractJavaSolver {
	
	public BpmnStructuredSynchronizingMerge() {
		super();
	}

	@Override
	public int matches(Object object) throws Exception {

		int countTotal = 0;
		
		if (!(object instanceof BpmnProcess)) 
			return countTotal;
		
		BpmnProcess process = (BpmnProcess) object;
		
		// on parcours chaque flowElement à la recherche des InclusiveGateway diverging
		List<InclusiveGateway> list = Filter.byType(InclusiveGateway.class, process.getProcess().getFlowElements(), GatewayDirection.DIVERGING);
		for (InclusiveGateway gatewayDiverging : list) {
				Gateway gatewayConverging = SESEManager.instance.findTwinGateway(process, gatewayDiverging);
				// on a la porte fermante, maintenant il faut vérifier que c'est le bon type (dans ce workflow pattern on cherche une InclusiveGateway fermante)
				if (gatewayConverging instanceof InclusiveGateway) 
					countTotal++;
		}
		
		return countTotal;
	}

	@Override
	public IConstraintRepresentation getRepresentation() {
		ConstraintRepresentation representation = new ConstraintRepresentation();
		
		// on construit les noeuds 
		InclusiveGateway choice = representation.buildInclusiveGatewayDiverging();
		Task a = representation.buildTask();
		Task b = representation.buildTask();
		InclusiveGateway merge = representation.buildInclusiveGatewayConverging();

		representation.linkGatewys(choice, merge);
		
		// puis les arcs
		representation.buildSequenceFlow(choice, a);
		representation.buildSequenceFlow(choice, b);
		representation.buildSequenceFlow(a, merge);
		representation.buildSequenceFlow(b, merge);
		
		// on définit le début et la fin
		representation.setBegin(choice);
		representation.setEnd(merge);
		
		return representation;
	}
}
