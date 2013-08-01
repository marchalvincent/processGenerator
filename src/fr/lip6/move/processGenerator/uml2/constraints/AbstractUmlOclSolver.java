package fr.lip6.move.processGenerator.uml2.constraints;

import org.eclipse.ocl.ParserException;
import fr.lip6.move.processGenerator.constraint.AbstractOclSolver;
import fr.lip6.move.processGenerator.constraint.OclBooleanValue;
import fr.lip6.move.processGenerator.uml2.UmlException;
import fr.lip6.move.processGenerator.uml2.UmlProcess;

/**
 * Cette classe valide une contrainte OCL à partir d'un {@link UmlProcess} représentant le process à vérifier.
 * 
 * @author Vincent
 * 
 */
public class AbstractUmlOclSolver extends AbstractOclSolver {
	
	public AbstractUmlOclSolver() {
		super();
	}
	
	@Override
	public int matches(Object object) throws UmlException, ParserException, OclBooleanValue {
		
		// on fait juste deux vérifications avant de lancer le solveur
		if (getOclQuery().isEmpty())
			return 0;
		if (!(object instanceof UmlProcess))
			throw new UmlException("Matches method : The object is not a " + UmlProcess.class.getSimpleName() + ".");
		
		UmlProcess process = (UmlProcess) object;
		return super.resolveQuery(process.getActivity().eClass(), process.getActivity());
	}
}
