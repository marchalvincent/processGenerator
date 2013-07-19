package fr.lip6.move.processGenerator.bpmn2.ga.cp;

import java.util.List;
import java.util.Random;
import org.eclipse.bpmn2.EndEvent;
import org.eclipse.bpmn2.ParallelGateway;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.Task;
import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;
import fr.lip6.move.processGenerator.ga.AbstractChangePattern;
import fr.lip6.move.processGenerator.ga.GeneticException;

/**
 * Ce change pattern applique l'insertion d'un nouveau thread au process. Ce nouveau thread s'aboutira sur un nouveau
 * noeud EndEvent implicite.
 * 
 * @author Vincent
 * 
 * @see {@link BpmnThreadInsertExplicit} ajout d'un nouveau thread explicite.
 * @see {@link BpmnThreadInsertRandom} ajout d'un nouveau thread implicite ou explicite.
 */
public class BpmnThreadInsertImplicit extends AbstractChangePattern<BpmnProcess> {
	
	// pour éviter trop d'instanciation de la part du thread insert random
	public static BpmnThreadInsertImplicit instance = new BpmnThreadInsertImplicit();
	
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
		SequenceFlow sequence;
		try {
			sequence = BpmnChangePatternHelper.instance.getRandomSequenceFlow(process, rng);
		} catch (GeneticException e) {
			// s'il n'y a pas de sequenceFlow c'est une erreur
			e.printStackTrace();
			return process;
		}

		// on créé la parallel, la task sainsi que l'EndEvent (correspondant au nouveau thread)
		ParallelGateway fork = process.buildParallelGatewayDiverging();
		Task a = process.buildTask();
		EndEvent end = process.buildEndEvent();
		
		// on créé les sequences flow
		process.buildSequenceFlow(fork, sequence.getTargetRef());
		process.buildSequenceFlow(fork, a);
		process.buildSequenceFlow(a, end);
		
		sequence.setTargetRef(fork);
		
		return process;
	}
}
