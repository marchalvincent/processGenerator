package fr.lip6.move.processGenerator.bpmn2.constraints.impl;

import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.bpmn2.EBpmnElement;
import fr.lip6.move.processGenerator.bpmn2.utils.BpmnFilter;
import fr.lip6.move.processGenerator.constraint.AbstractJavaSolver;

/**
 * Cette contrainte compte le nombre d'élément donné qu'il y a dans un process. Par exemple : le nombre de Task.
 * 
 * @author Vincent
 * 
 */
public class BpmnElementConstraint extends AbstractJavaSolver {
	
	private EBpmnElement element;
	
	public BpmnElementConstraint(EBpmnElement data) {
		super();
		element = data;
	}
	
	@Override
	public int matches(Object object) throws Exception {
		if (!(object instanceof BpmnProcess)) {
			System.err.println("Matches method : The object is not a " + BpmnProcess.class.getSimpleName() + ".");
			return 0;
		}
		BpmnProcess process = (BpmnProcess) object;
		return BpmnFilter.byType(element.getAssociatedClass(), process.getProcess().getFlowElements()).size();
	}
}
