package fr.lip6.move.processGenerator.dot;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.jung.JungEdge;
import fr.lip6.move.processGenerator.jung.JungProcess;
import fr.lip6.move.processGenerator.jung.JungVertex;
import fr.lip6.move.processGenerator.uml2.UmlProcess;

/**
 * Cette classe sert à générer le code DOT représentant le process afin d'avoir un visuel graphique.
 * 
 * @author Vincent
 *
 */
public class DotGenerator {
	
	/**
	 * Le process à afficher graphiquement
	 */
	private JungProcess jungProcess;

	private StringBuilder graphic = new StringBuilder();
	private StringBuilder edges = new StringBuilder();

	/**
	 * Constructeur à partir d'un process BPMN.
	 * @param process
	 */
	public DotGenerator(BpmnProcess process) {
		super();
		jungProcess = new JungProcess(process);
	}
	
	/**
	 * Constructeur à partir d'un process UML.
	 * @param process
	 */
	public DotGenerator(UmlProcess process) {
		super();
		jungProcess = new JungProcess(process);
	}
	
	/**
	 * Génère et enregistre le code DOT du process au chemin spécifier en paramètre.
	 * @param location le chemin du répertoire
	 * @param file le nom du fichier
	 * @throws IOException exception levée lorsque la création du fichier n'est pas possible
	 */
	public void generateDot(String location, String file) throws IOException {
		
		this.prepareGraphic();
		this.prepareEdges();
		
		// on construit le fichier
		StringBuilder sb = new StringBuilder("digraph G {\n");
		sb.append(graphic.toString());
		sb.append(edges.toString());
		sb.append("}");
		
		// puis on l'écrit
		Path path = Paths.get(location).resolve(file);
		BufferedWriter br = Files.newBufferedWriter(path, Charset.forName("UTF-8"), new OpenOption[] {StandardOpenOption.WRITE,
			StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING});
		
		br.write(sb.toString());
		br.flush();
		br.close();
	}
	
	/**
	 * Génère la partie graphique des noeuds, couleurs, formes, etc.
	 */
	private void prepareGraphic() {
		graphic.append("	/* graphical part */\n");
		for (JungVertex vertex : jungProcess.getGraph().getVertices()) {
			graphic.append("	");
			graphic.append(vertex.getId());
			graphic.append("[");
			graphic.append(vertex.getType());
			graphic.append(", ");
			graphic.append(vertex.getFillColor());
			graphic.append(", ");
			graphic.append(vertex.getWidth());
			graphic.append(", ");
			graphic.append(vertex.getHeight());
			graphic.append("];\n");
		}
	}
	
	/**
	 * Génère les arcs du process.
	 */
	private void prepareEdges() {
		edges.append("	/* edges */\n");
		for (JungEdge edge : jungProcess.getGraph().getEdges()) {
			edges.append("	");
			edges.append(edge.getSource());
			edges.append("->");
			edges.append(edge.getTarget());
			edges.append(";\n");
		}
	}
}
