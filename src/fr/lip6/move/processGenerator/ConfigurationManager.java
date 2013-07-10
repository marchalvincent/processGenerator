package fr.lip6.move.processGenerator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * Cette classe se charge d'enregistrer chaque données sélectionnées par l'utilisateur sur l'interface graphique.
 * 
 * @author Vincent
 * 
 */
public class ConfigurationManager {

	private final String TYPE_FILE = "0";
	private final String LOCATION = Utils.configurationFolder;
	private final String NODES = "30";
	private final String MARGIN = "10";
	
	private final String WORKFLOWS = "";
	private final String ELEMENTS = "";
	
	private final String POPULATION = "50";
	private final String ELITISM = "5";
	private final String SELECTION_STRATEGY = "1";
	private final String MUTATION = "true";
	private final String CHANGE_PATTERN = "";
	private final String CROSSOVER = "false";
	
	private final String SOLUTION_FOUND = "false";
	private final String DURING_SECONDES = "false";
	private final String NB_SECONDES = "60";
	private final String UNTIL_GENERATIONS = "false";
	private final String NB_GENERATIONS = "100";
	private final String UNTIL_STAGNATIONS = "false";
	private final String NB_STAGNATIONS = "100";
	
	private final String SIZE_W = "1";
	private final String ELEMENT_W = "1";
	private final String WORKFLOW_W = "1";
	private final String MANUAL_OCL_W = "1";
	
	private Properties properties;
	private String path = Utils.configurationFolder + ".processGeneratorProperties";
	
	public static final ConfigurationManager instance = new ConfigurationManager();
	
	/**
	 * Construit le ConfigurationManager et met les valeurs par défaut si elle ne sont pas trouvée dans le fichier de
	 * configuration.
	 */
	private ConfigurationManager() {
		super();
		try {
			this.properties = new Properties();
			
			// on créé le répertoire d'arrivée s'il n'existe pas
			File file = new File(LOCATION);
			if (!file.exists())
				file.mkdir();
			file = new File(path);
			if (!file.exists())
				file.createNewFile();
			
			// on charge les propriétés qui sont présentes
			InputStream in = new FileInputStream(path);
			properties.load(in);
			in.close();
			
			// on créé les valeurs par défaut s'il elle n'y sont pas
			this.checkProperty("TYPE_FILE", TYPE_FILE);
			
			for (int i = 0; i < 2; i++) {
				
				this.checkProperty("LOCATION" + i, LOCATION);
				this.checkProperty("NODES" + i, NODES);
				this.checkProperty("MARGIN" + i, MARGIN);
				
				this.checkProperty("WORKFLOWS" + i, WORKFLOWS);
				this.checkProperty("ELEMENTS" + i, ELEMENTS);
				
				this.checkProperty("POPULATION" + i, POPULATION);
				this.checkProperty("ELITISM" + i, ELITISM);
				this.checkProperty("SELECTION_STRATEGY" + i, SELECTION_STRATEGY);
				this.checkProperty("MUTATION" + i, MUTATION);
				this.checkProperty("CHANGE_PATTERN" + i, CHANGE_PATTERN);
				this.checkProperty("CROSSOVER" + i, CROSSOVER);
				
				this.checkProperty("SOLUTION_FOUND" + i, SOLUTION_FOUND);
				this.checkProperty("DURING_SECONDES" + i, DURING_SECONDES);
				this.checkProperty("NB_SECONDES" + i, NB_SECONDES);
				this.checkProperty("UNTIL_GENERATIONS" + i, UNTIL_GENERATIONS);
				this.checkProperty("NB_GENERATIONS" + i, NB_GENERATIONS);
				this.checkProperty("UNTIL_STAGNATIONS" + i, UNTIL_STAGNATIONS);
				this.checkProperty("NB_STAGNATIONS" + i, NB_STAGNATIONS);
				
				this.checkProperty("SIZE_W" + i, SIZE_W);
				this.checkProperty("ELEMENT_W" + i, ELEMENT_W);
				this.checkProperty("WORKFLOW_W" + i, WORKFLOW_W);
				this.checkProperty("MANUAL_OCL_W" + i, MANUAL_OCL_W);
			}
			
			this.store();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Enregistre les données dans un fichier de configuration.
	 * 
	 * @throws IOException
	 */
	public void store() throws IOException {
		OutputStream out = null;
		try {
			out = new FileOutputStream(new File(path));
			properties.store(out, null);
		} finally {
			if (out != null)
				out.close();
		}
	}
	
	/**
	 * Initialise la clé {@code key} avec la valeur {@code value} si elle n'existe pas.
	 * 
	 * @param key
	 *            la clé.
	 * @param value
	 *            la valeur associée à la clé.
	 */
	private void checkProperty(String key, String value) {
		if (this.properties.getProperty(key) == null)
			this.properties.put(key, value);
	}
	
	private int getInteger(String key, int defaut) {
		try {
			return Integer.parseInt(properties.getProperty(key));
		} catch (Exception e) {
			System.err.println("ConfigurationManager : Impossible to parse integer. Key : " + key);
			return defaut;
		}
	}
	
	private boolean getBoolean(String key, boolean defaut) {
		try {
			return Boolean.parseBoolean(properties.getProperty(key));
		} catch (Exception e) {
			System.err.println("ConfigurationManager : Impossible to parse boolean. Key : " + key);
			return defaut;
		}
	}
	
	public String getLocation() {
		return properties.getProperty("LOCATION" + getTypeFile());
	}
	
	public void setLocation(String value) {
		properties.put("LOCATION" + getTypeFile(), value);
	}
	
	public int getNbNodes() {
		return getInteger("NODES" + getTypeFile(), 30);
	}
	
	public void setNbNodes(String value) {
		properties.put("NODES" + getTypeFile(), value);
	}
	
	public int getMargin() {
		return getInteger("MARGIN" + getTypeFile(), 10);
	}
	
	public void setMargin(String value) {
		properties.put("MARGIN" + getTypeFile(), value);
	}
	
	public int getTypeFile() {
		return getInteger("TYPE_FILE", 0);
	}
	
	public void setTypeFile(String value) {
		properties.put("TYPE_FILE", value);
	}
	
	public int getPopulation() {
		return getInteger("POPULATION" + getTypeFile(), 50);
	}
	
	public void setPopulation(String value) {
		properties.put("POPULATION" + getTypeFile(), value);
	}
	
	public int getElitism() {
		return getInteger("ELITISM" + getTypeFile(), 5);
	}
	
	public void setElitism(String value) {
		properties.put("ELITISM" + getTypeFile(), value);
	}
	
	public int getSelectionStrategy() {
		return getInteger("SELECTION_STRATEGY" + getTypeFile(), 1);
	}
	
	public void setSelectionStrategy(String value) {
		properties.put("SELECTION_STRATEGY" + getTypeFile(), value);
	}
	
	public boolean isCheckMutation() {
		return getBoolean("MUTATION" + getTypeFile(), true);
	}
	
	public void setCheckMutation(boolean bool) {
		properties.put("MUTATION" + getTypeFile(), bool + "");
	}
	
	public boolean isCheckCrossover() {
		return getBoolean("CROSSOVER" + getTypeFile(), false);
	}
	
	public void setCheckCrossover(boolean bool) {
		properties.put("CROSSOVER" + getTypeFile(), bool + "");
	}
	
	public int getSizeWeight() {
		return getInteger("SIZE_W" + getTypeFile(), 1);
	}
	
	public void setSizeWeight(String value) {
		properties.put("SIZE_W" + getTypeFile(), value);
	}
	
	public int getElementsWeight() {
		return getInteger("ELEMENT_W" + getTypeFile(), 1);
	}
	
	public void setElementsWeight(String value) {
		properties.put("ELEMENT_W" + getTypeFile(), value);
	}
	
	public int getWorkflowsWeight() {
		return getInteger("WORKFLOW_W" + getTypeFile(), 1);
	}
	
	public void setWorkflowsWeight(String value) {
		properties.put("WORKFLOW_W" + getTypeFile(), value);
	}
	
	public int getManualOclWeight() {
		return getInteger("MANUAL_OCL_W" + getTypeFile(), 1);
	}
	
	public void setManualOCLWeight(String value) {
		properties.put("MANUAL_OCL_W" + getTypeFile(), value);
	}
	
	public String getWorkflowsAttributes() {
		return properties.getProperty("WORKFLOWS" + getTypeFile());
	}
	
	public void setWorkflowsAttributes(String value) {
		properties.put("WORKFLOWS" + getTypeFile(), value);
	}
	
	public String getElementsAttributes() {
		return properties.getProperty("ELEMENTS" + getTypeFile());
	}
	
	public void setElementsAttributes(String value) {
		properties.put("ELEMENTS" + getTypeFile(), value);
	}
	
	public String getChangePatternAttributes() {
		return properties.getProperty("CHANGE_PATTERN" + getTypeFile());
	}
	
	public void setChangePatternAttributes(String value) {
		properties.put("CHANGE_PATTERN" + getTypeFile(), value);
	}
	
	public boolean isUntilSolutionFound() {
		return getBoolean("SOLUTION_FOUND" + getTypeFile(), false);
	}
	
	public void setSolutionFound(boolean bool) {
		properties.put("SOLUTION_FOUND" + getTypeFile(), bool + "");
	}
	
	public boolean isDuringSecondes() {
		return getBoolean("DURING_SECONDES" + getTypeFile(), false);
	}
	
	public void setDuringSecondes(boolean bool) {
		properties.put("DURING_SECONDES" + getTypeFile(), bool + "");
	}
	
	public boolean isUntilGenerations() {
		return getBoolean("UNTIL_GENERATIONS" + getTypeFile(), false);
	}
	
	public void setUntilGenerations(boolean bool) {
		properties.put("UNTIL_GENERATIONS" + getTypeFile(), bool + "");
	}
	
	public boolean isUntilStagnations() {
		return getBoolean("UNTIL_STAGNATIONS" + getTypeFile(), false);
	}
	
	public void setUntilStagnations(boolean bool) {
		properties.put("UNTIL_STAGNATIONS" + getTypeFile(), bool + "");
	}
	
	public int getNbSecondes() {
		return getInteger("NB_SECONDES" + getTypeFile(), 60);
	}
	
	public void setNbSecondes(int nb) {
		properties.put("NB_SECONDES" + getTypeFile(), nb + "");
	}
	
	public int getNbGenerations() {
		return getInteger("NB_GENERATIONS" + getTypeFile(), 100);
	}
	
	public void setNbGenerations(int nb) {
		properties.put("NB_GENERATIONS" + getTypeFile(), nb + "");
	}
	
	public int getNbStagnations() {
		return getInteger("NB_STAGNATIONS" + getTypeFile(), 100);
	}
	
	public void setNbStagnations(int nb) {
		properties.put("NB_STAGNATIONS" + getTypeFile(), nb + "");
	}
}
