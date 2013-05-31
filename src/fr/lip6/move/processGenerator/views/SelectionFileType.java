package fr.lip6.move.processGenerator.views;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import fr.lip6.move.processGenerator.bpmn2.EBpmnElement;
import fr.lip6.move.processGenerator.geneticAlgorithm.ChangePatternFactory;
import fr.lip6.move.processGenerator.structuralConstraint.bpmn.EBpmnWorkflowPattern;
import fr.lip6.move.processGenerator.structuralConstraint.uml.EUmlWorkflowPattern;
import fr.lip6.move.processGenerator.uml.EUmlElement;

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
			// les champs du tableau éléments
			List<String> elements = new ArrayList<String>(EBpmnElement.values().length);
			for (EBpmnElement elem : EBpmnElement.values()) {
				elements.add(elem.toString().toLowerCase());
			}
			view.majTableOfElements(elements);
			
			// on met à jour le tableau des workflow patterns
			view.majTableOfWorkflows(EBpmnWorkflowPattern.patterns);
			
			// on met a jour le texte à coté du bouton (set initial process)
			view.getLabelSetInitialProcess().setText("(bpmn file)");
			
			// on affiche le bon tableau des mutations change patterns
			view.majTableOfChangePatterns(ChangePatternFactory.getInstance().getBpmnChangePatterns());
			
			
		} else {
			// on met à jour le tableau des éléments
			List<String> elements = new ArrayList<String>(EUmlElement.values().length);
			for (EUmlElement elem : EUmlElement.values()) {
				elements.add(elem.toString().toLowerCase());
			}
			view.majTableOfElements(elements);
			
			// on met à jour le tableau des workflow patterns
			view.majTableOfWorkflows(EUmlWorkflowPattern.patterns);
			
			// on met a jour le texte à coté du bouton (set initial process)
			view.getLabelSetInitialProcess().setText("(uml file)");

			// on affiche le bon tableau des mutations change patterns
			view.majTableOfChangePatterns(ChangePatternFactory.getInstance().getUmlChangePatterns());
		}
	}
}
