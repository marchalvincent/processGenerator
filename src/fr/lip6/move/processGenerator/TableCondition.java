package fr.lip6.move.processGenerator;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.swt.widgets.Table;

/**
 * Cette classe représente les conditions définies par l'utilisateur
 * quant au nombre d'éléments pour chaque type.
 * @author Vincent
 *
 */
public class TableCondition {
	
	public List<Condition> conditions;
	
	public TableCondition(Table table) {
		super();
		conditions = new ArrayList<Condition>();
		// TODO parser la Table pour récup les infos
		
	}
}
