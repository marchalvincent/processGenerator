package fr.lip6.move.processGenerator.views;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.TableItem;


public class SelectionComboInTable extends SelectionAdapter {
	
	private TableItem item;
	private Combo combo;
	
	public SelectionComboInTable(TableItem item, Combo combo) {
		super();
		this.item = item;
		this.combo = combo;
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		item.setText(2, combo.getText());
	}
}