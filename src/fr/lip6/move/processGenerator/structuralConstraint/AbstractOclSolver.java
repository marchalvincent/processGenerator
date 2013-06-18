package fr.lip6.move.processGenerator.structuralConstraint;

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
import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;

/**
 * Cette classe valide une contrainte OCL à partir d'un {@link EObjet} représentant le process à vérifier.
 * @author Vincent
 *
 */
public abstract class AbstractOclSolver implements IStructuralConstraint {

	private String oclQuery;
	
	public AbstractOclSolver() {
		super();
		oclQuery = new String();
	}
	
	protected void setOclQuery(String query) {
		this.oclQuery = query;
	}

	@Override
	public int matches(Object object) throws BpmnException {

		if (oclQuery.isEmpty()) 
			return 0;
		
		if (object instanceof BpmnProcess) {
			throw new BpmnException("Matches method : The object is not a Bpmn Process.");
		}
		
		BpmnProcess process = (BpmnProcess) object;
		
		// create an OCL instance for Ecore
		OCL<?, EClassifier, ?, ?, ?, ?, ?, ?, ?, Constraint, EClass, EObject> ocl;
		ocl = OCL.newInstance(EcoreEnvironmentFactory.INSTANCE);

		// create an OCL helper object
		OCLHelper<EClassifier, ?, ?, Constraint> helper = ocl.createOCLHelper();

		// set the OCL context classifier
		helper.setContext(process.getProcess().eClass());

		// create the ocl expression
		OCLExpression<EClassifier> oclExpession = null;
		try {
			oclExpession = helper.createQuery(oclQuery);
		} catch (ParserException e) {
			throw new BpmnException("ParserException : " + e.getMessage());
		}
		
		// create the query
		Query<EClassifier, EClass, EObject> query = ocl.createQuery(oclExpession);
		
		// evaluate
		Object o = query.evaluate(process.getProcess());
		if (o instanceof Integer) {
			return ((Integer)o).intValue();
		}
		else {
			System.err.println("Warning, the query result is not an Integer -> " + o.getClass().getSimpleName());
		}
		return 0;
	}

	@Override
	public String toString() {
		return "AbstractOclSolver [oclQuery=" + oclQuery + "]";
	}
}
