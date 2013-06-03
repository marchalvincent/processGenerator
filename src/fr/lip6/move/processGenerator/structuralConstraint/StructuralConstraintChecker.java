package fr.lip6.move.processGenerator.structuralConstraint;

import org.eclipse.emf.ecore.EObject;
import fr.lip6.move.processGenerator.EQuantity;
import fr.lip6.move.processGenerator.bpmn2.BpmnException;

/**
 * Représente un checker de contrainte structurelle. En fonction du nombre d'occurence
 * trouvée par sa {@link IStructuralConstraint}, ce checker va dire s'il respecte la quantité
 * et le nombre définis par l'utilisateur.
 * @author Vincent
 *
 */
public class StructuralConstraintChecker {

	private IStructuralConstraint constraint;
	private EQuantity quantity;
	private int number;
	
	public StructuralConstraintChecker(IStructuralConstraint constraint, EQuantity quantity, int number) {
		super();
		this.constraint = constraint;
		this.quantity = quantity;
		this.number = number;
	}
	
	public boolean check(EObject process) throws Exception {
		if (number < 0)
			throw new BpmnException("Le nombre du pattern doit être supérieure ou égale à 0.");
		
		Object result = constraint.matches(process);
		if (!(result instanceof Integer))
			return false;
		
		int resultat = ((Integer)result).intValue();
		switch (quantity) {
			case MORE:
				return resultat > number;
			case LESS:
				return resultat < number;
			case EQUAL:
				return resultat == number;
			case MORE_OR_EQUAL:
				return resultat >= number;
			case LESS_OR_EQUAL:
				return resultat <= number;
			default:
				return false;
		}
	}
	
	@Override
	public String toString() {

		return "StructuralConstraintChecker [constraint=" + constraint + ", quantity=" + quantity
				+ ", number=" + number + "]";
	}
}
