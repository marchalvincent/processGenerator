package fr.lip6.move.processGenerator.algorithms.tarjan;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class TarjanAlgorithm {
	
	private int index;
	private Stack<Vertex> s;
	private List<Vertex> partition;
	
	public TarjanAlgorithm() {
		super();
		index = 0;
		s = new Stack<Vertex>();
		partition = new ArrayList<>();
	}
	

	public List<Vertex> tarjan(Graph g) {
		for (Vertex v : g.getVertices()) {
			if (v.getIndex() == -1)
				strongConnect(g, v);
		}
		return partition;
	}
	
	private void strongConnect(Graph g, Vertex v) {
		v.setIndex(index);
		v.setLowLink(index);
		index++;
		s.push(v);
		
		// parcours récursif
		for (Edge e : v.getOutgoing()) {
			Vertex w = e.getTarget();
			if (w.getIndex() == -1) {
				strongConnect(g, w);
				v.setLowLink(min(v.getLowLink(), w.getLowLink()));
			}
			else if (s.contains(w)) {
				v.setLowLink(min(v.getLowLink(), w.getIndex()));
			}
		}
		
		if (v.getLowLink() == v.getIndex()) {
			// C'est une racine, calcule la composante fortement connexe associée
			List<Vertex> stronglyConnectedComponent = new ArrayList<>();
			Vertex w = null;
			do {
				w = s.pop();
				stronglyConnectedComponent.add(w);
			} while (w != v);
			partition.addAll(stronglyConnectedComponent);
		}
	}
	
	private int min (int a, int b) {
		if (a < b)
			return a;
		return b;
	}
}
