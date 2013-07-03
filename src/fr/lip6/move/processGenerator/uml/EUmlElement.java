package fr.lip6.move.processGenerator.uml;

import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.DecisionNode;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.FinalNode;
import org.eclipse.uml2.uml.ForkNode;
import org.eclipse.uml2.uml.InitialNode;
import org.eclipse.uml2.uml.JoinNode;
import org.eclipse.uml2.uml.MergeNode;

import fr.lip6.move.processGenerator.IEnumElement;

/**
 * Représente les éléments que peut sélectionner l'utilisateur pour spécifier des contraintes
 * structurelles.
 * @author Vincent
 *
 */
public enum EUmlElement implements IEnumElement {
	
	INITIAL_NODE(InitialNode.class),
	FINAL_NODE(FinalNode.class),
	ACTIVITY_NODE(ActivityNode.class),
	ACTIVITY_EDGE(ActivityEdge.class),
	FORK_NODE(ForkNode.class),
	JOIN_NODE(JoinNode.class),
	DECISION_NODE(DecisionNode.class),
	MERGE_NODE(MergeNode.class);

	private Class<? extends Element> clazz;
	private EUmlElement(Class<? extends Element> clazz) {
		this.clazz = clazz;
	}
	
	@Override
	public String toString() {
		return this.clazz.getSimpleName();
	}
}
