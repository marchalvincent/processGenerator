package fr.lip6.move.processGenerator.constraint;

/**
 * Représente le comportement que doit avoir une énumération de workflow pattern. Chaque énumération se voit attribué
 * une classe représentant la contrainte du workflow pattern.
 * 
 * @author Vincent
 * 
 */
public interface IEnumWorkflowPattern {
	
	/**
	 * Chaque énumération doit être capable d'instancier le workflow pattern auquelle elle est associée.
	 * 
	 * @return {@link IStructuralConstraint} le workflow pattern.
	 * @throws Exception
	 *             lorsque l'instanciation à échouée.
	 */
	public IStructuralConstraint newInstance() throws Exception;
}
