package fr.lip6.move.processGenerator.uml2;

import org.eclipse.uml2.uml.ActivityFinalNode;
import org.eclipse.uml2.uml.ControlNode;
import org.eclipse.uml2.uml.DecisionNode;
import org.eclipse.uml2.uml.Element;
import org.eclipse.uml2.uml.ExecutableNode;
import org.eclipse.uml2.uml.ForkNode;
import org.eclipse.uml2.uml.InitialNode;
import org.eclipse.uml2.uml.JoinNode;
import org.eclipse.uml2.uml.MergeNode;
import org.eclipse.uml2.uml.OpaqueAction;
import fr.lip6.move.processGenerator.IEnumElement;
import fr.lip6.move.processGenerator.IHierarchicalEnum;

/**
 * Représente les éléments que peut sélectionner l'utilisateur pour spécifier des contraintes structurelles.
 * 
 * @author Vincent
 * 
 */
public enum EUmlElement implements IEnumElement {
	
	CONTROL_NODE(null, ControlNode.class),
	INITIAL_NODE(CONTROL_NODE, InitialNode.class),
	ACTIVITY_FINAL_NODE(CONTROL_NODE, ActivityFinalNode.class),
	FORK_NODE(CONTROL_NODE, ForkNode.class),
	JOIN_NODE(CONTROL_NODE, JoinNode.class),
	DECISION_NODE(CONTROL_NODE, DecisionNode.class),
	MERGE_NODE(CONTROL_NODE, MergeNode.class),
	
	EXECUTABLE_NODE(null, ExecutableNode.class),
	OPAQUE_ACTION(EXECUTABLE_NODE, OpaqueAction.class);
	
	private IHierarchicalEnum parent;
	private Class<? extends Element> clazz;
	
	private EUmlElement(IHierarchicalEnum parent, Class<? extends Element> clazz) {
		this.parent = parent;
		this.clazz = clazz;
	}
	
	@Override
	public String toString() {
		return this.clazz.getSimpleName();
	}

	@Override
	public IHierarchicalEnum getParent() {
		return parent;
	}
}
