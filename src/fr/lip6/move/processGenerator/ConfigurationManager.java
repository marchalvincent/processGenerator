package fr.lip6.move.processGenerator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;


public class ConfigurationManager {

	private final String LOCATION = System.getProperty("user.home") + File.separator + ".processGenerator" + File.separator;
	private final String NODES = "30";
	private final String MARGIN = "10";
	private final String TYPE_FILE = "0";
	// TODO workflow & elements
	private final String POPULATION = "50";
	private final String ELITISM = "5";
	private final String SELECTION_STRATEGY = "1";
	private final String MUTATION = "true";
	// TODO change patterns
	private final String CROSSOVER = "false";
	// TODO termination condition
	private final String SIZE_W = "1";
	private final String ELEMENT_W = "1";
	private final String WORKFLOW_W = "1";
	private final String MANUAL_OCL_W = "1";


	private Properties properties;
	private String path = Utils.configurationFolder + ".properties";

	private static final ConfigurationManager instance = new ConfigurationManager();
	private ConfigurationManager() {
		super();
		try {
			this.properties = new Properties();

			// on créé le répertoire d'arrivée s'il n'existe pas
			File file = new File(Utils.configurationFolder);
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
			this.checkProperty("POPULATION", POPULATION);
			this.checkProperty("ELITISM", ELITISM);
			this.checkProperty("SELECTION_STRATEGY", SELECTION_STRATEGY);
			this.checkProperty("MUTATION", MUTATION);
			this.checkProperty("CROSSOVER", CROSSOVER);
			this.checkProperty("SIZE_W", SIZE_W);
			this.checkProperty("ELEMENT_W", ELEMENT_W);
			this.checkProperty("WORKFLOW_W", WORKFLOW_W);
			this.checkProperty("MANUAL_OCL_W", MANUAL_OCL_W);

			this.store();

		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}

	public static ConfigurationManager getInstance() {
		return instance;
	}

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

	public void setCheckMutation(String value) {
		properties.put("MUTATION", value);
	}

	public boolean isCheckCrossover() {
		return getBoolean("CROSSOVER", false);
	}

	public void setCheckCrossover(String value) {
		properties.put("CROSSOVER", value);
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

}
