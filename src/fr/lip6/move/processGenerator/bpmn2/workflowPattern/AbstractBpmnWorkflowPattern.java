package fr.lip6.move.processGenerator.bpmn2.workflowPattern;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.bpmn2.Process;
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
import fr.lip6.move.processGenerator.WorkflowPattern;
import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;


public abstract class AbstractBpmnWorkflowPattern implements WorkflowPattern {

	public static List<Class<? extends WorkflowPattern>> patterns;
	static {
		patterns = new ArrayList<Class<? extends WorkflowPattern>>();
		patterns.add(BpmnSequence.class);
		patterns.add(BpmnParallelSplit.class);
		patterns.add(BpmnSynchronization.class);
		patterns.add(BpmnExclusiveChoice.class);
		patterns.add(BpmnSimpleMerge.class);
	}

	private Process process;
	private String oclQuery;

	public AbstractBpmnWorkflowPattern(BpmnProcess process) {
		super();
		this.process = process.getProcess();
		this.oclQuery = new String();
	}

	protected Process getProcess() {
		return process;
	}

	protected void setOclQuery(String query) {
		this.oclQuery = query;
	}

	@Override
	public int matches() throws BpmnException {

		// create an OCL instance for Ecore
		OCL<?, EClassifier, ?, ?, ?, ?, ?, ?, ?, Constraint, EClass, EObject> ocl;
		ocl = OCL.newInstance(EcoreEnvironmentFactory.INSTANCE);

		// create an OCL helper object
		OCLHelper<EClassifier, ?, ?, Constraint> helper = ocl.createOCLHelper();

		// set the OCL context classifier
		helper.setContext(process.eClass());

		// create the ocl expression
		OCLExpression<EClassifier> oclExpession = null;
		try {
			oclExpession = helper.createQuery(oclQuery);
		} catch (ParserException e) {
			throw new BpmnException(e.getMessage());
		}
		
		// create the query
		Query<EClassifier, EClass, EObject> query = ocl.createQuery(oclExpession);
		
		// evaluate
		Object o = query.evaluate(process);
		if (o instanceof Integer) {
			return ((Integer)o).intValue();
		}
		else {
			System.err.println("Attention l'objet n'est pas un Integer -> " + o.getClass().getSimpleName());
		}
		return 0;
	}
}
