package fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.changePattern;

import java.util.List;
import java.util.Random;

import org.eclipse.bpmn2.EndEvent;
import org.eclipse.bpmn2.ParallelGateway;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.Task;

import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.geneticAlgorithm.GeneticException;
import fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.AbstractBpmnChangePattern;
import fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.IBpmnChangePattern;
import fr.lip6.move.processGenerator.structuralConstraint.StructuralConstraintChecker;

/**
 * Ce change pattern applique l'insertion d'un nouveau thread au process. Ce nouveau thread
 * s'aboutira soit explicitement (sur un EndEvent) soir implicitement (sans arc sortant).
 * @author Vincent
 *
 */
public class BpmnThreadInsert extends AbstractBpmnChangePattern implements IBpmnChangePattern {

	@Override
	public BpmnProcess apply(BpmnProcess oldProcess, Random rng, List<StructuralConstraintChecker> workflowsConstraints) {

		BpmnProcess process = null;
		try {
			process = new BpmnProcess(oldProcess);
		} catch (BpmnException e) {
			// impossible de copier...
			e.printStackTrace();
			return oldProcess;
		}

		// on récupère une séquence au hasard
		try {
			SequenceFlow sequence = ChangePatternHelper.instance.getRandomSequenceFlow(process, rng);
			
			// on créé la parallel ainsi que la task (correspondant au nouveau thread)
			ParallelGateway fork = process.buildParallelGatewayDiverging();
			Task a = process.buildTask();
			
			// on créé les sequences flow
			process.buildSequenceFlow(fork, sequence.getTargetRef());
			sequence.setTargetRef(fork);
			process.buildSequenceFlow(fork, a);

			// une fois sur deux, on va créer un thread de terminaison implicite ou explicite
			if (rng.nextBoolean()) {
				EndEvent end = process.buildEndEvent();
				process.buildSequenceFlow(a, end);
			}
			
		} catch (GeneticException e) {
			e.printStackTrace();
			return process;
		}
		
		return process;
	}

}
