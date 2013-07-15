package fr.lip6.move.processGenerator;

import fr.lip6.move.processGenerator.bpmn2.EBpmnElement;
import fr.lip6.move.processGenerator.uml2.EUmlElement;

/**
 * Cette interface permet de manipuler les différentes énumérations d'éléments. Cf. {@link EBpmnElement},
 * {@link EUmlElement}, etc.
 * 
 * @author Vincent
 * 
 */
public interface IEnumElement extends IHierarchicalEnum {
	
	/**
	 * Renvoie la classe associée à l'énumération.
	 * 
	 * @return
	 */
	Class<?> getAssociatedClass();
}
