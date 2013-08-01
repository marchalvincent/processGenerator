package fr.lip6.move.processGenerator.constraint;

import org.eclipse.ocl.ParserException;
import fr.lip6.move.processGenerator.EQuantity;

/**
 * Représente un checker de contrainte structurelle. En fonction du nombre d'occurence trouvée par sa
 * {@link IStructuralConstraint}, ce checker va dire s'il respecte la quantité et le nombre définis par l'utilisateur.
 * 
 * @author Vincent
 * 
 */
public class StructuralConstraintChecker {
	
	private IStructuralConstraint constraint;
	private EQuantity quantity;
	private int number;
	private int weight;
	private boolean manualOcl = false;
	
	/**
	 * Construit la {@link StructuralConstraintChecker} pour une requête OCL écrite manuellement.
	 * @param constraint
	 */
	public StructuralConstraintChecker(IStructuralConstraint constraint) {
		this(constraint, EQuantity.MORE_OR_EQUAL, 1);
		manualOcl = true;
	}
	
	/**
	 * Construit la {@link StructuralConstraintChecker} avec la valeur par défaut suivante : <li>poids : 1</li>
	 * 
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
	
	/**
	 * Execute la contrainte qu'on lui a associé et vérifie selon la quantité et le nombre si la contrainte est
	 * vérifiée.
	 * 
	 * @param process
	 *            Object, la représentation du process à évaluer.
	 * @return boolean vrai si le process vérifie la contrainte selon la quantité et le nombre. Faux sinon.
	 * @throws Exception
	 */
	public boolean check(Object process) throws Exception {
		if (manualOcl) {
			try {
				// comme on est dans une requête manuelle, on doit avoir l'exception
				constraint.matches(process);
			} catch (OclBooleanValue e) {
				return e.getValue();
			}
			throw new Exception("The manual Ocl request does not return a boolean value.");
		}
		
		if (number < 0)
			throw new Exception("The number of the pattern must be higher or equal than 0.");
		
		int result = 0;
		try {
			result = constraint.matches(process);
		} catch (ParserException e) {
			return false;
		} catch (OclBooleanValue e) {
			throw new Exception("The Ocl request return a boolean value.");
		}
		
		switch (quantity) {
			case MORE:
				return result > number;
			case LESS:
				return result < number;
			case EQUAL:
				return result == number;
			case MORE_OR_EQUAL:
				return result >= number;
			case LESS_OR_EQUAL:
				return result <= number;
			default:
				return false;
		}
	}
	
	public IStructuralConstraint getConstraint() {
		return constraint;
	}
	
	public int getWeight() {
		return weight;
	}
	
	public boolean hasRepresentation() {
		return this.constraint.getRepresentation() != null;
	}
	
	public IWorkflowRepresentation getRepresentation() {
		return constraint.getRepresentation();
	}
}
