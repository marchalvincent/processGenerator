package fr.lip6.move.processGenerator.geneticAlgorithm.bpmn;

import java.util.List;
import java.util.Random;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.geneticAlgorithm.IChangePattern;
import fr.lip6.move.processGenerator.structuralConstraint.StructuralConstraintChecker;


public interface IBpmnChangePattern extends IChangePattern {

	/**
	 * Applique une mutation à un candidat donné.
	 * @param process {@link BpmnProcess}, le candidat.
	 * @param rng un {@link Random} pour les opérations aléatoires.
	 * @param workflowsConstraints la liste des {@link StructuralConstraintChecker} sélectionnés par l'utilisateur.
	 * @return une copie du candidat auquel on a appliqué la mutation.
	 */
	BpmnProcess apply(BpmnProcess bpmnProcess, Random rng,
			List<StructuralConstraintChecker> workflowsConstraints);
}
