package fr.lip6.move.processGenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import fr.lip6.move.processGenerator.ga.IChangePattern;

/**
 * Cette classe contient des informations utiles à l'ensemble du projet.
 * 
 * @author Vincent
 * 
 */
public class Utils {
	
	public static final String configurationFolder = System.getProperty("user.home") + File.separator;
	public static final boolean DEBUG = true;
	public static final boolean BENCH = false;
	
	public static final String DEFAULT_CHECK = "0";
	public static final String DEFAULT_QUANTITY = "3";
	public static final String DEFAULT_NUMBER = "1";
	public static final String DEFAULT_WEIGHT = "1";
	
	public static Utils instance = new Utils();
	
	private Utils() {}
	
	@SuppressWarnings("unchecked")
	public <T> List<IChangePattern<T>> castChangePattern(List<IChangePattern<?>> changePatterns, Class<T> clazz) {
		List<IChangePattern<T>> list = new ArrayList<>();
		for (IChangePattern<?> iChangePattern : changePatterns) {
			try {
				list.add((IChangePattern<T>) iChangePattern);
			} catch (Exception e) {
				System.err.println("Cannot cast change pattern with the generic type : " + clazz.getSimpleName());
				e.printStackTrace();
			}
		}
		return list;
	}
}
