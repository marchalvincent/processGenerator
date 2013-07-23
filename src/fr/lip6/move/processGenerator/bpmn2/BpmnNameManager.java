package fr.lip6.move.processGenerator.bpmn2;

import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.Gateway;

/**
 * Cette classe s'assure que chaque identifiant attribué aux éléments d'un process BPMN sera différent.
 * 
 * @author Vincent
 * 
 */
public class BpmnNameManager {
	
	public static BpmnNameManager instance = new BpmnNameManager();
	
	private BpmnNameManager() {}
	
	private int process = 0;
	private int count = 0;
	
	public String getProcessName() {
		process++;
		return "process_" + process;
	}
	
	public String getFlowElementName(FlowElement elem) {
		count++;
		return elem.getClass().getSimpleName().replace("Impl", "") + "_" + count;
	}
	
	public String getGatewayName(Gateway gate, String direction) {
		count++;
		return gate.getClass().getSimpleName().replace("Impl", "") + direction + "_" + count;
	}
}
