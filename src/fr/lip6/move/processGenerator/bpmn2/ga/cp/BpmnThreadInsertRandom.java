package fr.lip6.move.processGenerator.bpmn2.ga.cp;

import java.util.List;
import java.util.Random;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.ga.AbstractChangePattern;

/**
 * Ce change pattern applique l'insertion d'un nouveau thread au process. Ce nouveau thread s'aboutira sur un nouveau
 * noeud EndEvent soit explicitement (met fin au process) soit implicitement (continue l'éxecution des autres threads).
 * 
 * @author Vincent
 * 
 * @see {@link BpmnThreadInsertImplicite} ajout d'un nouveau thread implicite.
 * @see {@link BpmnThreadInsertExplicite} ajout d'un nouveau thread explicite.
 */
public class BpmnThreadInsertRandom extends AbstractChangePattern<BpmnProcess> {
	
	@Override
	public BpmnProcess apply(BpmnProcess oldProcess, Random rng, List<StructuralConstraintChecker> workflowsConstraints) {
		// une fois sur deux on faire appel au CP implicite / explicite.
		if (rng.nextBoolean())
			return BpmnThreadInsertImplicite.instance.apply(oldProcess, rng, workflowsConstraints);
		return BpmnThreadInsertExplicite.instance.apply(oldProcess, rng, workflowsConstraints);
	}
}
