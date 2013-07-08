package fr.lip6.move.processGenerator.bpmn2.ga.cp;

import java.util.List;
import java.util.Random;
import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.FlowNode;
import org.eclipse.bpmn2.SequenceFlow;
import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.bpmn2.constraints.BpmnWorkflowRepresentation;
import fr.lip6.move.processGenerator.constraint.IWorkflowRepresentation;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.ga.AbstractChangePattern;
import fr.lip6.move.processGenerator.ga.GeneticException;

/**
 * Ce change pattern se charge d'insérer un workflow entier au process.
 * 
 * @author Vincent
 * 
 */
public class BpmnWorkflowInsert extends AbstractChangePattern<BpmnProcess> {
	
	@Override
	public BpmnProcess apply(BpmnProcess oldProcess, Random rng, List<StructuralConstraintChecker> structuralConstraints) {
		
		BpmnProcess process = null;
		try {
			process = new BpmnProcess(oldProcess);
		} catch (BpmnException e) {
			// impossible de copier...
			e.printStackTrace();
			return oldProcess;
		}
		
		// si la liste des contraintes n'est pas correcte
		if (structuralConstraints == null || (structuralConstraints.size() == 0))
			return process;
		
		// on tire au hasard un workflow parmis ceux sélectionnés par l'utilisateur
		int poidTotal = 0;
		for (StructuralConstraintChecker constraint : structuralConstraints) {
			if (constraint.hasRepresentation())
				poidTotal += constraint.getWeight();
		}
		int[] tableauRandom = new int[poidTotal];
		int cpt = 0;
		for (int indice = 0; indice < structuralConstraints.size(); indice++) {
			if (structuralConstraints.get(indice).hasRepresentation()) {
				for (int i = 0; i < structuralConstraints.get(indice).getWeight(); i++) {
					tableauRandom[cpt] = indice;
					cpt++;
				}
			}
		}
		
		// si on a aucune représentation de contrainte à appliquer
		if (cpt == 0)
			return process;
		
		// on procède au tirage au sort
		StructuralConstraintChecker checker = structuralConstraints.get(tableauRandom[rng.nextInt(tableauRandom.length)]);
		IWorkflowRepresentation rep = checker.getRepresentation();
		
		// si la représentation n'est pas correcte
		if (rep == null || !(rep instanceof BpmnWorkflowRepresentation)) {
			System.err.println("The bpmn representation of the constraints " + checker.getConstraint().getClass().getSimpleName()
					+ "is not correct.");
			return process;
		}
		
		BpmnWorkflowRepresentation representation = (BpmnWorkflowRepresentation) rep;
		
		// ici, on a notre représentation. Maintenant, on va pouvoir l'insérer au candidat.
		try {
			SequenceFlow arcBefore = BpmnChangePatternHelper.instance.getRandomSequenceFlow(process, rng);
			FlowNode cible = arcBefore.getTargetRef();
			
			// 1. on ajoute chaque flow Element au process (nodes et edges)
			for (FlowElement element : representation.getNodes())
				process.getProcess().getFlowElements().add(element);
			for (FlowElement element : representation.getEdges())
				process.getProcess().getFlowElements().add(element);
			
			// 1bis. On ajoute les liens de gateways
			process.addLinksGateways(representation.getLinks());
			
			// 2. On fait le lien avec l'ancien arc
			arcBefore.setTargetRef(representation.getBegin());
			
			// 3. On construit le dernier arc qui relie la représentation à la cible
			process.buildSequenceFlow(representation.getEnd(), cible);
			
		} catch (GeneticException e) {
			System.err.println(getClass().getSimpleName() + " : Impossible to get a random sequence flow from the process.");
			e.printStackTrace();
			return process;
		}
		
		return process;
	}
	
}
