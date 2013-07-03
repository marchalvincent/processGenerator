package fr.lip6.move.processGenerator.constraint;

import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.bpmn2.ga.cp.BpmnWorkflowInsert;
import fr.lip6.move.processGenerator.uml2.UmlProcess;

/**
 * Représente une contrainte structurelle sur un modèle.
 * 
 * @author Vincent
 * 
 */
public interface IStructuralConstraint {
	
	/**
	 * Renvoie le nombre d'occurences de la contrainte structurelle sur un modèle.
	 * 
	 * @param process
	 *            {@link Object}. Le modèle a vérifier. Par exemple, un {@link BpmnProcess} ou un {@link UmlProcess}.
	 * @return int le nombre d'occurences de la contrainte sur le modèle.
	 * @throws Exception
	 *             en cas d'erreur. Ex : l'expression OCL est incorrecte.
	 */
	int matches (Object process) throws Exception;
	
	/**
	 * Vous pouvez implémenter cette méthode de façon à ce qu'elle renvoie la représentation (bpmn2 ou uml2.0 ad par
	 * exemple) de la contrainte structurelle. Cette représentation est utile pour le change pattern qui insert
	 * directement les workflow. Cf. {@link BpmnWorkflowInsert} par exemple.
	 * 
	 * @return {@link IWorkflowRepresentation} si la représentation existe, null sinon.
	 */
	IWorkflowRepresentation getRepresentation ();
}
