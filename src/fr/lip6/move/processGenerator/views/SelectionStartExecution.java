package fr.lip6.move.processGenerator.views;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.uncommons.watchmaker.framework.TerminationCondition;
import org.uncommons.watchmaker.framework.termination.ElapsedTime;
import org.uncommons.watchmaker.framework.termination.GenerationCount;
import org.uncommons.watchmaker.framework.termination.Stagnation;
import org.uncommons.watchmaker.framework.termination.TargetFitness;
import org.uncommons.watchmaker.framework.termination.UserAbort;
import fr.lip6.move.processGenerator.ConfigurationManager;
import fr.lip6.move.processGenerator.EQuantity;
import fr.lip6.move.processGenerator.IEnumElement;
import fr.lip6.move.processGenerator.Utils;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.bpmn2.ga.BpmnGeneticAlgorithmExecutor;
import fr.lip6.move.processGenerator.constraint.AbstractStructuralConstraintFactory;
import fr.lip6.move.processGenerator.constraint.IStructuralConstraint;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.ga.DecisionMaker;
import fr.lip6.move.processGenerator.ga.FitnessWeightHelper;
import fr.lip6.move.processGenerator.ga.GeneticAlgorithmData;
import fr.lip6.move.processGenerator.ga.GeneticAlgorithmExecutor;
import fr.lip6.move.processGenerator.ga.IChangePattern;
import fr.lip6.move.processGenerator.ga.IEnumChangePattern;
import fr.lip6.move.processGenerator.uml2.UmlProcess;
import fr.lip6.move.processGenerator.uml2.ga.UmlGeneticAlgorithmExecutor;

/**
 * Cet adapteur est déclenché lorsque l'utilisateur click sur le bouton "run".
 * 
 * @author Vincent
 * 
 */
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
		
		ConfigurationManager.instance.setLocation(location);
		ConfigurationManager.instance.setNbNodes(nbNode + "");
		ConfigurationManager.instance.setMargin(margin + "");
		
		// ONGLET TARGET
		String typeFile = view.getComboTypeFile().getText();
		ConfigurationManager.instance.setTypeFile(view.getComboTypeFile().getSelectionIndex() + "");
		
		DecisionMaker decisionMaker = new DecisionMaker(typeFile);
		
		// on construit la factory des contraintes au passage
		AbstractStructuralConstraintFactory factory = decisionMaker.getStructuralConstraintFactory();
		typeFile = decisionMaker.getTypeFile();
		
		// les elements et workflows sélectionnés
		Tree treeElements = view.getTreeElements();
		Tree treeWorkflows = view.getTreeWorkflows();
		
		// les listes de contraintes structurelles en fonction des tableaux
		List<StructuralConstraintChecker> contraintesElements = null;
		List<StructuralConstraintChecker> contraintesWorkflows = null;
		try {
			contraintesElements = this.buildStructuralConstraints(treeElements.getItems(), ConstraintType.Element, factory, null);
			contraintesWorkflows = this.buildStructuralConstraints(treeWorkflows.getItems(), ConstraintType.Workflow, factory,
					null);
		} catch (Exception ex) {
			view.printError(ex.getMessage());
			System.err.println(ex.getMessage());
			return;
		}
		
		// la contrainte OCL écrite à la main
		String manualOcl = view.getTextOclConstraint().getText();
		IStructuralConstraint contrainte = null;
		StructuralConstraintChecker manualOclChecker = null;
		if (!manualOcl.isEmpty()) {
			contrainte = factory.newManualOclConstraint(manualOcl);
			manualOclChecker = new StructuralConstraintChecker(contrainte);
		}
		
		// ONGLET ALGO GENETIQUE
		// le nombre de population
		int nbPopulation = view.getSpinnerNbPopulation().getSelection();
		ConfigurationManager.instance.setPopulation(nbPopulation + "");
		
		// les process initiaux s'ils existent
		BpmnProcess initialBpmnProcess = view.getInitialBpmnProcess();
		UmlProcess initialUmlProcess = view.getInitialUmlProcess();
		
		// le nombre d'elitism et la stratégie de sélection
		int elitism = view.getSpinnerElitism().getSelection();
		String selectionStrategy = view.getComboStrategySelection().getText();
		
		ConfigurationManager.instance.setElitism(elitism + "");
		ConfigurationManager.instance.setSelectionStrategy(view.getComboStrategySelection().getSelectionIndex() + "");
		
		// les opérations d'évolution
		boolean isCheckMutation = view.getButtonCheckMutation().getSelection();
		ConfigurationManager.instance.setCheckMutation(isCheckMutation);
		
		List<IChangePattern<?>> changePatterns = null;
		if (isCheckMutation) {
			try {
				changePatterns = this.getChangePatterns(view.getTreeMutationParameters().getItems(), null);
			} catch (Exception ex) {
				view.printError(ex.getMessage());
				System.err.println(ex.getMessage());
				return;
			}
		}
		
		boolean isCheckCrossover = view.getButtonCheckCrossover().getSelection();
		ConfigurationManager.instance.setCheckCrossover(isCheckCrossover);
		
		// les conditions de terminaison
		List<TerminationCondition> conditions = new ArrayList<TerminationCondition>();
		boolean bool;
		// 1 solution trouvée
		bool = view.getButtonUntilSolutionFound().getSelection();
		if (bool)
			conditions.add(new TargetFitness(GeneticAlgorithmData.totalFitness, true));
		ConfigurationManager.instance.setSolutionFound(bool);
		
		// during x secondes
		int secondes = view.getSpinnerUntilSeconde().getSelection();
		bool = view.getButtonDuringSeconde().getSelection();
		if (bool)
			conditions.add(new ElapsedTime(secondes * 1000));
		ConfigurationManager.instance.setDuringSecondes(bool);
		ConfigurationManager.instance.setNbSecondes(secondes);
		
		// during x generation
		int generations = view.getSpinnerUntilGeneration().getSelection();
		bool = view.getButtonUntilGeneration().getSelection();
		if (bool)
			conditions.add(new GenerationCount(generations));
		ConfigurationManager.instance.setUntilGenerations(bool);
		ConfigurationManager.instance.setNbGenerations(generations);
		
		// during x stagnation
		int stagnations = view.getSpinnerUntilStagnation().getSelection();
		bool = view.getButtonUntilStagnation().getSelection();
		if (bool)
			conditions.add(new Stagnation(stagnations, true));
		ConfigurationManager.instance.setUntilStagnations(bool);
		ConfigurationManager.instance.setNbStagnations(stagnations);
		
		// les poids fitness
		int sizeWeight = view.getSpinnerSizeWeight().getSelection();
		int elementWeight = view.getSpinnerElementWeight().getSelection();
		int workflowWeight = view.getSpinnerWorkflowWeight().getSelection();
		int manualOclWeight = view.getSpinnerManualOclWeight().getSelection();
		FitnessWeightHelper weightHelper = new FitnessWeightHelper(sizeWeight, elementWeight, workflowWeight, manualOclWeight);
		
		ConfigurationManager.instance.setSizeWeight(sizeWeight + "");
		ConfigurationManager.instance.setElementsWeight(elementWeight + "");
		ConfigurationManager.instance.setWorkflowsWeight(workflowWeight + "");
		ConfigurationManager.instance.setManualOCLWeight(manualOclWeight + "");
		
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
		if (decisionMaker.isBpmn()) {
			
			GeneticAlgorithmExecutor<BpmnProcess> executor = new BpmnGeneticAlgorithmExecutor();
			
			// on est obligé de caster les changePatterns pour qu'ils correspondent à l'éxecutor
			List<IChangePattern<BpmnProcess>> newChangePatterns = Utils.instance.castChangePattern(changePatterns,
					BpmnProcess.class);
			
			executor.setGeneticOperations(isCheckMutation, newChangePatterns, isCheckCrossover);
			executor.setGeneticSelection(elitism, selectionStrategy);
			executor.setInitialProcess(initialBpmnProcess);
			
			executor.setLabel(view);
			executor.setNbPopulation(nbPopulation);
			executor.setRunConfiguration(location, nbNode, margin);
			executor.setStructuralsConstraintsChecker(contraintesElements, contraintesWorkflows, manualOclChecker);
			executor.setTerminationCondition(conditions);
			executor.setWeightHelper(weightHelper);
			
			executor.start();
		} else if (decisionMaker.isUml()) {
			
			GeneticAlgorithmExecutor<UmlProcess> executor = new UmlGeneticAlgorithmExecutor();
			
			// on est obligé de caster les changePatterns pour qu'ils correspondent à l'éxecutor
			List<IChangePattern<UmlProcess>> newChangePatterns = Utils.instance.castChangePattern(changePatterns,
					UmlProcess.class);
			
			executor.setGeneticOperations(isCheckMutation, newChangePatterns, isCheckCrossover);
			executor.setGeneticSelection(elitism, selectionStrategy);
			executor.setInitialProcess(initialUmlProcess);
			
			// désolé pour la duplication de code...
			executor.setLabel(view);
			executor.setNbPopulation(nbPopulation);
			executor.setRunConfiguration(location, nbNode, margin);
			executor.setStructuralsConstraintsChecker(contraintesElements, contraintesWorkflows, manualOclChecker);
			executor.setTerminationCondition(conditions);
			executor.setWeightHelper(weightHelper);
			
			executor.start();
		}
		
		// on enregistre les conf
		try {
			ConfigurationManager.instance.store();
		} catch (IOException e1) {
			System.err.println("Impossible to save the configuration.");
		}
	}
	
	/**
	 * En fonction du type de fichier passé en paramètre, cette méthode va récupérer le tableau des change patterns
	 * sélectionné par l'utilisateur, les instancier et les renvoyer.
	 * 
	 * @return une {@link List} de {@link IChangePattern}.
	 * @throws Exception
	 */
	private List<IChangePattern<?>> getChangePatterns(TreeItem[] items, StringBuilder sb) throws Exception {
		
		if (items == null || items.length == 0)
			return Collections.emptyList();
		
		List<IChangePattern<?>> changePatterns = new ArrayList<>();
		// le stringBuilder va servir à enregistrer les préférences utilisateurs
		if (sb == null)
			sb = new StringBuilder();
		
		// pour chaque ligne du tableau
		for (TreeItem item : items) {
			
			// on applique la récursivité sur les fils
			List<IChangePattern<?>> listTemp = this.getChangePatterns(item.getItems(), sb);
			changePatterns.addAll(listTemp);
			
			// les préférences utilisateurs
			sb.append("___");
			sb.append(item.getData(Utils.NAME_KEY).toString());
			sb.append("%");
			if (item.getChecked())
				sb.append("1%");
			else
				sb.append("0%");
			sb.append(item.getData(Utils.NUMBER_KEY));
			
			// si le change pattern est coché
			if (item.getChecked()) {
				// on vérifie que l'item est bien une enum de change pattern
				if (item.getData(Utils.NAME_KEY) instanceof IEnumChangePattern<?>) {
					// si oui, on instancie dynamiquement la classe
					IChangePattern<?> cPattern = ((IEnumChangePattern<?>) item.getData(Utils.NAME_KEY)).newInstance((String) item
							.getData(Utils.NUMBER_KEY));
					changePatterns.add(cPattern);
				} else {
					System.err.println("Carreful, the item data is not a " + IEnumChangePattern.class.getSimpleName() + ".");
				}
			}
		}
		
		ConfigurationManager.instance.setChangePatternAttributes(sb.toString());
		return changePatterns;
	}
	
	/**
	 * Construit une liste de {@link StructuralConstraintChecker} en fonction de l'arbre passé en paramètre. Cette
	 * méthode fonctionne à la fois pour l'arbre des éléments mais aussi pour l'arbre des workflow patterns.
	 * 
	 * @param treeElements
	 *            l'arbre à parser pour construire les checkeurs contraintes.
	 * @param constraintType
	 * @param factory
	 * @return
	 * @throws Exception
	 */
	private List<StructuralConstraintChecker> buildStructuralConstraints(TreeItem[] items, ConstraintType constraintType,
			AbstractStructuralConstraintFactory factory, StringBuilder sb) throws Exception {
		
		if (items.length == 0)
			return Collections.emptyList();
		
		// ce stringBuilder va permettre d'enregistrer les préférences utilisateurs
		if (sb == null)
			sb = new StringBuilder();
		
		List<StructuralConstraintChecker> liste = new ArrayList<>();
		// pour chaque ligne du tableau
		for (TreeItem item : items) {
			
			// on applique la récursivité sur les fils
			List<StructuralConstraintChecker> listTemp = this.buildStructuralConstraints(item.getItems(), constraintType,
					factory, sb);
			liste.addAll(listTemp);
			
			// on récupère la quantité
			EQuantity quantity = EQuantity.getQuantityByString((String) item.getData(Utils.QUANTITY_KEY));
			
			int number = 1, weight = 1;
			try {
				/*
				 * puis le nombre (normalement le parseInt ne renvoie pas d'exception car le traitement est déjà fait à
				 * la volée)
				 */
				number = Integer.parseInt((String) item.getData(Utils.NUMBER_KEY));
				// ainsi que le poids
				weight = Integer.parseInt((String) item.getData(Utils.WEIGHT_KEY));
				
			} catch (Exception e) {
				// une erreur ici ne devrait pas arriver car le traitement est déjà fait dans le listener
				e.printStackTrace();
			}
			
			// pour les préférences utilisateurs
			sb.append("___");
			sb.append(item.getText(0));
			// la case à cochée
			sb.append("%");
			sb.append((item.getChecked()) ? "1" : "0");
			// la sélection de la quantité
			sb.append("%");
			sb.append(quantity.getPosition());
			// ensuite le nombre
			sb.append("%");
			sb.append(number);
			// puis enfin le poids
			sb.append("%");
			sb.append(weight);
			
			// on n'ajoute la condition que lorsqu'elle est cochée
			if (item.getChecked()) {
				// on construit la StructuralConstraint dyamiquement en fonction du type
				IStructuralConstraint contrainte;
				if (constraintType.equals(ConstraintType.Element)) {
					Object o = item.getData(Utils.NAME_KEY);
					if (o instanceof IEnumElement)
						contrainte = factory.newElementConstraint((IEnumElement) o);
					else {
						System.err.println("The object in the table is not a " + IEnumElement.class.getSimpleName() + ".");
						continue;
					}
				} else {
					contrainte = factory.newWorkflowPatternConstraint(item.getData(Utils.NAME_KEY));
				}
				
				// on construit le checker puis on l'ajoute à la liste
				StructuralConstraintChecker checker = new StructuralConstraintChecker(contrainte, quantity, number, weight);
				liste.add(checker);
			}
		}
		
		// enregistrement des préférences utilisateur
		if (constraintType.equals(ConstraintType.Element)) {
			ConfigurationManager.instance.setElementsAttributes(sb.toString());
		} else {
			ConfigurationManager.instance.setWorkflowsAttributes(sb.toString());
		}
		
		return liste;
	}
	
}
