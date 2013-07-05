package fr.lip6.move.processGenerator.views;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
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
			// les champs du tableau éléments
			view.majTableOfElements(EBpmnElement.values());
			
			// on met à jour le tableau des workflow patterns
			view.majTableOfWorkflows(EBpmnWorkflowPattern.values());
			
			// on met a jour le texte à coté du bouton (set initial process)
			view.getLabelSetInitialProcess().setText("(bpmn file)");
			
			// on affiche le bon tableau des mutations change patterns
			view.majTableOfChangePatterns(EBpmnChangePattern.values());
			
		} else {
			// on met à jour le tableau des éléments
			view.majTableOfElements(EUmlElement.values());
			
			// on met à jour le tableau des workflow patterns
			view.majTableOfWorkflows(EUmlWorkflowPattern.values());
			
			// on met a jour le texte à coté du bouton (set initial process)
			view.getLabelSetInitialProcess().setText("(uml file)");
			
			// on affiche le bon tableau des mutations change patterns
			view.majTableOfChangePatterns(EUmlChangePattern.values());
		}
	}
}
