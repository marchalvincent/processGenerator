package fr.lip6.move.processGenerator.views;

import org.eclipse.swt.widgets.Label;

/**
 * Ajoute le texte dans le champ destiné aux messages utilisateur.
 * 
 * @author Vincent
 * 
 */
public class RunnablePrintAppendView implements Runnable {
	
	private Label label;
	private String text;
	
	public RunnablePrintAppendView(Label label, String text) {
		super();
		this.label = label;
		this.text = text;
	}
	
	@Override
	public void run() {
		label.setText(label.getText() + "\n" + text);
		label.getParent().layout(true);
	}
}
