package fr.lip6.move.processGenerator.structuralConstraint;

import org.eclipse.emf.ecore.EObject;

/**
 * Représente une contrainte structurelle sur un modèle. 
 * @author Vincent
 *
 */
public interface IStructuralConstraint {

	/**
	 * Renvoie le nombre d'occurences de la contrainte structurelle sur un modèle.
	 * @param process {@link EObject}. Le modèle a vérifier.
	 * @return int le nombre d'occurences de la contrainte sur le modèle.
	 * @throws Exception en cas d'erreur. Ex : l'expression OCL est incorrecte.
	 */
	int matches(EObject process) throws Exception;
}
