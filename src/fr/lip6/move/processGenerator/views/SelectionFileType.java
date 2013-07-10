package fr.lip6.move.processGenerator.views;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import fr.lip6.move.processGenerator.ConfigurationManager;
import fr.lip6.move.processGenerator.bpmn2.EBpmnElement;
import fr.lip6.move.processGenerator.bpmn2.constraints.EBpmnWorkflowPattern;
import fr.lip6.move.processGenerator.bpmn2.ga.cp.EBpmnChangePattern;
import fr.lip6.move.processGenerator.uml2.EUmlElement;
import fr.lip6.move.processGenerator.uml2.constraints.EUmlWorkflowPattern;
import fr.lip6.move.processGenerator.uml2.ga.cp.EUmlChangePattern;

/**
 * Evenement déclenché par la sélection d'un type de fichier de sortie (bpmn, uml, etc.).
 * 
 * @author Vincent
 * 
 */
public class SelectionFileType extends SelectionAdapter {
	
	private ProcessGeneratorView view;
	
	public SelectionFileType(ProcessGeneratorView view) {
		super();
		this.view = view;
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		Combo combo = (Combo) e.getSource();
		String s = combo.getText();
		if (s.toLowerCase().contains("bpmn")) {
			// first, on met à jour le type de fichier dans les préférences
			ConfigurationManager.instance.setTypeFile("0");
			
			// les champs du tableau éléments
			view.majTreeOfElements(EBpmnElement.values());
			
			// on met à jour le tableau des workflow patterns
			view.majTreeOfWorkflows(EBpmnWorkflowPattern.values());
			
			// on met a jour le texte à coté du bouton (set initial process)
			view.getLabelSetInitialProcess().setText("(bpmn file)");
			
			// on affiche le bon tableau des mutations change patterns
			view.majTreeOfChangePatterns(EBpmnChangePattern.values());
			
		} else {
			// first, on met à jour le type dans les préférences
			ConfigurationManager.instance.setTypeFile("1");
			
			// on met à jour le tableau des éléments
			view.majTreeOfElements(EUmlElement.values());
			
			// on met à jour le tableau des workflow patterns
			view.majTreeOfWorkflows(EUmlWorkflowPattern.values());
			
			// on met a jour le texte à coté du bouton (set initial process)
			view.getLabelSetInitialProcess().setText("(uml file)");
			
			// on affiche le bon tableau des mutations change patterns
			view.majTreeOfChangePatterns(EUmlChangePattern.values());
		}
		view.majGeneticAlgorithmInfos();
	}
}
