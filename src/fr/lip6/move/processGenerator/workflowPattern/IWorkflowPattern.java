package fr.lip6.move.processGenerator.workflowPattern;

import fr.lip6.move.processGenerator.file.bpmn2.BpmnException;

/**
 * Représente un workflow pattern définit par le professeur Wil van der Aalst.
 * @author Vincent
 *
 */
public interface IWorkflowPattern {
	
	/**
	 * Un {@link IWorkflowPattern} doit être capable de renvoyer le nombre
	 * d'occurence de son pattern sur un modèle.
	 * @return
	 * @throws BpmnException en cas d'erreur. Ex : l'expression OCL est incorrecte.
	 */
	int matches() throws BpmnException;
}
