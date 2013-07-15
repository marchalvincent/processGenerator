package fr.lip6.move.processGenerator.uml2.constraints.impl;

import fr.lip6.move.processGenerator.constraint.AbstractJavaSolver;
import fr.lip6.move.processGenerator.uml2.EUmlElement;
import fr.lip6.move.processGenerator.uml2.UmlProcess;
import fr.lip6.move.processGenerator.uml2.utils.UmlFilter;

/**
 * Cette contrainte compte le nombre d'élément donné qu'il y a dans un process. Par exemple : le nombre d'OpaqueAction.
 * 
 * @author Vincent
 * 
 */
public class UmlElementConstraint extends AbstractJavaSolver {
	
	private EUmlElement element;
	
	public UmlElementConstraint(EUmlElement data) {
		super();
		element = data;
	}
	
	@Override
	public int matches(Object object) throws Exception {
		if (!(object instanceof UmlProcess)) {
			System.err.println("Matches method : The object is not an " + UmlProcess.class.getSimpleName() + ".");
			return 0;
		}
		UmlProcess process = (UmlProcess) object;
		return UmlFilter.byType(element.getAssociatedClass(), process.getActivity().getNodes()).size();
	}
}
