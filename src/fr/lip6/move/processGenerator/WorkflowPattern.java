package fr.lip6.move.processGenerator;

import fr.lip6.move.processGenerator.bpmn2.BpmnException;

/**
 * Représente un workflow pattern définit par le professeur Wil van der Aalst.
 * @author Vincent
 *
 */
public interface WorkflowPattern {
	
	/**
	 * Un {@link WorkflowPattern} doit être capable de renvoyer le nombre
	 * d'occurences de son pattern sur un modèle.
	 * @return int le nombre d'occurences.
	 * @throws BpmnException en cas d'erreur. Ex : l'expression OCL est incorrecte.
	 */
	int matches() throws BpmnException;
}
