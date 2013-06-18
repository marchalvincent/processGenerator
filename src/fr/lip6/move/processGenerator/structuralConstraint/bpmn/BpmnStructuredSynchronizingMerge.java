package fr.lip6.move.processGenerator.structuralConstraint.bpmn;

import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.Gateway;
import org.eclipse.bpmn2.GatewayDirection;
import org.eclipse.bpmn2.InclusiveGateway;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.changePattern.SESEManager;
import fr.lip6.move.processGenerator.structuralConstraint.AbstractJavaSolver;

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
		for (FlowElement element : process.getProcess().getFlowElements()) {
			if (element instanceof InclusiveGateway && ((InclusiveGateway) element).getGatewayDirection().equals(GatewayDirection.DIVERGING)) {
				InclusiveGateway inclusive = (InclusiveGateway) element;
				Gateway gatewayConverging = SESEManager.instance.getEndOfGateway(process, inclusive);
				// on a la porte fermante, maintenant il faut vérifier que c'est le bon type (dans ce workflow pattern on cherche une InclusiveGateway fermante)
				if (gatewayConverging instanceof InclusiveGateway) 
					countTotal++;
			}
		}
		
		return countTotal;
	}
}
