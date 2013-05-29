package fr.lip6.move.processGenerator.bpmn2.workflowPattern;

import fr.lip6.move.processGenerator.Quantity;
import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;


public class WPCheckerBpmn {
	
	private AbstractBpmnWorkflowPattern pattern;
	
	public WPCheckerBpmn(BpmnProcess process, String pattern) throws BpmnException {
		super();
		this.pattern = WPFactoyBpmn.getInstance().newInstance(process, pattern);
	}
	
	public boolean check(Quantity quantity, int x) throws BpmnException {
		if (x < 0)
			throw new BpmnException("La quantité du pattern doit être supérieure ou égale à 0.");
		
		Object result = pattern.matches();
		if (!(result instanceof Integer))
			return false;
		
		int resultat = ((Integer)result).intValue();
		switch (quantity) {
			case MORE:
				return resultat > x;
			case LESS:
				return resultat < x;
			case EQUAL:
				return resultat == x;
			case MORE_OR_EQUAL:
				return resultat >= x;
			case LESS_OR_EQUAL:
				return resultat <= x;
		}
		return false;
	}
}
