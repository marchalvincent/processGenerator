package fr.lip6.move.processGenerator.structuralConstraint.bpmn.query;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import fr.lip6.move.processGenerator.bpmn2.BpmnException;

/**
 * Classe helper permettant de lire facilement dans un fichier.
 * @author Vincent
 *
 */
public class BpmnQueryReaderHelper {
	
	public static final String directory = "configuration/fr.lip6.move.processGenerator/bpmn.query";
	
	/**
	 * Renvoie la requête lue dans le fichier dont le nom est passé en paramètre.
	 * @param nameFile String le nom du fichier à lire.
	 * @return String la requête.
	 * @throws BpmnException Dans le cas où le fichier est inexistant.
	 */
	public static String read(String nameFile) throws BpmnException {

		List<String> lines = new ArrayList<String>();
		Path sequencePath = Paths.get(directory, nameFile);
		try {
			lines.addAll(Files.readAllLines(sequencePath, Charset.forName("UTF-8")));
		} catch (IOException e) {
			throw new BpmnException("Warning, impossible to read the file of the following query : " + nameFile + ".");
		}
		
		StringBuilder query = new StringBuilder();
		for (String s : lines) {
			query.append(s.replace("	", "") + " ");
		}
		return query.toString();
	}
}
