package fr.lip6.move.processGenerator.views;

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;


public class ModifyTextInTable implements ModifyListener {

	private TableItem item;
	private Text text;
	private int indexOfTable;
	
	public ModifyTextInTable(TableItem item, Text text, int indexOfTable) {
		super();
		this.item = item;
		this.text = text;
		this.indexOfTable = indexOfTable;
	}
	
	@Override
	public void modifyText(ModifyEvent me) {
		// on n'accepte que les nombres
		try {
			Integer.parseInt(text.getText());
		} catch (Exception ex) {
			text.setText("1");
		}
		// puis on met à jour la valeur du Tableitem
		item.setText(indexOfTable, text.getText());
	}
}
