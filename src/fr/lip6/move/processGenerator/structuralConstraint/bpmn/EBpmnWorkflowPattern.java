package fr.lip6.move.processGenerator.structuralConstraint.bpmn;

import java.util.ArrayList;
import java.util.List;
import fr.lip6.move.processGenerator.structuralConstraint.IStructuralConstraint;

/**
 * Cette classe liste l'ensemble des workflow patterns implémentés pour le type de fichier bpmn.
 * @author Vincent
 *
 */
public final class EBpmnWorkflowPattern {
	
	public static List<Class<? extends IStructuralConstraint>> patterns;
	static {
		patterns = new ArrayList<Class<? extends IStructuralConstraint>>();
		patterns.add(BpmnSequence.class);
		patterns.add(BpmnParallelSplit.class);
		patterns.add(BpmnSynchronization.class);
		patterns.add(BpmnExclusiveChoice.class);
		patterns.add(BpmnSimpleMerge.class);
	}
}
