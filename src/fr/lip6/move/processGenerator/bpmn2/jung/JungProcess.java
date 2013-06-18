package fr.lip6.move.processGenerator.bpmn2.jung;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.FlowNode;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.SequenceFlow;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;



public class JungProcess {
	
	private Graph<JungNode, JungEdge> graph;
	private Map<String, JungNode> allVertices;
	
	public JungProcess(BpmnProcess bpmnProcess) {
		this(bpmnProcess.getProcess());
	}
	
	public JungProcess(Process process) {
		super();
		graph = new SparseMultigraph<JungNode, JungEdge>();
		allVertices = new HashMap<String, JungNode>();
		
		// on parcours chaque élément du process pour y mettre dans un premier temps les FlowNodes
		for (FlowElement element : process.getFlowElements()) {
			if (element instanceof FlowNode) {
				JungNode vertex = new JungNode((FlowNode) element);
				allVertices.put(vertex.getId(), vertex);
				graph.addVertex(vertex);
			}
		}
		
		// et on peut enfin mettre les arcs
		for (FlowElement element : process.getFlowElements()) {
			if (element instanceof SequenceFlow) {
				
				SequenceFlow sequence = (SequenceFlow) element;
				JungNode v1 = allVertices.get(sequence.getSourceRef().getId());
				JungNode v2 = allVertices.get(sequence.getTargetRef().getId());
				graph.addEdge(new JungEdge(sequence), v1, v2, EdgeType.DIRECTED);
			}
		}
	}
	
	public Graph<JungNode, JungEdge> getGraph() {
		return graph;
	}
	
	public JungNode getVertex(String name) {
		return allVertices.get(name);
	}

	@Override
	public String toString() {
		return "JungManager [graph=" + graph + "]";
	}
}
