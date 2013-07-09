package fr.lip6.move.processGenerator.views;

import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.TreeItem;

/**
 * Ce listener est déclenché lorsque l'utilisateur change une valeur dans un tableau. Il va permettre de forcer
 * l'utilisateur à mettre un entier dans le champ associé.
 * 
 * @author Vincent
 * 
 */
public class ModifyTextInTree implements FocusListener {
	
	private TreeItem item;
	private TableItem table;
	private Text text;
	private int indexOfTable;
	private String keyData;
	
	public ModifyTextInTree(TreeItem item, Text text, String keyData) {
		super();
		this.item = item;
		this.text = text;
		this.keyData = keyData;
	}
	
	/**
	 * Temporaire, les change patterns vont être dans un arbre eux aussi (cf. constructeur précédent)
	 * @param tableItem
	 * @param text2
	 * @param indexOfTable2
	 */
	public ModifyTextInTree(TableItem tableItem, Text text, int indexOfTable) {
		super();
		this.table = tableItem;
		this.text = text;
		this.indexOfTable = indexOfTable;
	}

	@Override
	public void focusLost(FocusEvent me) {
		// on n'accepte que les nombres
		try {
			Integer.parseInt(text.getText());
		} catch (Exception ex) {
			text.setText("1");
		}
		// puis on met à jour la valeur du Tableitem
		if (item != null)
			item.setData(keyData, text.getText());
		else
			table.setText(indexOfTable, text.getText());
	}

	@Override
	public void focusGained(FocusEvent e) {}
}
