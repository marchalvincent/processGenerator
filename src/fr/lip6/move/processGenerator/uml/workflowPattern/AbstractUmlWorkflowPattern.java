package fr.lip6.move.processGenerator.uml.workflowPattern;

import java.util.ArrayList;
import java.util.List;
import fr.lip6.move.processGenerator.WorkflowPattern;
import fr.lip6.move.processGenerator.bpmn2.BpmnException;


public class AbstractUmlWorkflowPattern implements WorkflowPattern {
	
	public static List<Class<? extends WorkflowPattern>> patterns;
	static {
		patterns = new ArrayList<Class<? extends WorkflowPattern>>();
		patterns.add(UmlSequence.class);
	}
	
	@Override
	public int matches() throws BpmnException {
		return 0;
	}
}
