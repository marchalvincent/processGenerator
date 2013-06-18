package fr.lip6.move.processGenerator;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.BpmnFitnessEvaluatorTest;
import fr.lip6.move.processGenerator.structuralConstraint.bpmn.ArbitraryCyclesTest;
import fr.lip6.move.processGenerator.structuralConstraint.bpmn.ExclusiveChoiceTest;
import fr.lip6.move.processGenerator.structuralConstraint.bpmn.MultiChoiceTest;
import fr.lip6.move.processGenerator.structuralConstraint.bpmn.MultiMergeTest;
import fr.lip6.move.processGenerator.structuralConstraint.bpmn.ParallelSplitTest;
import fr.lip6.move.processGenerator.structuralConstraint.bpmn.SequenceTest;
import fr.lip6.move.processGenerator.structuralConstraint.bpmn.SimpleMergeTest;
import fr.lip6.move.processGenerator.structuralConstraint.bpmn.StructuredSynchronizingMergeTest;
import fr.lip6.move.processGenerator.structuralConstraint.bpmn.SynchronizationTest;
import fr.lip6.move.processGenerator.structuralConstraint.bpmn.query.QueryHelperTest;


@RunWith(Suite.class)
@SuiteClasses({SequenceTest.class,
	ParallelSplitTest.class, 
	SynchronizationTest.class, 
	ExclusiveChoiceTest.class,
	SimpleMergeTest.class, 
	QueryHelperTest.class,
	BpmnFitnessEvaluatorTest.class,
	StructuredSynchronizingMergeTest.class,
	MultiChoiceTest.class,
	MultiMergeTest.class,
	ArbitraryCyclesTest.class})
public class WholeTests {
}
