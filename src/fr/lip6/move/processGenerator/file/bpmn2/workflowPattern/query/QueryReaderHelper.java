package fr.lip6.move.processGenerator.file.bpmn2.workflowPattern.query;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import fr.lip6.move.processGenerator.file.bpmn2.BpmnException;

/**
 * Classe helper permettant de lire facilement dans un fichier.
 * @author Vincent
 *
 */
public class QueryReaderHelper {
	
	public static final String directory = "src/fr/lip6/move/processGenerator/file/bpmn2/workflowPattern/query";
	
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
			throw new BpmnException("Attention, impossible de lire dans le fichier de la requete " + nameFile + ".");
		}
		
		StringBuilder query = new StringBuilder();
		for (String s : lines) {
			query.append(s.replace("	", "") + " ");
		}
		return query.toString();
	}
}