package fr.lip6.move.processGenerator.uml2.ga.cp;

import java.util.List;
import java.util.Random;
import org.eclipse.uml2.uml.Action;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.ga.AbstractChangePattern;
import fr.lip6.move.processGenerator.uml2.UmlException;
import fr.lip6.move.processGenerator.uml2.UmlProcess;

/**
 * Insère une {@link Action} au hasard dans le diagramme.
 * 
 * @author Vincent
 * 
 */
public class UmlSerialInsert extends AbstractChangePattern<UmlProcess> {
	
	@Override
	public UmlProcess apply(UmlProcess oldProcess, Random rng, List<StructuralConstraintChecker> workflowsConstraints) {
		
		// on fait la copie du process
		UmlProcess process = new UmlProcess(oldProcess);
		
		// on récupère un arc au hasard
		ActivityEdge edge = null;
		try {
			edge = UmlChangePatternHelper.instance.getRandomActivityEdge(process, rng);
		} catch (UmlException e) {
			e.printStackTrace();
			return process;
		}
		
		// on créé le nouveau noeud
		ActivityNode node = process.buildRandomExecutableNode();
		process.buildControlFlow(node, edge.getTarget());
		
		// on modifie la destination de notre arc
		edge.setTarget(node);
		
		return process;
	}
}
