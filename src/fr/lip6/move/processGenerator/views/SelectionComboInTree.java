package fr.lip6.move.processGenerator.views;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.TreeItem;

/**
 * Cet adapteur est déclenché lorsque l'utilisateur modifie la valeur d'un combobox dans un tableau. Afin de garder la
 * valeur seléctionnée, on l'écrit dans un champ caché du tableau.
 * 
 * @author Vincent
 * 
 */
public class SelectionComboInTree extends SelectionAdapter {
	
	private TreeItem item;
	private Combo combo;
	private int indexOfTree;
	
	public SelectionComboInTree(TreeItem item, Combo combo, int indexOfTree) {
		super();
		this.item = item;
		this.combo = combo;
		this.indexOfTree = indexOfTree;
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		item.setText(indexOfTree, combo.getText());
	}
}
