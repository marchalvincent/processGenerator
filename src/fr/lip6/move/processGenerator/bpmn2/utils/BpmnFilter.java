package fr.lip6.move.processGenerator.bpmn2.utils;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.bpmn2.Gateway;
import org.eclipse.bpmn2.GatewayDirection;
import fr.lip6.move.processGenerator.Filter;

/**
 * Cette classe permet de simplifier les filtres de liste par rapport aux classes bpmn.
 * 
 * @author Vincent
 * 
 */
public class BpmnFilter extends Filter {
	
	/**
	 * Filtre la liste selon la classe mais aussi selon une {@link GatewayDirection}. Cette méthode n'est valable que
	 * lorsqu'on lui spécifie une classe héritant de {@link Gateway}.
	 * 
	 * @param clazz
	 *            la classe dont on veut garder les éléments.
	 * @param elements
	 *            les éléments à filtrer.
	 * @param direction
	 *            la {@link GatewayDirection} que doit avoir les éléments à garder.
	 * @return {@link List}.
	 */
	public static <T> List<T> byType(Class<T> clazz, List<?> elements, GatewayDirection direction) {
		List<T> results = new ArrayList<T>();
		for (Object o : elements)
			if (clazz.isInstance(o) && o instanceof Gateway && ((Gateway) o).getGatewayDirection().equals(direction))
				results.add(clazz.cast(o));
		return results;
	}
	
	/**
	 * Filtre des éléments en ne gardant que ceux qui sont d'au moins une des classes passées en paramètre.
	 * 
	 * @param classes
	 *            une liste de classe.
	 * @param elements
	 *            les éléments à filter.
	 * @return {@link List}.
	 */
	public static List<Gateway> gatewayByType(List<Class<? extends Gateway>> classes, List<?> elements) {
		List<Gateway> results = new ArrayList<Gateway>();
		for (Object o : elements) {
			for (Class<? extends Gateway> clazz : classes) {
				if (clazz.isInstance(o)) {
					results.add(Gateway.class.cast(o));
					// sa sert plus a rien de tester les classes d'après
					break;
				}
			}
		}
		return results;
	}
	
	/**
	 * Filtre des éléments en ne gardant que ceux qui héritent d'au moins une des classes passées en paramètre et dont
	 * la direction est également passée en paramètre.
	 * 
	 * @param classes
	 *            une liste de classe.
	 * @param elements
	 *            les éléments à filter.
	 * @param direction
	 *            la {@link GatewayDirection} que doivent respecter les candidats.
	 * @return {@link List}.
	 */
	public static List<Gateway> gatewayByType(List<Class<? extends Gateway>> classes, List<?> elements, GatewayDirection direction) {
		List<Gateway> results = new ArrayList<Gateway>();
		for (Object o : elements) {
			for (Class<? extends Gateway> clazz : classes) {
				if (clazz.isInstance(o)) {
					if (((Gateway) o).getGatewayDirection().equals(direction)) {
						results.add(Gateway.class.cast(o));
					}
					// sa sert plus a rien de tester les classes d'après
					break;
				}
			}
		}
		return results;
	}
}
