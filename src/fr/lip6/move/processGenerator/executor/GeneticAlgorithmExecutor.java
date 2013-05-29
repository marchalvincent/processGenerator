package fr.lip6.move.processGenerator.executor;

import org.eclipse.swt.widgets.Table;
import fr.lip6.move.processGenerator.views.ProcessGeneratorView;


public class GeneticAlgorithmExecutor {
	
	private ProcessGeneratorView view;
	
	public GeneticAlgorithmExecutor(ProcessGeneratorView view) {
		super();
		this.view = view;
	}
	
	/**
	 * La méthode appelée lorsqu'on lance l'exécution de la génération.
	 */
	public void run() {
		
		// les infos de l'onglet run
		int nbNode = view.getSpinnerNbNode().getSelection();
		int margin = view.getSpinnerMargin().getSelection();
		
		// les infos de l'onglet target
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
		// TODO construire les objets TableCondition
		
	}
}
