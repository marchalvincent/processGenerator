package fr.lip6.move.processGenerator.algorithms.tarjan;


public class Edge {
	
	private Vertex source;
	private Vertex target;
	
	public Edge() {
		super();
	}

	public Vertex getSource() {
		return source;
	}

	public void setSource(Vertex source) {
		this.source = source;
	}

	public Vertex getTarget() {
		return target;
	}

	public void setTarget(Vertex target) {
		this.target = target;
	}
}
