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
 * @see {@link BpmnThreadInsertExplicite} ajout d'un nouveau thread explicite.
 * @see {@link BpmnThreadInsertRandom} ajout d'un nouveau thread implicite ou explicite.
 */
public class BpmnThreadInsertImplicite extends AbstractChangePattern<BpmnProcess> {

	// pour éviter trop d'instanciation de la part du thread insert random
	public static BpmnThreadInsertImplicite instance = new BpmnThreadInsertImplicite();
	
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
			SequenceFlow sequence = BpmnChangePatternHelper.instance.getRandomSequenceFlow(process, rng);
			
			// on créé la parallel, la task sainsi que l'EndEvent (correspondant au nouveau thread)
			ParallelGateway fork = process.buildParallelGatewayDiverging();
			Task a = process.buildTask();
			EndEvent end = process.buildEndEvent();
			
			// on créé les sequences flow
			process.buildSequenceFlow(fork, sequence.getTargetRef());
			sequence.setTargetRef(fork);
			process.buildSequenceFlow(fork, a);
			process.buildSequenceFlow(a, end);
			
		} catch (GeneticException e) {
			e.printStackTrace();
		}
		return process;
	}
}
