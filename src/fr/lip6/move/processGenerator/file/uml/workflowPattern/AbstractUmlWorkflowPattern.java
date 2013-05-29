package fr.lip6.move.processGenerator.file.uml.workflowPattern;

import java.util.ArrayList;
import java.util.List;
import fr.lip6.move.processGenerator.file.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.workflowPattern.IWorkflowPattern;


public class AbstractUmlWorkflowPattern implements IWorkflowPattern {
	
	public static List<Class<? extends IWorkflowPattern>> patterns;
	static {
		patterns = new ArrayList<Class<? extends IWorkflowPattern>>();
		patterns.add(UmlSequence.class);
	}
	
	@Override
	public int matches() throws BpmnException {
		return 0;
	}
}
