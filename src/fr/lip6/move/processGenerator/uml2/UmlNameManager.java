package fr.lip6.move.processGenerator.uml2;

import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;


public class UmlNameManager {

	public static UmlNameManager instance = new UmlNameManager();
	private UmlNameManager() {}
	
	private int processCount = 0;
	private int count = 0;
	
	public String getProcessName() {
		processCount ++;
		return "process_" + processCount;
	}
	
	public String getActivityNodeName(ActivityNode node) {
		count ++;
		return node.getClass().getSimpleName() + "_" + count;
	}

	public String getActivityEdgeName (ActivityEdge edge) {
		count ++;
		return edge.getClass().getSimpleName() + "_" + count;
	}
}
