package fr.lip6.move.processGenerator.views;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.ui.forms.widgets.Section;

/**
 * Evenement déclenché par la sélection d'un type de fichier de sortie (bpmn, uml, etc.).
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
			// on affiche les éléments bpmn
			this.setSectionVisibility(view.getSectionBpmnElements(), true);
			// on cache les éléments uml
			this.setSectionVisibility(view.getSectionUmlElements(), false);
			// on affiche les workflows bpmn
			this.setSectionVisibility(view.getSectionWorkflowBpmn(), true);
			// on cache les workflows uml
			this.setSectionVisibility(view.getSectionWorkflowUml(), false);
			
			view.getLabelSetInitialProcess().setText("(bpmn file)");
			
		} else {
			// on cache les éléments bpmn
			this.setSectionVisibility(view.getSectionBpmnElements(), false);
			// on affiche les éléments uml
			this.setSectionVisibility(view.getSectionUmlElements(), true);
			// on cache les workflows bpmn
			this.setSectionVisibility(view.getSectionWorkflowBpmn(), false);
			// on affiche les workflows uml
			this.setSectionVisibility(view.getSectionWorkflowUml(), true);

			view.getLabelSetInitialProcess().setText("(uml file)");
		}
	}
	
	private void setSectionVisibility(Section section, boolean bool) {
		section.setEnabled(bool);
		section.setExpanded(bool);
	}
}
