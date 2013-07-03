package fr.lip6.move.processGenerator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;


/**
 * Cette classe se charge d'enregistrer chaque données sélectionnées par l'utilisateur sur l'interface 
 * graphique.
 * @author Vincent
 *
 */
public class ConfigurationManager {

	private final String LOCATION = Utils.configurationFolder;
	private final String NODES = "30";
	private final String MARGIN = "10";
	private final String TYPE_FILE = "0";
	
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
	 * Construit le ConfigurationManager et met les valeurs par défaut si elle ne sont pas 
	 * trouvée dans le fichier de configuration.
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
			this.checkProperty("LOCATION", LOCATION);
			this.checkProperty("NODES", NODES);
			this.checkProperty("MARGIN", MARGIN);
			this.checkProperty("TYPE_FILE", TYPE_FILE);

			this.checkProperty("WORKFLOWS", WORKFLOWS);
			this.checkProperty("ELEMENTS", ELEMENTS);
			
			this.checkProperty("POPULATION", POPULATION);
			this.checkProperty("ELITISM", ELITISM);
			this.checkProperty("SELECTION_STRATEGY", SELECTION_STRATEGY);
			this.checkProperty("MUTATION", MUTATION);
			this.checkProperty("CHANGE_PATTERN", CHANGE_PATTERN);
			this.checkProperty("CROSSOVER", CROSSOVER);
			
			this.checkProperty("SOLUTION_FOUND", SOLUTION_FOUND);
			this.checkProperty("DURING_SECONDES", DURING_SECONDES);
			this.checkProperty("NB_SECONDES", NB_SECONDES);
			this.checkProperty("UNTIL_GENERATIONS", UNTIL_GENERATIONS);
			this.checkProperty("NB_GENERATIONS", NB_GENERATIONS);
			this.checkProperty("UNTIL_STAGNATIONS", UNTIL_STAGNATIONS);
			this.checkProperty("NB_STAGNATIONS", NB_STAGNATIONS);

			this.checkProperty("SIZE_W", SIZE_W);
			this.checkProperty("ELEMENT_W", ELEMENT_W);
			this.checkProperty("WORKFLOW_W", WORKFLOW_W);
			this.checkProperty("MANUAL_OCL_W", MANUAL_OCL_W);

			this.store();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Enregistre les données dans un fichier de configuration.
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
	 * @param key la clé.
	 * @param value la valeur associée à la clé.
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
		return properties.getProperty("LOCATION");
	}

	public void setLocation(String value) {
		properties.put("LOCATION", value);
	}

	public int getNbNodes() {
		return getInteger("NODES", 30);
	}

	public void setNbNodes(String value) {
		properties.put("NODES", value);
	}

	public int getMargin() {
		return getInteger("MARGIN", 10);
	}

	public void setMargin(String value) {
		properties.put("MARGIN", value);
	}

	public int getTypeFile() {
		return getInteger("TYPE_FILE", 0);
	}

	public void setTypeFile(String value) {
		properties.put("TYPE_FILE", value);
	}

	public int getPopulation() {
		return getInteger("POPULATION", 50);
	}

	public void setPopulation(String value) {
		properties.put("POPULATION", value);
	}

	public int getElitism() {
		return getInteger("ELITISM", 5);
	}

	public void setElitism(String value) {
		properties.put("ELITISM", value);
	}

	public int getSelectionStrategy() {
		return getInteger("SELECTION_STRATEGY", 1);
	}

	public void setSelectionStrategy(String value) {
		properties.put("SELECTION_STRATEGY", value);
	}

	public boolean isCheckMutation() {
		return getBoolean("MUTATION", true);
	}

	public void setCheckMutation(boolean bool) {
		properties.put("MUTATION", bool + "");
	}

	public boolean isCheckCrossover() {
		return getBoolean("CROSSOVER", false);
	}

	public void setCheckCrossover(boolean bool) {
		properties.put("CROSSOVER", bool + "");
	}

	public int getSizeWeight() {
		return getInteger("SIZE_W", 1);
	}

	public void setSizeWeight(String value) {
		properties.put("SIZE_W", value);
	}

	public int getElementsWeight() {
		return getInteger("ELEMENT_W", 1);
	}

	public void setElementsWeight(String value) {
		properties.put("ELEMENT_W", value);
	}

	public int getWorkflowsWeight() {
		return getInteger("WORKFLOW_W", 1);
	}

	public void setWorkflowsWeight(String value) {
		properties.put("WORKFLOW_W", value);
	}

	public int getManualOCLWeight() {
		return getInteger("MANUAL_OCL_W", 1);
	}

	public void setManualOCLWeight(String value) {
		properties.put("MANUAL_OCL_W", value);
	}
	
	public String getWorkflowsAttributes() {
		return properties.getProperty("WORKFLOWS");
	}

	public void setWorkflowsAttributes(String value) {
		properties.put("WORKFLOWS", value);
	}
	
	public String getElementsAttributes() {
		return properties.getProperty("ELEMENTS");
	}

	public void setElementsAttributes(String value) {
		properties.put("ELEMENTS", value);
	}
	
	public String getChangePatternAttributes() {
		return properties.getProperty("CHANGE_PATTERN");
	}

	public void setChangePatternAttributes(String value) {
		properties.put("CHANGE_PATTERN", value);
	}
	
	public boolean isSolutionFound() {
		return getBoolean("SOLUTION_FOUND", false);
	}
	
	public void setSolutionFound(boolean bool) {
		properties.put("SOLUTION_FOUND", bool + "");
	}
	
	public boolean isDuringSecondes() {
		return getBoolean("DURING_SECONDES", false);
	}
	
	public void setDuringSecondes(boolean bool) {
		properties.put("DURING_SECONDES", bool + "");
	}
	
	public boolean isUntilGenerations() {
		return getBoolean("UNTIL_GENERATIONS", false);
	}
	
	public void setUntilGenerations(boolean bool) {
		properties.put("UNTIL_GENERATIONS", bool + "");
	}
	
	public boolean isUntilStagnations() {
		return getBoolean("UNTIL_STAGNATIONS", false);
	}
	
	public void setUntilStagnations(boolean bool) {
		properties.put("UNTIL_STAGNATIONS", bool + "");
	}
	
	public int getNbSecondes() {
		return getInteger("NB_SECONDES", 60);
	}
	
	public void setNbSecondes(int nb) {
		properties.put("NB_SECONDES", nb + "");
	}
	
	public int getNbGenerations() {
		return getInteger("NB_GENERATIONS", 100);
	}
	
	public void setNbGenerations(int nb) {
		properties.put("NB_GENERATIONS", nb + "");
	}
	
	public int getNbStagnations() {
		return getInteger("NB_STAGNATIONS", 100);
	}
	
	public void setNbStagnations(int nb) {
		properties.put("NB_STAGNATIONS", nb + "");
	}
}
