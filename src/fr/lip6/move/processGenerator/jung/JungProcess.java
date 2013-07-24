package fr.lip6.move.processGenerator.jung;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.FlowNode;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.bpmn2.utils.BpmnFilter;
import fr.lip6.move.processGenerator.dot.DotGraphicManager;
import fr.lip6.move.processGenerator.uml2.UmlProcess;

/**
 * Représente un process bpmn ({@link BpmnProcess}) ou uml ({@link UmlProcess}). Cette classe est destinée à être
 * manipulée par la librairie JUNG afin d'y appliquer certains algorithmes.
 * 
 * @author Vincent
 * 
 */
public class JungProcess {
	
	/**
	 * Représente le graph du process
	 */
	private Graph<JungVertex, JungEdge> graph;
	
	/**
	 * Associe chaque nom a un {@link JungVertex}
	 */
	private Map<String, JungVertex> allVertices;
	
	/**
	 * Construit un {@link JungProcess} à partir d'un {@link BpmnProcess}.
	 * 
	 * @param process
	 */
	public JungProcess(BpmnProcess bpmnProcess) {
		this(bpmnProcess.getProcess());
	}
	
	/**
	 * Construit un {@link JungProcess} à partir d'un {@link Process} bpmn.
	 * 
	 * @param process
	 */
	public JungProcess(Process process) {
		super();
		graph = new SparseMultigraph<>();
		allVertices = new HashMap<>();
		
		// on parcours chaque élément du process pour y mettre dans un premier temps les FlowNodes
		for (FlowNode element : BpmnFilter.byType(FlowNode.class, process.getFlowElements())) {
			
			String type = DotGraphicManager.instance.getShape(element);
			String color = DotGraphicManager.instance.getFillColor(element);
			String width = DotGraphicManager.instance.getWidth(element);
			String height = DotGraphicManager.instance.getHeight(element);
			
			JungVertex vertex = new JungVertex(element, type, color, width, height);
			allVertices.put(vertex.getId(), vertex);
			graph.addVertex(vertex);
		}
		
		// et on peut enfin mettre les arcs
		for (FlowElement element : process.getFlowElements()) {
			if (element instanceof SequenceFlow) {
				SequenceFlow sequence = (SequenceFlow) element;
				JungVertex v1 = allVertices.get(sequence.getSourceRef().getId());
				JungVertex v2 = allVertices.get(sequence.getTargetRef().getId());
				graph.addEdge(new JungEdge(sequence), v1, v2, EdgeType.DIRECTED);
			}
		}
	}
	
	/**
	 * Constructeur à partir d'un {@link UmlProcess}.
	 * 
	 * @param process
	 */
	public JungProcess(UmlProcess process) {
		this(process.getActivity());
	}
	
	/**
	 * Constructeur à partir d'une {@link Activity} UML2.0.
	 * 
	 * @param process
	 */
	public JungProcess(Activity process) {
		super();
		graph = new SparseMultigraph<>();
		allVertices = new HashMap<>();
		
		// on parcours chaque node du process
		for (ActivityNode node : process.getNodes()) {

			String type = DotGraphicManager.instance.getShape(node);
			String color = DotGraphicManager.instance.getFillColor(node);
			String width = DotGraphicManager.instance.getWidth(node);
			String height = DotGraphicManager.instance.getHeight(node);
			
			JungVertex vertex = new JungVertex(node, type, color, width, height);
			allVertices.put(node.getName(), vertex);
			graph.addVertex(vertex);
		}
		
		// et on peut enfin mettre les arcs
		for (ActivityEdge edge : process.getEdges()) {
			JungVertex v1 = allVertices.get(edge.getSource().getName());
			JungVertex v2 = allVertices.get(edge.getTarget().getName());
			graph.addEdge(new JungEdge(edge), v1, v2, EdgeType.DIRECTED);
		}
	}
	
	public Graph<JungVertex, JungEdge> getGraph() {
		return graph;
	}
	
	public JungVertex getVertex(String name) {
		return allVertices.get(name);
	}
	
	@Override
	public String toString() {
		return "JungManager [graph=" + graph + "]";
	}
}
