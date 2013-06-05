package fr.lip6.move.processGenerator.structuralConstraint;



public abstract class AbstractStructuralConstraintFactory {
	
	public IStructuralConstraint newWorkflowPatternConstraint(Object eBpmnWorkflowPattern) throws Exception {
		if (eBpmnWorkflowPattern instanceof IEnumWorkflowPattern) {
			return ((IEnumWorkflowPattern) eBpmnWorkflowPattern).newInstance();
		}
		throw new Exception("The parameter of the newWorkflowPatternConstraint method is not a IEnumWorkflowPattern.");
	}
	
	public abstract IStructuralConstraint newManualOclConstraint(String query);
	
	public abstract IStructuralConstraint newElementConstraint(Object eElement) throws Exception;
}
