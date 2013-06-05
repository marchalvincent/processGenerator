package fr.lip6.move.processGenerator.structuralConstraint.bpmn;

import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.bpmn2.EBpmnElement;
import fr.lip6.move.processGenerator.structuralConstraint.AbstractOclSolver;
import fr.lip6.move.processGenerator.structuralConstraint.bpmn.query.BpmnQueryReaderHelper;


public class BpmnElementConstraint extends AbstractOclSolver {
	
	public BpmnElementConstraint(EBpmnElement data) throws BpmnException {
		super();
		String query = BpmnQueryReaderHelper.read("elementConstraint");
		query = query.replace("dynamic", data.toString());
		super.setOclQuery(query);
	}
}
