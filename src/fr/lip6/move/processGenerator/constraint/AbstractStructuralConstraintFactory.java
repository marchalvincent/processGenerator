package fr.lip6.move.processGenerator.constraint;

import fr.lip6.move.processGenerator.IEnumElement;

/**
 * Représente une factory abstraite de {@link IStructuralConstraint}.
 * 
 * @author Vincent
 * 
 */
public abstract class AbstractStructuralConstraintFactory {
	
	/**
	 * La création de workflow pattern est la même pour chaque type de fichier, c'est pourquoi le code est dans cette
	 * classe abstraite.
	 * 
	 * @param eBpmnWorkflowPattern
	 *            cet objet doit être une {@link IEnumWorkflowPattern}. Ainsi il pourra instancier le workflow.
	 * @return {@link IStructuralConstraint} la contrainte représentant le workflow pattern.
	 * @throws Exception
	 */
	public IStructuralConstraint newWorkflowPatternConstraint(Object eBpmnWorkflowPattern) throws Exception {
		if (eBpmnWorkflowPattern instanceof IEnumWorkflowPattern) {
			return ((IEnumWorkflowPattern) eBpmnWorkflowPattern).newInstance();
		}
		throw new Exception("The parameter of the new WorkflowPatternConstraint method is not a "
				+ IEnumWorkflowPattern.class.getSimpleName() + ".");
	}
	
	/**
	 * Créé une nouvelle contrainte OCL définie à la main par l'utilisateur.
	 * 
	 * @param query
	 *            String, la requete écrite à la main.
	 * @return {@link IStructuralConstraint}.
	 */
	public abstract IStructuralConstraint newManualOclConstraint(String query);
	
	/**
	 * Créé une nouvelle contrainte structurelle représentant un nombre d'élément à avoir.
	 * 
	 * @param eElement
	 *            Cet objet doit être une {@link IEnumElement}.
	 * @return {@link IStructuralConstraint}.
	 * @throws Exception
	 *             si l'élément n'est pas du même type que le fichier qu'on évalue.
	 */
	public abstract IStructuralConstraint newElementConstraint(IEnumElement eElement) throws Exception;
}
