package fr.lip6.move.processGenerator.constraint;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.ocl.OCL;
import org.eclipse.ocl.ParserException;
import org.eclipse.ocl.Query;
import org.eclipse.ocl.ecore.Constraint;
import org.eclipse.ocl.ecore.EcoreEnvironmentFactory;
import org.eclipse.ocl.expressions.OCLExpression;
import org.eclipse.ocl.helper.OCLHelper;

/**
 * Cette classe offre un niveau d'abstraction aux solveurs de contraintes OCL.
 * 
 * @author Vincent
 * 
 */
public abstract class AbstractOclSolver implements IStructuralConstraint {
	
	private String oclQuery;
	
	public AbstractOclSolver() {
		super();
		oclQuery = new String();
	}
	
	public void setOclQuery(String query) {
		this.oclQuery = query;
	}
	
	public String getOclQuery() {
		return oclQuery;
	}
	
	@Override
	public abstract int matches(Object object) throws Exception;
	
	@Override
	public IWorkflowRepresentation getRepresentation() {
		// par défaut les contraintes n'ont pas de représentation
		return null;
	}
	
	/**
	 * Cette méthode est générique et permet de résoudre les contraintes OCL sur un objet donné. Attention, les
	 * paramètres passés à cette méthode doivent être vérifiés avant l'appel de cette méthode.
	 * 
	 * @param eClass
	 *            la {@link EClass} de l'objet à évaluer.
	 * @param object
	 *            l'objet à évaluer.
	 * @return le nombre de structure trouvée par la contrainte ocl.
	 */
	protected int resolveQuery(EClass eClass, Object object) {
		// create an OCL instance for Ecore
		OCL<?, EClassifier, ?, ?, ?, ?, ?, ?, ?, Constraint, EClass, EObject> ocl;
		ocl = OCL.newInstance(EcoreEnvironmentFactory.INSTANCE);
		
		// create an OCL helper object
		OCLHelper<EClassifier, ?, ?, Constraint> helper = ocl.createOCLHelper();
		
		// set the OCL context classifier
		helper.setContext(eClass);
		
		// create the ocl expression
		OCLExpression<EClassifier> oclExpession = null;
		try {
			oclExpession = helper.createQuery(oclQuery);
		} catch (ParserException e) {
			System.err.println("Error with the following query : " + oclQuery);
			e.printStackTrace();
			return 0;
		}
		
		// create the query
		Query<EClassifier, EClass, EObject> query = ocl.createQuery(oclExpession);
		
		// evaluate
		Object o = query.evaluate(object);
		if (o instanceof Integer) {
			return ((Integer) o).intValue();
		} else {
			System.err.println("Warning, the query result is not an Integer -> " + o.getClass().getSimpleName());
		}
		return 0;
	}
}
