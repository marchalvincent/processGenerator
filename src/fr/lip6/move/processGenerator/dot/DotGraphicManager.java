package fr.lip6.move.processGenerator.dot;

import org.eclipse.bpmn2.EndEvent;
import org.eclipse.bpmn2.EventDefinition;
import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.Gateway;
import org.eclipse.bpmn2.ParallelGateway;
import org.eclipse.bpmn2.StartEvent;
import org.eclipse.bpmn2.TerminateEventDefinition;
import org.eclipse.uml2.uml.ActivityFinalNode;
import org.eclipse.uml2.uml.ActivityNode;
import org.eclipse.uml2.uml.DecisionNode;
import org.eclipse.uml2.uml.FlowFinalNode;
import org.eclipse.uml2.uml.ForkNode;
import org.eclipse.uml2.uml.InitialNode;
import org.eclipse.uml2.uml.JoinNode;
import org.eclipse.uml2.uml.MergeNode;

/**
 * Cette classe utilitaire sert à modifier l'aspect graphique des diagrammes DOT. C'est ici qu'on peut manipuler les formes, 
 * couleurs etc.
 * @author Vincent
 *
 */
public class DotGraphicManager {
	
	public static DotGraphicManager instance = new DotGraphicManager();
	
	public String getShape(FlowElement element) {
		if (element instanceof StartEvent) return "shape=circle";
		if (element instanceof EndEvent) return "shape=circle";
		if (element instanceof Gateway) return "shape=diamond";
		return "shape=box";
	}
	
	public String getShape(ActivityNode node) {
		if (node instanceof InitialNode) return "shape=circle";
		if (node instanceof FlowFinalNode) return "shape=circle";
		if (node instanceof ActivityFinalNode) return "shape=circle";

		if (node instanceof ForkNode) return "shape=diamond";
		if (node instanceof JoinNode) return "shape=diamond";
		if (node instanceof DecisionNode) return "shape=diamond";
		if (node instanceof MergeNode) return "shape=diamond";
		return "shape=box";
	}

	public String getFillColor(FlowElement element) {
		if (element instanceof ParallelGateway) return "style=filled, fillcolor=lightgrey";
		if (element instanceof Gateway) return "style=filled, fillcolor=lightblue";
		if (element instanceof StartEvent) return "style=filled, fillcolor=green";
		if (element instanceof EndEvent) {
			for (EventDefinition eventDef : ((EndEvent) element).getEventDefinitions()) {
				if (eventDef instanceof TerminateEventDefinition) {
					return "style=filled, fillcolor=sienna";
				}
			}
			return "style=filled, fillcolor=red";
		}
		return "style=filled, fillcolor=white";
	}

	public String getFillColor(ActivityNode node) {
		return "style=filled, fillcolor=white";
	}
	
	public String getWidth(FlowElement element) {
		return "width=1";
	}
	
	public String getWidth(ActivityNode node) {
		return "width=1";
	}
	
	public String getHeight(FlowElement element) {
		if (element instanceof Gateway) return "height=1.5";
		return "height=0.5";
	}
	
	public String getHeight(ActivityNode node) {
		if (node instanceof ForkNode) return "height=1";
		if (node instanceof JoinNode) return "height=1";
		if (node instanceof DecisionNode) return "height=1";
		if (node instanceof MergeNode) return "height=1";
		return "height=0.5";
	}
}
