package fr.lip6.move.processGenerator.ga;

import fr.lip6.move.processGenerator.bpmn2.constraints.BpmnStructuralConstraintFactory;
import fr.lip6.move.processGenerator.constraint.AbstractStructuralConstraintFactory;
import fr.lip6.move.processGenerator.uml2.constraints.UmlStructuralConstraintFactory;

/**
 * Cette classe utilitaire permet d'éviter de faire trop de comparaison sur un string (le type de fichier qu'on manipule).
 * Ici la comparaison est faites qu'une seule fois dans le constructeur.
 * @author Vincent
 *
 */
public class DecisionMaker {

	private boolean isBpmn = false;
	private boolean isUml = false;

	public DecisionMaker(String typeFile) {
		super();
		if(typeFile.toLowerCase().contains("bpmn"))
			isBpmn = true;
		else
			isUml = true;
	}

	public AbstractStructuralConstraintFactory getStructuralConstraintFactory () {
		if (isBpmn)
			return BpmnStructuralConstraintFactory.instance;
		return UmlStructuralConstraintFactory.instance;
	}

	public String getTypeFile () {
		if (isBpmn)
			return "bpmn";
		return "uml";
	}

	public boolean isBpmn () {
		return isBpmn;
	}
	
	public boolean isUml() {
		return isUml;
	}
}
