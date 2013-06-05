package fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.changePattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.eclipse.bpmn2.Activity;
import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.SequenceFlow;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.geneticAlgorithm.GeneticException;


public class ChangePatternHelper {

	private final static ChangePatternHelper instance = new ChangePatternHelper();

	public static ChangePatternHelper getInstance() {
		return instance;
	}

	private ChangePatternHelper() {
		super();
	}

	public SequenceFlow getRandomSequenceFlow(BpmnProcess process, Random rng) throws GeneticException {

		// on récupère la liste des sequence flow
		List<SequenceFlow> liste = new ArrayList<SequenceFlow>();
		for (FlowElement elem : process.getProcess().getFlowElements()) {
			if (elem instanceof SequenceFlow)
				liste.add((SequenceFlow) elem);
		}
		
		// s'il n'y a aucun arc (cela ne devrait jamais arriver !)
		if (liste.isEmpty())
			throw new GeneticException("The process does not contain any SequenceFlow.");

		// on en prend un au hasard
		return liste.get(rng.nextInt(liste.size()));
	}

	public Activity getRandomActivity(BpmnProcess process, Random rng) throws GeneticException {

		// on récupère la liste des activité
		List<Activity> liste = new ArrayList<Activity>();
		for (FlowElement elem : process.getProcess().getFlowElements()) {
			if (elem instanceof Activity)
				liste.add((Activity) elem);
		}

		// s'il n'y a aucune activité...
		if (liste.isEmpty())
			throw new GeneticException("The process does not contain any Activity.");

		// on en prend un au hasard
		return liste.get(rng.nextInt(liste.size()));
	}
}
