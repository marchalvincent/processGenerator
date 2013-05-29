package fr.lip6.move.processGenerator.bpmn2.geneticAlgorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.watchmaker.framework.CandidateFactory;
import org.uncommons.watchmaker.framework.EvolutionEngine;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.uncommons.watchmaker.framework.FitnessEvaluator;
import org.uncommons.watchmaker.framework.GenerationalEvolutionEngine;
import org.uncommons.watchmaker.framework.SelectionStrategy;
import org.uncommons.watchmaker.framework.TerminationCondition;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.watchmaker.framework.termination.TargetFitness;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;


public class Example {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		// la factory des candidats de départ
		CandidateFactory<BpmnProcess> factory = new BpmnCandidateFactory();
		
		// définition des opérations 
		List<EvolutionaryOperator<BpmnProcess>> operations = new ArrayList<EvolutionaryOperator<BpmnProcess>>(2);
		operations.add(new BpmnCrossoverOperation());
		operations.add(new BpmnMutationOperation());
		EvolutionaryOperator<BpmnProcess> pipeline = new EvolutionPipeline<BpmnProcess>(operations);
		
		// la classe calculant le fitness
		FitnessEvaluator<BpmnProcess> fitnessEvaluator = new BpmnFitnessEvaluator();
		
		// la méthode de sélection
		SelectionStrategy<Object> selection = new RouletteWheelSelection();
		
		// le random rapide et juste
		Random random = new MersenneTwisterRNG();
		
		// le moteur d'évolution avec tous les paramètres ci-dessus
		EvolutionEngine<BpmnProcess> engine = new GenerationalEvolutionEngine<>(
				factory, 
				pipeline, 
				fitnessEvaluator, 
				selection, 
				random);
		
		// un petit observeur pour voir ce qu'il se passe
		engine.addEvolutionObserver(new BpmnEvolutionObserver());
		
		// les conditions de terminaisons
		TerminationCondition condition = new TargetFitness(100, true);
		
		// la méthode d'évolution
		engine.evolve(100, 5, condition);
	}
}
