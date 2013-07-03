package fr.lip6.move.processGenerator.bpmn2.ga;

import java.util.List;
import java.util.Random;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.ga.IChangePattern;

/**
 * Cette interface définit le comportement des change pattern implémentés pour fonctionner sur les fichiers bpmn.
 * 
 * @author Vincent
 * 
 */
public interface IBpmnChangePattern extends IChangePattern {
	
	/**
	 * Applique une mutation à un candidat donné.
	 * 
	 * @param process
	 *            {@link BpmnProcess}, le candidat.
	 * @param rng
	 *            un {@link Random} pour les opérations aléatoires.
	 * @param workflowsConstraints
	 *            la liste des {@link StructuralConstraintChecker} de workflow sélectionnés par l'utilisateur.
	 * @return une copie du candidat auquel on a appliqué la mutation.
	 */
	BpmnProcess apply (BpmnProcess bpmnProcess, Random rng, List<StructuralConstraintChecker> workflowsConstraints);
}
