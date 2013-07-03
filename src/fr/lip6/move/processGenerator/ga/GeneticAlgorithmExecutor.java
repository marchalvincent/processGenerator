package fr.lip6.move.processGenerator.ga;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.CandidateFactory;
import org.uncommons.watchmaker.framework.EvolutionEngine;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.uncommons.watchmaker.framework.FitnessEvaluator;
import org.uncommons.watchmaker.framework.GenerationalEvolutionEngine;
import org.uncommons.watchmaker.framework.SelectionStrategy;
import org.uncommons.watchmaker.framework.TerminationCondition;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.RankSelection;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.watchmaker.framework.selection.StochasticUniversalSampling;
import org.uncommons.watchmaker.framework.selection.TournamentSelection;
import fr.lip6.move.processGenerator.Benchmarker;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.bpmn2.ga.BpmnCandidateFactory;
import fr.lip6.move.processGenerator.bpmn2.ga.BpmnCrossoverOperation;
import fr.lip6.move.processGenerator.bpmn2.ga.BpmnFitnessEvaluator;
import fr.lip6.move.processGenerator.bpmn2.ga.BpmnMutationOperation;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.views.ProcessGeneratorView;

/**
 * Cette classe se charge d'instancier et de lancer l'algorithme génétique.
 * 
 * @author Vincent
 * 
 */
public class GeneticAlgorithmExecutor extends Thread {
	
	private String location;
	private Integer nbNodes;
	private Integer margin;
	
	private List<StructuralConstraintChecker> contraintesElements;
	private List<StructuralConstraintChecker> contraintesWorkflows;
	private StructuralConstraintChecker manualOclChecker;
	
	private Integer nbPopulation;
	private Object initialProcess;
	private Integer elitism;
	private String selectionStrategy;
	
	private Boolean isCheckMutation;
	private List<IChangePattern> changePatterns;
	private Boolean isCheckCrossover;
	
	private List<TerminationCondition> terminationCondition;
	private FitnessWeightHelper weightHelper;
	
	private ProcessGeneratorView view;
	private DecisionMaker decisionMaker;
	
	public GeneticAlgorithmExecutor() {
		super();
	}
	
	@Override
	public String toString () {
		return "GeneticAlgorithmExecutor [location=" + location + ", nbNodes=" + nbNodes + ", margin=" + margin
				+ ", decisionMaker=" + decisionMaker + ", contraintesElements=" + contraintesElements + ", contraintesWorkflows="
				+ contraintesWorkflows + ", manualOclChecker=" + manualOclChecker + ", nbPopulation=" + nbPopulation
				+ ", initialProcess=" + initialProcess + ", elitism=" + elitism + ", selectionStrategy=" + selectionStrategy
				+ ", isCheckMutation=" + isCheckMutation + ", changePatterns=" + changePatterns + ", isCheckCrossover="
				+ isCheckCrossover + ", terminationCondition=" + terminationCondition + "]";
	}
	
	public void setRunConfiguration (String location, int nbNodes, int margin) {
		this.location = location;
		this.nbNodes = nbNodes;
		this.margin = margin;
	}
	
	public void setDecisionMaker (DecisionMaker decisionMaker) {
		this.decisionMaker = decisionMaker;
	}
	
	public void setStructuralsConstraintsChecker (List<StructuralConstraintChecker> contraintesElements,
			List<StructuralConstraintChecker> contraintesWorkflows, StructuralConstraintChecker manualOclChecker) {
		this.contraintesElements = contraintesElements;
		this.contraintesWorkflows = contraintesWorkflows;
		this.manualOclChecker = manualOclChecker;
	}
	
	public void setNbPopulation (int nbPopulation) {
		this.nbPopulation = nbPopulation;
	}
	
	public void setInitialProcess (Object initialProcess) throws GeneticException {
		this.initialProcess = decisionMaker.setInitialProcess(initialProcess);
	}
	
	public void setGeneticSelection (int elitism, String selectionStrategy) {
		this.elitism = elitism;
		this.selectionStrategy = selectionStrategy;
	}
	
	public void setGeneticOperations (boolean isCheckMutation, List<IChangePattern> changePatterns, boolean isCheckCrossover) {
		this.isCheckMutation = isCheckMutation;
		this.changePatterns = changePatterns;
		this.isCheckCrossover = isCheckCrossover;
	}
	
	public void setTerminationCondition (List<TerminationCondition> conditions) {
		this.terminationCondition = conditions;
	}
	
	public void setLabel (ProcessGeneratorView view) {
		this.view = view;
	}
	
	public void setWeightHelper (FitnessWeightHelper weightHelper) {
		this.weightHelper = weightHelper;
	}
	
	public boolean isReady () {
		return (this.isCheckCrossover == null) || (this.isCheckMutation == null) || (this.changePatterns == null)
				|| (this.contraintesElements == null) || (this.contraintesWorkflows == null) || (this.elitism == null)
				|| (this.initialProcess == null) || (this.location == null) || (this.manualOclChecker == null)
				|| (this.margin == null) || (this.nbNodes == null) || (this.nbPopulation == null)
				|| (this.selectionStrategy == null) || (this.terminationCondition == null) || (this.decisionMaker == null)
				|| (view == null) || (weightHelper == null);
	}
	
	/**
	 * La méthode qui lance l'exécution de l'algorithme génétique.
	 * 
	 * @throws GeneticException
	 * @throws IOException
	 */
	public void run () {
		if (!isReady()) {
			view.print("The genetic algorithm executor is not completely set.");
			return;
		}
		
		if (!decisionMaker.isBpmn()) {
			view.printError("The uml generation is not implemented yet.");
			view.print("");
			return;
		}
		if (decisionMaker.isBpmn()) {
			try {
				this.runBpmn();
			} catch (GeneticException | IOException | IllegalArgumentException e) {
				e.printStackTrace();
				view.printError(e.getMessage());
			}
		}
	}
	
	/**
	 * Lance l'algorithme génétique pour le cas d'une exécution en mode BPMN.
	 * 
	 * @throws GeneticException
	 * @throws IOException
	 */
	private void runBpmn () throws GeneticException, IOException {
		
		// la factory des candidats de départ
		CandidateFactory<BpmnProcess> factory = null;
		if (initialProcess != null && initialProcess instanceof BpmnProcess)
			factory = new BpmnCandidateFactory((BpmnProcess) initialProcess);
		else
			factory = new BpmnCandidateFactory();
		
		// définition des opérations
		List<EvolutionaryOperator<BpmnProcess>> operations = new ArrayList<EvolutionaryOperator<BpmnProcess>>();
		if (isCheckCrossover)
			operations.add(new BpmnCrossoverOperation());
		if (isCheckMutation && changePatterns != null)
			operations.add(new BpmnMutationOperation(changePatterns, contraintesWorkflows));
		
		if (operations.isEmpty())
			throw new GeneticException("You must select at least one evolutionary operator.");
		
		EvolutionaryOperator<BpmnProcess> pipeline = new EvolutionPipeline<BpmnProcess>(operations);
		
		// la classe calculant le fitness
		FitnessEvaluator<BpmnProcess> fitnessEvaluator = new BpmnFitnessEvaluator(nbNodes, margin, contraintesElements,
				contraintesWorkflows, manualOclChecker, weightHelper);
		
		// le random rapide et juste de watchmaker
		Random random = new MersenneTwisterRNG();
		
		// la méthode de sélection
		SelectionStrategy<Object> selection;
		if (selectionStrategy.equals(ESelectionStrategy.Rank_selection.toString()))
			selection = new RankSelection();
		else if (selectionStrategy.equals(ESelectionStrategy.Roulette_wheel_selection.toString()))
			selection = new RouletteWheelSelection();
		else if (selectionStrategy.equals(ESelectionStrategy.Stochastic_universal_sampling.toString()))
			selection = new StochasticUniversalSampling();
		else if (selectionStrategy.equals(ESelectionStrategy.Tournament_selection.toString()))
			selection = new TournamentSelection(new Probability(0.5));
		else
			throw new GeneticException("The selection strategy selected is not know.");
		
		// le moteur d'évolution avec tous les paramètres ci-dessus
		EvolutionEngine<BpmnProcess> engine = new GenerationalEvolutionEngine<BpmnProcess>(factory, pipeline, fitnessEvaluator,
				selection, random);
		
		// un petit observeur pour voir ce qu'il se passe
		Benchmarker bench = new Benchmarker();
		engine.addEvolutionObserver(new MyEvolutionObserver(view, bench));
		
		// les conditions de terminaisons
		TerminationCondition[] cond = new TerminationCondition[terminationCondition.size()];
		int i = 0;
		for (TerminationCondition c : terminationCondition) {
			cond[i] = c;
			i++;
		}
		
		// la méthode d'évolution
		bench.start();
		BpmnProcess winner = engine.evolve(nbPopulation, elitism, cond);
		bench.stop(location + "bpmn_bench.csv");
		
		// la sauvegarde du process winner
		i = 0;
		while (new File(location + "gen" + i + ".bpmn").exists()) {
			i++;
		}
		winner.save(location + "gen" + i + ".bpmn");
	}
}
