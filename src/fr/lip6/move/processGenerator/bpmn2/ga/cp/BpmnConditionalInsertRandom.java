package fr.lip6.move.processGenerator.bpmn2.ga.cp;

import java.util.List;
import java.util.Random;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.ga.AbstractChangePattern;

/**
 * Ce change pattern représente l'insertion conditionnelle d'une activité. Elle peut s'appliquer sur un SequenceFlow et
 * entrainer la création de gateways de condition, ou tout simplement s'appliquer sur des gateways déjà existantes et
 * ajouter une branche seulement. Le choix est fait aléatoirement.
 * 
 * @see BpmnConditionalInsertSequence insertion conditionnelle sur un SequenceFlow.
 * @see BpmnConditionalInsertGateway insertion conditionnelle sur des gateways déjà existantes.
 * 
 * @author Vincent
 * 
 */
public class BpmnConditionalInsertRandom extends AbstractChangePattern<BpmnProcess> {
	
	@Override
	public BpmnProcess apply(BpmnProcess oldProcess, Random rng, List<StructuralConstraintChecker> structuralConstraints) {
		
		// on récupère le nombre de séquence et le nombre d'exclusive gateway
		int nbSequence = BpmnChangePatternHelper.instance.countSequenceFlow(oldProcess);
		int nbConditional = BpmnChangePatternHelper.instance.countConditionalGateway(oldProcess);
		if (nbConditional % 2 != 0) {
			System.err.println("Error, the number of ExclusiveGateway is odd.");
			return oldProcess;
		}
		
		// on divise par deux le nombre d'ExclusiveGateway car il y a une ouvrante et une fermante.
		nbConditional = nbConditional / 2;
		
		if ((nbSequence + nbConditional) == 0)
			return oldProcess;
		
		// on fait un random équitable pour savoir si on applique la condition sur un arc ou sur une ExclusiveGateway
		// deja existante
		int[] tableau = new int[nbSequence + nbConditional];
		for (int i = 0; i < nbSequence; i++) {
			tableau[i] = 0;
		}
		for (int i = nbSequence; i < nbSequence + nbConditional; i++) {
			tableau[i] = 1;
		}
		
		// on procède au tirage au sort. 0 pour une insertion sur un arc, 1 pour une insertion sur des Gateways déjà
		// existantes.
		int tirage = tableau[rng.nextInt(tableau.length)];
		if (tirage == 0)
			return BpmnConditionalInsertSequence.instance.apply(oldProcess, rng, structuralConstraints);
		else
			return BpmnConditionalInsertGateway.instance.apply(oldProcess, rng, structuralConstraints);
	}
}
