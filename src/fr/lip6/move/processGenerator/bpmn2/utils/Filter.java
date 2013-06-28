package fr.lip6.move.processGenerator.bpmn2.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpmn2.Gateway;
import org.eclipse.bpmn2.GatewayDirection;


public class Filter<T> {

	public static <T> List<T> byType(Class<T> clazz, List<?> elements) {
		List<T> results = new ArrayList<T>();
		for (Object o : elements)
			if (clazz.isInstance(o))
				results.add(clazz.cast(o));
		return results;
	}
	
	public static <T> List<T> byType(Class<T> clazz, List<?> elements, GatewayDirection direction) {
		List<T> results = new ArrayList<T>();
		for (Object o: elements)
			if (clazz.isInstance(o) && o instanceof Gateway && ((Gateway) o).getGatewayDirection().equals(direction))
				results.add(clazz.cast(o));
		return results;
	}
	
	public static List<Gateway> gatewayByType(List<Class<? extends Gateway>> classes, List<?> elements) {
		List<Gateway> results = new ArrayList<Gateway>();
		for (Object o: elements) {
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
	
	public static List<Gateway> gatewayByType(List<Class<? extends Gateway>> classes, List<?> elements, GatewayDirection direction) {
		List<Gateway> results = new ArrayList<Gateway>();
		for (Object o: elements) {
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
