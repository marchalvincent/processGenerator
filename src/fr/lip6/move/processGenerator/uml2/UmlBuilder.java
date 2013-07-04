package fr.lip6.move.processGenerator.uml2;

import org.eclipse.uml2.uml.ActivityFinalNode;
import org.eclipse.uml2.uml.InitialNode;

/**
 * Cette classe permet de construire rapidement diverses {@link UmlProcess}.
 * 
 * @author Vincent
 * 
 */
public class UmlBuilder {
	
	public static UmlBuilder instance = new UmlBuilder();
	private UmlBuilder() {}
	
	/**
	 * Construit un process simple : InitialNode -> ActivityFinalNode.
	 * @return
	 */
	public UmlProcess initialFinal () {
		UmlProcess process = new UmlProcess();
		InitialNode init = process.buildInitialNode();
		ActivityFinalNode finalNode = process.buildActivityFinalNode();
		process.buildControlFlow(init, finalNode);
		return process;
	}
}
