package fr.lip6.move.processGenerator.uml2.ga.cp;

import java.util.List;
import java.util.Random;
import org.eclipse.uml2.uml.Action;
import org.eclipse.uml2.uml.ActivityEdge;
import fr.lip6.move.processGenerator.Utils;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.ga.AbstractChangePattern;
import fr.lip6.move.processGenerator.uml2.UmlException;
import fr.lip6.move.processGenerator.uml2.UmlProcess;

/**
 * Ce change pattern se charge de supprimer une activité au hasard puis de simplifier le process lorsqu'il contient des données
 * incohérentes.
 * 
 * @author Vincent
 *
 */
public class UmlRemove extends AbstractChangePattern<UmlProcess> {
	
	@Override
	public UmlProcess apply(UmlProcess oldProcess, Random rng, List<StructuralConstraintChecker> workflowsConstraints) {
		
		// copie du process
		UmlProcess process = new UmlProcess(oldProcess);
		
		// on tire une action au hasard
		Action action;
		try {
			action = UmlChangePatternHelper.instance.getRandomAction(process, rng);
		} catch (UmlException e) {
			// si il n'y a pas d'Action ce n'est pas une erreur donc pas de print
			return process;
		}
		
		// on récupère les arc arrivant et partant de cette activity
		List<ActivityEdge> edgesIn = action.getIncomings();
		List<ActivityEdge> edgesOut = action.getOutgoings();
		
		if (edgesIn == null || edgesOut == null)
			return process;
		
		if (edgesIn.size() != 1) {
			System.err.println(getClass().getSimpleName() + " : The number of incoming edges is not correct : "
					+ edgesIn.size() + ". " + action.getClass());
			process.save(System.getProperty("user.home") + Utils.bugPathUml);
		}
		if (edgesOut.size() > 1)
			System.err.println(getClass().getSimpleName() + " : The number of outgoing edges is not correct : "
					+ edgesOut.size() + ". " + action.getClass());
		
		ActivityEdge arcIn = edgesIn.get(0);
		ActivityEdge arcOut = edgesOut.get(0);
		
		// on modifie la destination de l'arc entrant
		arcIn.setTarget(arcOut.getTarget());
		
		// puis on supprime l'action et l'arc sortant
		 process.removeActivityEdge(arcOut);
		 process.removeActivityNode(action);
		 
		 // enfin on fini pas nettoyer le process dans le cas où des données incohérentes sera apparues
		 UmlChangePatternHelper.instance.cleanProcess(process);
		
		return process;
	}
}
