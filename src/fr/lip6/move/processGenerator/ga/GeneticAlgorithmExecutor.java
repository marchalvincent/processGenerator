package fr.lip6.move.processGenerator.ga;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.search.internal.ui.SearchPlugin;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.maths.random.Probability;
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
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.views.ProcessGeneratorView;

/**
 * Cette classe se charge d'instancier et de lancer l'algorithme génétique.
 * 
 * @author Vincent
 * 
 * @param <T>
 *            le type de candidat que fait évoluer l'algorithme génétique.
 */
@SuppressWarnings("restriction")
public abstract class GeneticAlgorithmExecutor<T> extends Thread {
	
	private String location;
	private Integer nbNodes;
	private Integer margin;
	
	private List<StructuralConstraintChecker> contraintesElements;
	private List<StructuralConstraintChecker> contraintesWorkflows;
	private StructuralConstraintChecker manualOclChecker;
	
	private Integer nbPopulation;
	private T initialProcess;
	private Integer elitism;
	private String selectionStrategy;
	
	private Boolean isCheckMutation;
	private List<IChangePattern<T>> changePatterns;
	private Boolean isCheckCrossover;
	
	private List<TerminationCondition> terminationCondition;
	private FitnessWeightHelper weightHelper;
	
	private ProcessGeneratorView view;
	
	public GeneticAlgorithmExecutor() {
		super();
	}
	
	public void setRunConfiguration(String location, int nbNodes, int margin) {
		this.location = location;
		this.nbNodes = nbNodes;
		this.margin = margin;
	}
	
	public void setStructuralsConstraintsChecker(List<StructuralConstraintChecker> contraintesElements,
			List<StructuralConstraintChecker> contraintesWorkflows, StructuralConstraintChecker manualOclChecker) {
		this.contraintesElements = contraintesElements;
		this.contraintesWorkflows = contraintesWorkflows;
		this.manualOclChecker = manualOclChecker;
	}
	
	public void setNbPopulation(int nbPopulation) {
		this.nbPopulation = nbPopulation;
	}
	
	public void setInitialProcess(T initialProcess) {
		this.initialProcess = initialProcess;
	}
	
	public void setGeneticSelection(int elitism, String selectionStrategy) {
		this.elitism = elitism;
		this.selectionStrategy = selectionStrategy;
	}
	
	public void setGeneticOperations(boolean isCheckMutation, List<IChangePattern<T>> changePatterns, boolean isCheckCrossover) {
		this.isCheckMutation = isCheckMutation;
		this.changePatterns = changePatterns;
		this.isCheckCrossover = isCheckCrossover;
	}
	
	public void setTerminationCondition(List<TerminationCondition> conditions) {
		this.terminationCondition = conditions;
	}
	
	public void setLabel(ProcessGeneratorView view) {
		this.view = view;
	}
	
	public void setWeightHelper(FitnessWeightHelper weightHelper) {
		this.weightHelper = weightHelper;
	}
	
	public boolean isReady() {
		return !((this.isCheckCrossover == null) || (this.isCheckMutation == null) || (this.changePatterns == null)
				|| (this.contraintesElements == null) || (this.contraintesWorkflows == null) || (this.elitism == null)
				|| (this.location == null) || (this.margin == null) || (this.nbNodes == null)
				|| (this.nbPopulation == null) || (this.selectionStrategy == null) || (this.terminationCondition == null)
				|| (view == null) || (weightHelper == null));
	}
	
	/**
	 * La méthode qui lance l'exécution de l'algorithme génétique.
	 * 
	 * @throws GeneticException
	 * @throws IOException
	 */
	public void run() {
		if (!isReady()) {
			view.print("The genetic algorithm executor is not completely set.");
			return;
		}
		
		try {
			this.runExecution();
		} catch (GeneticException | IOException | IllegalArgumentException e) {
			e.printStackTrace();
			view.printError(e.getMessage());
		}
	}

	/**
	 * La méthode qui lance l'exécution de l'algorithme génétique.
	 * 
	 * @throws GeneticException
	 * @throws IOException
	 */
	private void runExecution() throws GeneticException, IOException {
		
		// définition des opérations
		List<EvolutionaryOperator<T>> operations = new ArrayList<>();
		if (isCheckCrossover)
			operations.add(getCrossoverOperation());
		if (isCheckMutation && changePatterns != null)
			operations.add(getMutationOperation(changePatterns, contraintesWorkflows));
		
		if (operations.isEmpty())
			throw new GeneticException("You must select at least one evolutionary operator.");
		
		EvolutionaryOperator<T> pipeline = new EvolutionPipeline<T>(operations);
		
		// la classe calculant le fitness
		FitnessEvaluator<T> fitnessEvaluator = getFitnessEvaluator(nbNodes, margin, contraintesElements, contraintesWorkflows,
				manualOclChecker, weightHelper);
		
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
		EvolutionEngine<T> engine = new GenerationalEvolutionEngine<>(getCandidateFactory(initialProcess), pipeline,
				fitnessEvaluator, selection, random);
		
		// un petit observeur pour voir ce qu'il se passe
		Benchmarker bench = new Benchmarker();
		engine.addEvolutionObserver(new MyEvolutionObserver<T>(view, bench));
		
		// les conditions de terminaisons
		TerminationCondition[] cond = new TerminationCondition[terminationCondition.size()];
		int i = 0;
		for (TerminationCondition c : terminationCondition) {
			cond[i] = c;
			i++;
		}
		
		// la méthode d'évolution
		bench.start();
		T winner = engine.evolve(nbPopulation, elitism, cond);
		bench.stop(location + "bpmn_bench.csv");
		
		// la sauvegarde du process winner
		try {
			String path = this.saveWinner(winner, location);
			view.printAppend("Your file has been created : " + path);
			if (path.subSequence(path.length() - 4, path.length()).equals(".uml"))
				this.openFile(path);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Ouvre le fichier dont le path est passé en paramètre sur l'editeur d'eclipse. Utilise l'éditeur par défaut.
	 * 
	 * @param path
	 */
	private void openFile(String path) {
		final File fileToOpen = new File(path);
		
		if (fileToOpen.exists() && fileToOpen.isFile()) {
			
			Display.getDefault().asyncExec(new Runnable() {
				public void run() {
					IFileStore fileStore = EFS.getLocalFileSystem().getStore(fileToOpen.toURI());
					IWorkbenchPage page = SearchPlugin.getActivePage();
					try {
						IDE.openEditorOnFileStore(page, fileStore);
					} catch (PartInitException e) {
						e.printStackTrace();
					}
				}
			});
		} else {
			System.err.println("The file does not exist.");
		}
	}
	
	/**
	 * Sauvegarde le candidat sélectionné à la fin de la génération du process.
	 * 
	 * @param winner
	 *            le candidat sélectionné.
	 * @param location
	 *            le chemin du dossier de travail
	 * @return String le path complet du fichier qui vient d'être créé
	 * @throws IOException
	 *             lorsque l'enregistrement à échoué
	 */
	protected abstract String saveWinner(T winner, String location) throws IOException;

	/**
	 * Renvoie la classe chargée de l'évaluation "fitness" de chaque candidat selon les contraintes spécifiée par
	 * l'utilisateur.
	 * 
	 * @param nbNodes
	 *            le nombre de noeuds que doivent avoir les candidats.
	 * @param margin
	 *            la marge qu'ils ont pour la taille.
	 * @param contraintesElements
	 *            les contraintes sur le nombre de chaque élément.
	 * @param contraintesWorkflows
	 *            les contraintes sur les workflows patterns qu'ils doivent respecter.
	 * @param manualOclChecker
	 *            la contrainte manuelle que l'utilisateur a saisie.
	 * @param weightHelper
	 *            le poids associé à chaque type de contrainte.
	 * @return {@link FitnessEvaluator}.
	 */
	protected abstract FitnessEvaluator<T> getFitnessEvaluator(Integer nbNodes, Integer margin,
			List<StructuralConstraintChecker> contraintesElements, List<StructuralConstraintChecker> contraintesWorkflows,
			StructuralConstraintChecker manualOclChecker, FitnessWeightHelper weightHelper);
	
	/**
	 * Renvoie la classe chargée de la mutation des candidats entre deux génération.
	 * 
	 * @param changePatterns
	 *            la liste des {@link IChangePattern} que l'on doit appliquer aux candidats.
	 * @param contraintesWorkflows
	 *            la liste des {@link StructuralConstraintChecker} que doivent vérifier les candidats.
	 * @return {@link EvolutionaryOperator}.
	 * @throws GeneticException
	 *             lorsque les paramètres ne sont pas corrects.
	 */
	protected abstract EvolutionaryOperator<T> getMutationOperation(List<IChangePattern<T>> changePatterns,
			List<StructuralConstraintChecker> contraintesWorkflows) throws GeneticException;
	
	/**
	 * Renvoie la classe chargée du croisement des candidats entre deux générations.
	 * 
	 * @return {@link EvolutionaryOperator}.
	 */
	protected abstract EvolutionaryOperator<T> getCrossoverOperation();
	
	/**
	 * Renvoie la factory qui construit les candidats initiaux pour l'algorithme génétique.
	 * 
	 * @param initialProcess
	 *            le process initial ou null s'il n'existe pas.
	 * @return {@link CandidateFactory}.
	 */
	protected abstract CandidateFactory<T> getCandidateFactory(T initialProcess);
}
