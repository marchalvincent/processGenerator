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
	
	/*
	 * Toutes ces constantes sont utilisées pour la construction et la manipulation des Tree.
	 */
	public static final String NAME_KEY = "name";
	public static final String DEFAULT_CHECK = "0";
	public static final String QUANTITY_KEY = "quantityKey";
	public static final String DEFAULT_QUANTITY = "3";
	public static final String NUMBER_KEY = "numberKey";
	public static final String DEFAULT_NUMBER = "1";
	public static final String WEIGHT_KEY = "weightKey";
	public static final String DEFAULT_WEIGHT = "1";
	
	public static final String projectPath = "/Documents/workspace/processGenerator/";
	public static final String bugPathBpmn = projectPath + "gen/bug.bpmn";
	public static final String bugPathUml = projectPath + "gen/bug.uml";
	
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
