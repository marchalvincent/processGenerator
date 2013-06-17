package fr.lip6.move.processGenerator.structuralConstraint.bpmn;

import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.GatewayDirection;
import org.eclipse.bpmn2.Process;
import org.eclipse.emf.ecore.EObject;
import fr.lip6.move.processGenerator.bpmn2.MyGateway;
import fr.lip6.move.processGenerator.bpmn2.MyInclusiveGateway;
import fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.changePattern.SingleEntrySingleExitManager;
import fr.lip6.move.processGenerator.structuralConstraint.AbstractJavaSolver;


public class BpmnStructuredSynchronizingMerge extends AbstractJavaSolver {
	
	public BpmnStructuredSynchronizingMerge() {
		super();
	}

	@Override
	public int matches(EObject eObject) throws Exception {

		int countTotal = 0;
		
		if (!(eObject instanceof org.eclipse.bpmn2.Process)) 
			return countTotal;
		
		Process process = (Process) eObject;
		SingleEntrySingleExitManager seseManager = new SingleEntrySingleExitManager();
		
		// on parcours chaque flowElement à la recherche des InclusiveGateway diverging
		for (FlowElement element : process.getFlowElements()) {
			if (element instanceof MyInclusiveGateway && ((MyInclusiveGateway) element).getGatewayDirection().equals(GatewayDirection.DIVERGING)) {
				MyInclusiveGateway inclusive = (MyInclusiveGateway) element;
				MyGateway gatewayConverging = seseManager.getEndOfGateway(inclusive);
				// on a la porte fermante, maintenant il faut vérifier que c'est le bon type (dans ce workflow pattern on cherche une InclusiveGateway fermante)
				if (gatewayConverging instanceof MyInclusiveGateway) 
					countTotal++;
			}
		}
		
		return countTotal;
	}
}
