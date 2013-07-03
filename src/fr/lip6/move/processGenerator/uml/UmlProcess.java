package fr.lip6.move.processGenerator.uml;

import org.eclipse.uml2.uml.Activity;

/**
 * Représente un process UML.
 * @author Vincent
 *
 */
public class UmlProcess {
	
	// implémenter le diagramme d'activités de UML2.0
	@SuppressWarnings("unused")
	private Activity activity;
	
	public UmlProcess() {
		super();
		// faire un process initial -> final
	}
	
	public UmlProcess(Activity activity) {
		super();
		this.activity = activity;
	}
}
