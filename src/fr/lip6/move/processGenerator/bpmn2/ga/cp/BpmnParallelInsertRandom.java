package fr.lip6.move.processGenerator.bpmn2.ga.cp;

import java.util.List;
import java.util.Random;
import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.ga.AbstractChangePattern;

/**
 * Ce change pattern est chargé d'appliquer une insertion d'activité en parallèle. L'insertion peut se faire sur une
 * activité et donc entrainer la création de parallel gateway, ou tout simplement s'appliquer sur des parallel gateways
 * déjà existantes et ajouter une branche supplémentaire.
 * 
 * @see BpmnParallelInsertActivity insertion d'une Task en parallèle avec une autre.
 * @see BpmnParallelInsertGateway insertion d'une Task sur des gateways déjà existantes.
 * 
 * @author Vincent
 * 
 */
public class BpmnParallelInsertRandom extends AbstractChangePattern<BpmnProcess> {
	
	@Override
	public BpmnProcess apply(BpmnProcess oldProcess, Random rng, List<StructuralConstraintChecker> structuralConstraints) {
		
		// on récupère toutes les Activities et le nombre de ParallelGateway
		int nbActivity = BpmnChangePatternHelper.instance.countActivity(oldProcess);
		int nbParallel = BpmnChangePatternHelper.instance.countLinkedParallelGateway(oldProcess);
		if (nbParallel % 2 != 0) {
			System.err.println("Error, the number of ParallelGateway is odd.");
			try {
				return new BpmnProcess(oldProcess);
			} catch (BpmnException e) {
				return oldProcess;
			}
		}
		// on divise par deux le nombre de parallelGateway car il y a une ouvrante et une fermante.
		nbParallel = nbParallel / 2;
		
		if ((nbActivity + nbParallel) == 0) {
			try {
				return new BpmnProcess(oldProcess);
			} catch (BpmnException e) {
				return oldProcess;
			}
		}
		
		// on fait un random équitable pour savoir si on applique le parallel sur une Activity ou sur une
		// parallelGateway deja existante
		int[] tableau = new int[nbActivity + nbParallel];
		for (int i = 0; i < nbActivity; i++) {
			tableau[i] = 0;
		}
		for (int i = nbActivity; i < nbActivity + nbParallel; i++) {
			tableau[i] = 1;
		}
		
		// on procède au tirage au sort. 0 pour une insertion sur une ativity, 1 pour une insertion sur une parallel.
		int tirage = tableau[rng.nextInt(tableau.length)];
		if (tirage == 0)
			return BpmnParallelInsertActivity.instance.apply(oldProcess, rng, structuralConstraints);
		else
			return BpmnParallelInsertGateway.instance.apply(oldProcess, rng, structuralConstraints);
	}
}
