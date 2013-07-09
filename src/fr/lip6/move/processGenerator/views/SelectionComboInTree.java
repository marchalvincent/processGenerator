package fr.lip6.move.processGenerator.views;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.TreeItem;
import fr.lip6.move.processGenerator.Utils;

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
	
	public SelectionComboInTree(TreeItem item, Combo combo) {
		super();
		this.item = item;
		this.combo = combo;
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		item.setData(Utils.QUANTITY_KEY, combo.getText());
	}
}
