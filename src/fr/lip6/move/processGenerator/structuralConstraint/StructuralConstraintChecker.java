package fr.lip6.move.processGenerator.structuralConstraint;

import fr.lip6.move.processGenerator.EQuantity;
import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;

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
	private int weight;
	
	/**
	 * Construit la {@link StructuralConstraintChecker} avec les valeurs par défaut suivantes :
	 * <li>quantité : "MORE_OR_EQUAL"</li>
	 * <li>nombre : 1</li>
	 * <li>poids : 1</li>
	 * @param constraint
	 */
	public StructuralConstraintChecker(IStructuralConstraint constraint) {
		this(constraint, EQuantity.MORE_OR_EQUAL, 1);
	}
	
	/**
	 * Construit la {@link StructuralConstraintChecker} avec la valeur par défaut suivante :
	 * <li>poids : 1</li>
	 * @param constraint
	 */
	public StructuralConstraintChecker(IStructuralConstraint constraint, EQuantity quantity, int number) {
		this(constraint, quantity, number, 1);
	}
	
	public StructuralConstraintChecker(IStructuralConstraint constraint, EQuantity quantity, int number, int weight) {
		super();
		this.constraint = constraint;
		this.quantity = quantity;
		this.number = number;
		this.weight = weight;
	}
	
	public boolean check(BpmnProcess process) throws Exception {
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
	
	public int getWeight() {
		return weight;
	}
	
	public boolean hasRepresentation() {
		return this.constraint.getRepresentation() != null;
	}
	
	public IConstraintRepresentation getRepresentation() {
		return constraint.getRepresentation();
	}
	
	@Override
	public String toString() {
		return "StructuralConstraintChecker [constraint=" + constraint + ", quantity=" + quantity
				+ ", number=" + number + "]";
	}
}
