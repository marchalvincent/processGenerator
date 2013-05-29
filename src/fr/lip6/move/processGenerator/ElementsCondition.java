package fr.lip6.move.processGenerator;

import java.util.HashMap;
import java.util.Map;

/**
 * Cette classe représente les conditions définies par l'utilisateur
 * quant au nombre d'éléments pour chaque type.
 * @author Vincent
 *
 */
public class ElementsCondition {
	
	public Map<String, Integer> conditions;
	
	public ElementsCondition() {
		super();
		conditions = new HashMap<String, Integer>();
	}
	
	public void put(String key, Integer value) {
		conditions.put(key, value);
	}
}
