package fr.lip6.move.processGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * Cette classe est un filtre utilitaire.
 * @author Vincent
 *
 */
public class Filter {

	/**
	 * Filtre la liste selon la classe passée en paramètre.
	 * @param clazz la classe dont on veut garder les éléments.
	 * @param elements la liste à filtrer.
	 * @return {@link List}.
	 */
	public static <T> List<T> byType(Class<T> clazz, List<?> elements) {
		List<T> results = new ArrayList<T>();
		for (Object o : elements)
			if (clazz.isInstance(o))
				results.add(clazz.cast(o));
		return results;
	}
}
