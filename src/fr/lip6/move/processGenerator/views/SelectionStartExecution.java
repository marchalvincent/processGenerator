package fr.lip6.move.processGenerator.views;

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
import fr.lip6.move.processGenerator.EQuantity;
import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.geneticAlgorithm.ChangePatternFactory;
import fr.lip6.move.processGenerator.geneticAlgorithm.GeneticAlgorithmData;
import fr.lip6.move.processGenerator.geneticAlgorithm.GeneticAlgorithmExecutor;
import fr.lip6.move.processGenerator.geneticAlgorithm.GeneticException;
import fr.lip6.move.processGenerator.geneticAlgorithm.IChangePattern;
import fr.lip6.move.processGenerator.structuralConstraint.IStructuralConstraint;
import fr.lip6.move.processGenerator.structuralConstraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.structuralConstraint.bpmn.BpmnStructuralConstraintFactory;
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
		
		// ONGLET RUN
		String location = view.getLabelLocation().getText();
		int nbNode = view.getSpinnerNbNode().getSelection();
		int margin = view.getSpinnerMargin().getSelection();

		// ONGLET TARGET
		String typeFile = view.getComboTypeFile().getText();

		// les elements et workflows sélectionnés
		Table tableElements = null;
		Table tableWorkflow = null;
		if (typeFile.toLowerCase().contains("bpmn")) {
			typeFile = "bpmn";
		} else {
			typeFile = "uml";
		}

		tableElements = view.getTableElements();
		tableWorkflow = view.getTableWorkflow();
		
		// les listes de contraintes structurelles en fonction des tableaux
		List<StructuralConstraintChecker> contraintesElements = null;
		List<StructuralConstraintChecker> contraintesWorkflows = null;
		try {
			contraintesElements = this.buildStructuralConstraints(tableElements, ConstraintType.Element);
			contraintesWorkflows = this.buildStructuralConstraints(tableWorkflow, ConstraintType.Workflow);
		} catch(BpmnException ex) {
			view.print(ex.getMessage());
			System.err.println(ex.getMessage());
			return;
		}
		
		// la contrainte OCL écrite à la main
		String manualOcl = view.getTextOclConstraint().getText();
		IStructuralConstraint contrainte = null;
		StructuralConstraintChecker manualOclChecker = null;
		if (!manualOcl.isEmpty()) {
			contrainte = BpmnStructuralConstraintFactory.getInstance().newManualOclConstraint(manualOcl);
			manualOclChecker = new StructuralConstraintChecker(contrainte, EQuantity.MORE_OR_EQUAL, 1);
		}
		
		// ONGLET ALGO GENETIQUE
		// le nombre de population
		int nbPopulation = view.getSpinnerNbPopulation().getSelection();
		
		// les process initiaux s'ils existent
		BpmnProcess initialBpmnProcess = view.getInitialBpmnProcess();
		UmlProcess initialUmlProcess = view.getInitialUmlProcess();

		// le nombre d'elitism et la stratégie de sélection 
		int elitism = view.getSpinnerElitism().getSelection();
		String selectionStrategy = view.getComboStrategySelection().getText();

		boolean isCheckMutation = view.getButtonCheckMutation().getSelection();
		List<IChangePattern> changePatterns = null;
		if (isCheckMutation) {
			changePatterns = this.getChangePatterns(typeFile);
		}
		
		boolean isCheckCrossover = view.getButtonCheckCrossover().getSelection();
		
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
		
		executor.start();
		// TODO tester
	}

	private List<IChangePattern> getChangePatterns(String typeFile) {

		IChangePattern cPattern;
		List<IChangePattern> changePatterns = new ArrayList<IChangePattern>();
		for (TableItem item : view.getTableMutationParameters().getItems()) {
			cPattern = ChangePatternFactory.getInstance().getChangePattern(typeFile, item.getText(0), item.getText(1));
			if (cPattern != null)
				changePatterns.add(cPattern);
		}
		return changePatterns;
	}

	private List<StructuralConstraintChecker> buildStructuralConstraints(Table table, ConstraintType constraintType) throws BpmnException {

		List<StructuralConstraintChecker> liste = new ArrayList<StructuralConstraintChecker>();
		// pour chaque ligne du tableau
		for (TableItem item : table.getItems()) {
			// on n'ajoute la condition que lorsqu'elle est cochée
			if (item.getChecked()) {
				
				// on construit la StructuralConstraint dyamiquement en fonction du type
				IStructuralConstraint contrainte;
				if (constraintType.equals(ConstraintType.Element)) {
					contrainte = BpmnStructuralConstraintFactory.getInstance().newElementConstraint(item.getText(1));
				} else {
					contrainte = BpmnStructuralConstraintFactory.getInstance().newWorkflowPatternConstraint(item.getText(1));
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
					System.err.println("Le nombre du tableau ne peut être parsé !");
				}
			}
		}
		return liste;
	}
	
}
