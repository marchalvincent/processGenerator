package fr.lip6.move.processGenerator.uml2.ga.cp;

import org.eclipse.uml2.uml.ControlNode;
import fr.lip6.move.processGenerator.uml2.UmlProcess;



public class ControlNodeManager {
	
	public static ControlNodeManager instance = new ControlNodeManager();
	
	private ControlNodeManager() {}
	
	/**
	 * 
	 * @param process
	 * @param controlNode
	 * @return
	 */
	public ControlNode findControlNodeTwin(UmlProcess process, ControlNode controlNode) {
		
		// on tente de récupérer la twin par le process
		ControlNode twin = process.getTwin(controlNode.getName());
		if (twin != null)
			return twin;
		
		// sinon il faut chercher dans le process à la main
		//TODO
		return null;
	}
}
