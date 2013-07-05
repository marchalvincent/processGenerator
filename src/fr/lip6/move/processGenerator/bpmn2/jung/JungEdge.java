package fr.lip6.move.processGenerator.bpmn2.jung;

import org.eclipse.bpmn2.SequenceFlow;

/**
 * Représente un arc bpmn (SequenceFlow). Cette classe est destinée à être manipulée par la librairie JUNG afin d'y
 * appliquer certains algorithmes.
 * 
 * @author Vincent
 * 
 */
public class JungEdge {
	
	private String id;
	
	public JungEdge(SequenceFlow sequence) {
		super();
		this.id = sequence.getId();
	}
	
	@Override
	public String toString() {
		return "JungEdge[id=" + id + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JungEdge other = (JungEdge) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
