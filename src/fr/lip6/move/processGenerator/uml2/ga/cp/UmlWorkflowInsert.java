package fr.lip6.move.processGenerator.uml2.ga.cp;

import java.util.List;
import java.util.Random;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;
import fr.lip6.move.processGenerator.constraint.IWorkflowRepresentation;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.ga.AbstractChangePattern;
import fr.lip6.move.processGenerator.uml2.UmlException;
import fr.lip6.move.processGenerator.uml2.UmlProcess;
import fr.lip6.move.processGenerator.uml2.constraints.UmlWorkflowRepresentation;

/**
 * Permet l'insertion d'un workflow pattern entier au process.
 * 
 * @author Vincent
 *
 */
public class UmlWorkflowInsert extends AbstractChangePattern<UmlProcess> {

	@Override
	public UmlProcess apply(UmlProcess oldProcess, Random rng, List<StructuralConstraintChecker> structuralConstraints) {
		
		UmlProcess process = new UmlProcess(oldProcess);
		
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
		if (rep == null || !(rep instanceof UmlWorkflowRepresentation)) {
			System.err.println("The uml representation of the constraints " + checker.getConstraint().getClass().getSimpleName()
					+ "is not correct.");
			return process;
		}
		// si le début ou la fin n'est pas bien définie
		if (rep.getBegin() == null || rep.getEnd() == null) {
			System.err.println("The beginning or the end of the workflows representation is not defined : " + 
					checker.getClass().getSimpleName());
		}
		
		UmlWorkflowRepresentation representation = (UmlWorkflowRepresentation) rep;
		
		// ici, on a notre représentation. Maintenant, on va pouvoir l'insérer au candidat.
		ActivityEdge arcBefore;
		try {
			arcBefore = UmlChangePatternHelper.instance.getRandomActivityEdge(process, rng);
		} catch (UmlException e) {
			// pas de sequenceFlow = erreur
			e.printStackTrace();
			return process;
		}
		
		ActivityNode cible = arcBefore.getTarget();
		if (cible == null)
			return process;
		
		// 1. on ajoute chaque ActivityNode et ActivityEdge au process
		for (ActivityNode node : representation.getNodes()) {
			process.getActivity().getNodes().add(node);
			node.setActivity(process.getActivity());
		}
		for (ActivityEdge edge : representation.getEdges()) {
			process.getActivity().getEdges().add(edge);
			edge.setActivity(process.getActivity());
		}
		
		// 1bis. On ajoute les liens de gateways
		process.addLinksControlNodes(representation.getLinks());
		
		// 2. On fait le lien avec l'ancien arc
		arcBefore.setTarget(representation.getBegin());
		
		// 3. On construit le dernier arc qui relie la représentation à la cible
		process.buildControlFlow(representation.getEnd(), cible);

		return process;
	}
}
