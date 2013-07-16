package fr.lip6.move.processGenerator.uml2.ga.cp;

import java.util.List;
import java.util.Random;
import org.eclipse.uml2.uml.Action;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.DecisionNode;
import org.eclipse.uml2.uml.MergeNode;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.ga.AbstractChangePattern;
import fr.lip6.move.processGenerator.uml2.UmlException;
import fr.lip6.move.processGenerator.uml2.UmlProcess;

/**
 * Insère une {@link Action} en conditionnelle sur un arc en créant les noeuds de décision/merge.
 * 
 * @author Vincent
 * 
 * @see UmlConditionalInsertDecisionMerge insertion conditionelle sur un couple Decision/Merge.
 * @see UmlConditionalInsertRandom insertion conditionnelle au hasard dans le process.
 */
public class UmlConditionalInsertEdge extends AbstractChangePattern<UmlProcess> {
	
	// cela évite trop d'instanciation de la part du change pattern UmlConditionalInsertRandom
	public static UmlConditionalInsertEdge instance = new UmlConditionalInsertEdge();

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
		
		// on créé les nouveaux noeuds
		DecisionNode decision = process.buildDecisionNode();
		Action a = process.buildAction();
		MergeNode merge = process.buildMergeNode();
		
		process.linkControlNodes(decision, merge);
		
		// puis les arcs
		process.buildControlFlow(decision, a);
		process.buildControlFlow(a, merge);
		process.buildControlFlow(decision, merge);
		process.buildControlFlow(merge, edge.getTarget());
		
		// et on fini par changer la destination de notre arc
		edge.setTarget(decision);
		
		return process;
	}
}
