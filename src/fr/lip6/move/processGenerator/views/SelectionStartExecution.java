package fr.lip6.move.processGenerator.views;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.uncommons.watchmaker.framework.TerminationCondition;
import org.uncommons.watchmaker.framework.termination.ElapsedTime;
import org.uncommons.watchmaker.framework.termination.GenerationCount;
import org.uncommons.watchmaker.framework.termination.Stagnation;
import org.uncommons.watchmaker.framework.termination.TargetFitness;
import org.uncommons.watchmaker.framework.termination.UserAbort;
import fr.lip6.move.processGenerator.ConfigurationManager;
import fr.lip6.move.processGenerator.EQuantity;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.geneticAlgorithm.FitnessWeightHelper;
import fr.lip6.move.processGenerator.geneticAlgorithm.GeneticAlgorithmData;
import fr.lip6.move.processGenerator.geneticAlgorithm.GeneticAlgorithmExecutor;
import fr.lip6.move.processGenerator.geneticAlgorithm.GeneticException;
import fr.lip6.move.processGenerator.geneticAlgorithm.IChangePattern;
import fr.lip6.move.processGenerator.geneticAlgorithm.IEnumChangePattern;
import fr.lip6.move.processGenerator.structuralConstraint.AbstractStructuralConstraintFactory;
import fr.lip6.move.processGenerator.structuralConstraint.IStructuralConstraint;
import fr.lip6.move.processGenerator.structuralConstraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.structuralConstraint.bpmn.StructuralConstraintFactory;
import fr.lip6.move.processGenerator.structuralConstraint.uml.UmlStructuralConstraintFactory;
import fr.lip6.move.processGenerator.uml.UmlProcess;


public class SelectionStartExecution extends SelectionAdapter {

	public enum ConstraintType {
		Element,
		Workflow
	}
	
	private ProcessGeneratorView view;

	public SelectionStartExecution(ProcessGeneratorView view) {
		super();
		this.view = view;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {

		view.print("Initialization...");
		
		// on créé le répertoire s'il n'existe pas
		File directory = new File(view.getLabelLocation().getText());
		if (!directory.isDirectory()) {
			boolean bool = directory.mkdir();
			if (!bool) {
				view.printError("Impossible to create the directory path.");
				System.err.println("Impossible to create the directory path.");
				return;
			}
		}
		
		// ONGLET RUN
		String location = view.getLabelLocation().getText();
		int nbNode = view.getSpinnerNbNode().getSelection();
		int margin = view.getSpinnerMargin().getSelection();
		
		ConfigurationManager.getInstance().setLocation(location);
		ConfigurationManager.getInstance().setNbNodes(nbNode + "");
		ConfigurationManager.getInstance().setMargin(margin + "");

		// ONGLET TARGET
		String typeFile = view.getComboTypeFile().getText();
		ConfigurationManager.getInstance().setTypeFile(view.getComboTypeFile().getSelectionIndex() + "");
		
		// on construit la factory des contraintes au passage
		AbstractStructuralConstraintFactory factory = null;
		if (typeFile.toLowerCase().contains("bpmn")) {
			typeFile = "bpmn";
			factory = StructuralConstraintFactory.getInstance();
		} else {
			typeFile = "uml";
			factory = UmlStructuralConstraintFactory.getInstance();
		}
		
		// les elements et workflows sélectionnés
		Table tableElements = view.getTableElements();
		Table tableWorkflow = view.getTableWorkflow();
		
		// les listes de contraintes structurelles en fonction des tableaux
		List<StructuralConstraintChecker> contraintesElements = null;
		List<StructuralConstraintChecker> contraintesWorkflows = null;
		try {
			contraintesElements = this.buildStructuralConstraints(tableElements, ConstraintType.Element, factory);
			contraintesWorkflows = this.buildStructuralConstraints(tableWorkflow, ConstraintType.Workflow, factory);
		} catch(Exception ex) {
			view.print(ex.getMessage());
			System.err.println(ex.getMessage());
			return;
		}
		
		// la contrainte OCL écrite à la main
		String manualOcl = view.getTextOclConstraint().getText();
		IStructuralConstraint contrainte = null;
		StructuralConstraintChecker manualOclChecker = null;
		if (!manualOcl.isEmpty()) {
			contrainte = factory.newManualOclConstraint(manualOcl);
			manualOclChecker = new StructuralConstraintChecker(contrainte, EQuantity.MORE_OR_EQUAL, 1);
		}
		
		// ONGLET ALGO GENETIQUE
		// le nombre de population
		int nbPopulation = view.getSpinnerNbPopulation().getSelection();
		ConfigurationManager.getInstance().setPopulation(nbPopulation + "");
		
		// les process initiaux s'ils existent
		BpmnProcess initialBpmnProcess = view.getInitialBpmnProcess();
		UmlProcess initialUmlProcess = view.getInitialUmlProcess();

		// le nombre d'elitism et la stratégie de sélection 
		int elitism = view.getSpinnerElitism().getSelection();
		String selectionStrategy = view.getComboStrategySelection().getText();
		
		ConfigurationManager.getInstance().setElitism(elitism + "");
		ConfigurationManager.getInstance().setSelectionStrategy(view.getComboStrategySelection().getSelectionIndex() + "");

		// les opérations d'évolution
		boolean isCheckMutation = view.getButtonCheckMutation().getSelection();
		ConfigurationManager.getInstance().setCheckMutation(isCheckMutation + "");
		
		List<IChangePattern> changePatterns = null;
		if (isCheckMutation) {
			try {
				changePatterns = this.getChangePatterns(typeFile);
			} catch (Exception ex) {
				view.print(ex.getMessage());
				System.err.println(ex.getMessage());
				return;
			}
		}
		
		boolean isCheckCrossover = view.getButtonCheckCrossover().getSelection();
		ConfigurationManager.getInstance().setCheckCrossover(isCheckCrossover + "");
		
		// les conditions de terminaison
		List<TerminationCondition> conditions = new ArrayList<TerminationCondition>();
		// 1 solution trouvée
		if (view.getButtonUntilSolutionFound().getSelection())
			conditions.add(new TargetFitness(GeneticAlgorithmData.totalFitness, true));
		
		// during x secondes
		if (view.getButtonDuringSeconde().getSelection()) 
			conditions.add(new ElapsedTime(view.getSpinnerUntilSeconde().getSelection() * 1000));
		
		// during x generation
		if (view.getButtonUntilGeneration().getSelection())
			conditions.add(new GenerationCount(view.getSpinnerUntilGeneration().getSelection()));
		
		// during x stagnation
		if (view.getButtonUntilStagnation().getSelection())
			conditions.add(new Stagnation(view.getSpinnerUntilStagnation().getSelection(), true));
		
		// les poids fitness
		int sizeWeight = view.getSpinnerSizeWeight().getSelection();
		int elementWeight = view.getSpinnerElementWeight().getSelection();
		int workflowWeight = view.getSpinnerWorkflowWeight().getSelection();
		int manualOclWeight = view.getSpinnerManualOclWeight().getSelection();
		FitnessWeightHelper weightHelper = new FitnessWeightHelper(sizeWeight, elementWeight, workflowWeight, manualOclWeight);
		
		ConfigurationManager.getInstance().setSizeWeight(sizeWeight + "");
		ConfigurationManager.getInstance().setElementsWeight(elementWeight + "");
		ConfigurationManager.getInstance().setWorkflowsWeight(workflowWeight + "");
		ConfigurationManager.getInstance().setManualOCLWeight(manualOclWeight + "");
		
		// le bouton stop
		final UserAbort userAbort = new UserAbort();
		view.getButtonStop().addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				userAbort.abort();
			}
		});
		conditions.add(userAbort);
		
		// Enfin on peut construire l'exécuteur de l'algo génétique
		GeneticAlgorithmExecutor executor = new GeneticAlgorithmExecutor();
		executor.setFileType(typeFile);
		executor.setGeneticOperations(isCheckMutation, changePatterns, isCheckCrossover);
		executor.setGeneticSelection(elitism, selectionStrategy);
		try {
			if (typeFile.contains("bpmn") && initialBpmnProcess != null)
				executor.setInitialProcess(initialBpmnProcess);
			else if (initialUmlProcess != null)
				executor.setInitialProcess(initialUmlProcess);
		} catch (GeneticException ex) {
			view.print(ex.getMessage());
			System.err.println(ex.getMessage());
			return;
		}
		executor.setLabel(view);
		executor.setNbPopulation(nbPopulation);
		executor.setRunConfiguration(location, nbNode, margin);
		executor.setStructuralsConstraintsChecker(contraintesElements, contraintesWorkflows, manualOclChecker);
		executor.setTerminationCondition(conditions);
		executor.setWeightHelper(weightHelper);
		
		executor.start();
		
		// on enregistre les conf
		try {
			ConfigurationManager.getInstance().store();
		} catch (IOException e1) {
			System.err.println("Impossible to save the configuration.");
		}
	}

	private List<IChangePattern> getChangePatterns(String typeFile) throws Exception {

		List<IChangePattern> changePatterns = new ArrayList<IChangePattern>();
		
		// pour chaque ligne du tableau
		for (TableItem item : view.getTableMutationParameters().getItems()) {
			// on vérifie que l'item est bien une enum de change pattern
			if (item.getData("0") instanceof IEnumChangePattern) {
				// si oui, on instancie dynamiquement la classe
				IChangePattern cPattern = ((IEnumChangePattern) item.getData("0")).newInstance(item.getText(1));
				changePatterns.add(cPattern);
			} else {
				System.err.println("Carreful, the item data is not a IEnumChangePattern.");
			}
		}
		return changePatterns;
	}

	private List<StructuralConstraintChecker> buildStructuralConstraints(Table table, ConstraintType constraintType, 
			AbstractStructuralConstraintFactory factory) throws Exception {

		List<StructuralConstraintChecker> liste = new ArrayList<StructuralConstraintChecker>();
		// pour chaque ligne du tableau
		for (TableItem item : table.getItems()) {
			// on n'ajoute la condition que lorsqu'elle est cochée
			if (item.getChecked()) {
				
				// on construit la StructuralConstraint dyamiquement en fonction du type
				IStructuralConstraint contrainte;
				if (constraintType.equals(ConstraintType.Element)) {
					contrainte = factory.newElementConstraint(item.getData("1"));
				} else {
					contrainte = factory.newWorkflowPatternConstraint(item.getData("1"));
				}
				
				// on récupère la quantité
				EQuantity quantity = EQuantity.getQuantityByString(item.getText(2));
				
				// puis le nombre (normalement le parseInt ne renvoie pas d'exception car le traitement est déjà fait à la volée
				int number;
				try {
					number = Integer.parseInt(item.getText(3));
					
					// on construit le checker puis on l'ajoute à la liste
					StructuralConstraintChecker checker = new StructuralConstraintChecker(contrainte, quantity, number);
					liste.add(checker);
				} catch(Exception e) {
					System.err.println("NumberFormatException : " + e.getMessage());
				}
			}
		}
		return liste;
	}
	
}
