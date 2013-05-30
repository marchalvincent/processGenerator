package fr.lip6.move.processGenerator.views;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import fr.lip6.move.processGenerator.EQuantity;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
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
			tableElements = view.getTableBpmnElements();
			tableWorkflow = view.getTableBpmnWorkflow();
		} else {
			tableElements = view.getTableUmlElements();
			tableWorkflow = view.getTableUmlWorkflow();
		}
		// les listes de contraintes structurelles en fonction des tableaux
		List<StructuralConstraintChecker> contraintesElements = this.buildStructuralConstraints(tableElements, ConstraintType.Element);
		List<StructuralConstraintChecker> contraintesWorkflows = this.buildStructuralConstraints(tableWorkflow, ConstraintType.Workflow);

		// la contrainte OCL écrite à la main
		String manualOcl = view.getTextOclConstraint().getText();
		IStructuralConstraint contrainte = BpmnStructuralConstraintFactory.getInstance().newManualOclConstraint(manualOcl);
		StructuralConstraintChecker manualOclChecker = new StructuralConstraintChecker(contrainte, EQuantity.MORE_OR_EQUAL, 1);
		
		// ONGLET ALGO GENETIQUE
		// le nombre de population
		int nbPopulation = view.getSpinnerNbPopulation().getSelection();
		
		// les process initiaux s'ils existent
		BpmnProcess initialBpmnProcess = view.getInitialBpmnProcess();
		UmlProcess initialUmlProcess = view.getInitialUmlProcess();

		// le nombre d'elitism
		int elitism = view.getSpinnerElitism().getSelection();
		String selectionStrategy = view.getComboStrategySelection().getText();

		boolean isCheckMutation = view.getButtonCheckMutation().getSelection();
		if (isCheckMutation) {
			List<IChangePattern> changePatterns; // TODO construire la liste en fonction du tableau
			// TODO
		}
		
		boolean isCheckCrossover = view.getButtonCheckCrossover().getSelection();
		
		
		// Enfin on peut construire l'exécuteur de l'algo génétique
	}

	private List<StructuralConstraintChecker> buildStructuralConstraints(Table table, ConstraintType constraintType) {

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
