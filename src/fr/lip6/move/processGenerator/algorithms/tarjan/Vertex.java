package fr.lip6.move.processGenerator.algorithms.tarjan;

import java.util.ArrayList;
import java.util.List;


public class Vertex {

	private String id;
	private List<Edge> incoming;
	private List<Edge> outgoing;
	private int num;
	private int numAccessible;
	
	public Vertex(String id) {
		super();
		incoming = new ArrayList<>();
		outgoing = new ArrayList<>();
		num = -1;
		numAccessible = -1;
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

	public void addIncoming(Edge edge) {
		incoming.add(edge);
	}
	
	public void addOutgoing(Edge edge) {
		outgoing.add(edge);
	}
	
	public List<Edge> getIncoming() {
		return incoming;
	}
	
	public List<Edge> getOutgoing() {
		return outgoing;
	}

	public void setIndex(int num) {
		this.num = num;
	}

	public void setLowLink(int num) {
		this.numAccessible = num;
	}

	public int getIndex() {
		return num;
	}

	public int getLowLink() {
		return numAccessible;
	}

	@Override
	public String toString() {
		return "Vertex [id=" + id + "]";
	}
}
