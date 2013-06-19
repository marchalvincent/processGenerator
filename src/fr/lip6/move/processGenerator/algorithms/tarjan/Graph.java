package fr.lip6.move.processGenerator.algorithms.tarjan;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.eclipse.bpmn2.FlowNode;
import org.eclipse.bpmn2.SequenceFlow;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.bpmn2.utils.Filter;


public class Graph {

	private Map<String, Vertex> vertices;
	private List<Edge> edges;
	
	public Graph(BpmnProcess process) {
		super();
		vertices = new HashMap<>();
		edges = new ArrayList<>();
		
		// on parcours chaque élément du process pour y mettre dans un premier temps les FlowNodes
		List<FlowNode> list = Filter.byType(FlowNode.class, process.getProcess().getFlowElements());
		for (FlowNode element : list) {
			Vertex vertex = new Vertex(element.getId());
			vertices.put(vertex.getId(), vertex);
		}
		
		// et on peut enfin mettre les arcs
		List<SequenceFlow> list2 = Filter.byType(SequenceFlow.class, process.getProcess().getFlowElements());
		for (SequenceFlow sequence : list2) {
			Vertex v1 = vertices.get(sequence.getSourceRef().getId());
			Vertex v2 = vertices.get(sequence.getTargetRef().getId());
			this.addEdge(new Edge(), v1, v2);
		}
	}
	
	public void addVertex(Vertex v) {
		vertices.put(v.getId(), v);
	}
	
	public void addEdge(Edge e, Vertex source, Vertex target) {
		edges.add(e);
		e.setSource(source);
		e.setTarget(target);
		source.addOutgoing(e);
		target.addIncoming(e);
	}

	public Collection<Vertex> getVertices() {
		return vertices.values();
	}

	public List<Edge> getEdges() {
		return edges;
	}
}
