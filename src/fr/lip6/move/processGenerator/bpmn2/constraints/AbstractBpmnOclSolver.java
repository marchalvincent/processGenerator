package fr.lip6.move.processGenerator.bpmn2.constraints;

import org.eclipse.ocl.ParserException;
import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.constraint.AbstractOclSolver;

/**
 * Cette classe valide une contrainte OCL à partir d'un {@link BpmnProcess} représentant le process à vérifier.
 * 
 * @author Vincent
 * 
 */
public abstract class AbstractBpmnOclSolver extends AbstractOclSolver {
	
	public AbstractBpmnOclSolver() {
		super();
	}
	
	@Override
	public int matches(Object object) throws BpmnException, ParserException {
		
		// on fait juste deux vérifications avant de lancer le solveur
		if (getOclQuery().isEmpty())
			return 0;
		if (!(object instanceof BpmnProcess))
			throw new BpmnException("Matches method : The object is not a " + BpmnProcess.class.getSimpleName() + ".");
		
		BpmnProcess process = (BpmnProcess) object;
		return super.resolveQuery(process.getProcess().eClass(), process.getProcess());
	}
}
