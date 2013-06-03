package fr.lip6.move.processGenerator.views;

import org.eclipse.swt.widgets.Label;


public class RunnablePrintView implements Runnable {

	private Label label;
	private String text;
	
	public RunnablePrintView(Label label, String text) {
		super();
		this.label = label;
		this.text = text;
	}
	
	@Override
	public void run() {
		label.setText(text);
		label.getParent().layout(true);
	}
}
