package fr.lip6.move.processGenerator.structuralConstraint.bpmn;

import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.structuralConstraint.AbstractOclSolver;
import fr.lip6.move.processGenerator.structuralConstraint.bpmn.query.BpmnQueryReaderHelper;


public class BpmnElementConstraint extends AbstractOclSolver {

	public BpmnElementConstraint(String element) {
		super();
		try {
			String query = BpmnQueryReaderHelper.read("elementConstraint");
			query.replace("%dynamic%", element);
			super.setOclQuery(query);
			
		} catch (BpmnException e) {
			e.printStackTrace();
		}
	}
}
