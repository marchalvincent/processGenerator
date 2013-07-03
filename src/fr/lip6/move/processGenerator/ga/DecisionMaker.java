package fr.lip6.move.processGenerator.ga;

import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.bpmn2.constraints.BpmnStructuralConstraintFactory;
import fr.lip6.move.processGenerator.constraint.AbstractStructuralConstraintFactory;
import fr.lip6.move.processGenerator.uml2.UmlProcess;
import fr.lip6.move.processGenerator.uml2.constraints.UmlStructuralConstraintFactory;

public class DecisionMaker {
	
	private boolean isBpmn;
	
	public DecisionMaker(String typeFile) {
		super();
		isBpmn = typeFile.toLowerCase().contains("bpmn");
	}
	
	public AbstractStructuralConstraintFactory getStructuralConstraintFactory () {
		if (isBpmn)
			return BpmnStructuralConstraintFactory.instance;
		return UmlStructuralConstraintFactory.instance;
	}
	
	public String getTypeFile () {
		if (isBpmn)
			return "bpmn";
		return "uml";
	}
	
	public boolean isBpmn () {
		return isBpmn;
	}
	
	public Object setInitialProcess (Object initialProcess) throws GeneticException {
		
		if (initialProcess == null)
			throw new GeneticException("The initial process is null.");
		
		if (isBpmn && initialProcess instanceof BpmnProcess)
			return initialProcess;
		else if (!isBpmn && initialProcess instanceof UmlProcess)
			return initialProcess;
		
		throw new GeneticException("The initial process is not matching to the type file selected. Process type : "
				+ initialProcess.getClass().getSimpleName() + ". File type " + ((isBpmn) ? "bpmn" : "uml"));
	}
}
