package fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.changePattern;

import org.eclipse.bpmn2.SequenceFlow;

/**
 * Représente un SESE (Single Entry Single Exit) pour les diagramme représentés par BPMN2.0.
 * Un SESE est un morceau de diagramme ne contenant qu'une seule entrée et qu'une seule sortie.
 * @author Vincent
 *
 */
public class SingleEntrySingleExit {

	private SequenceFlow first, last;
	
	public SingleEntrySingleExit(SequenceFlow first, SequenceFlow last) {
		super();
		this.first = first;
		this.last = last;
	}
	
	
	public SequenceFlow getLast() {
		return last;
	}

	public SequenceFlow getFirst() {
		return first;
	}


	/**
	 * <p>Un SESE complexe est suceptible de contenir d'autres SESE.</p> 
	 * <p>Par exemple, un morceau de diagramme contenant en entrée un "fork" et en sortie un "join" est un SESE.
	 * Mais ce morceau contiendra d'autres SESEs, dont chaque chemin en font partie.</p>
	 * @return boolean true si le SESE est complexe. False sinon.
	 */
	public boolean isComplexe() {
		return first.getTargetRef() != last.getSourceRef();
	}


	@Override
	public String toString() {
		return "SingleEntrySingleExit [first=" + first.getId() + ", last=" + last.getId() + "]";
	}
}
