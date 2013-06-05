package fr.lip6.move.processGenerator;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.BpmnFitnessEvaluatorTest;
import fr.lip6.move.processGenerator.structuralConstraint.bpmn.ExclusiveChoiceTest;
import fr.lip6.move.processGenerator.structuralConstraint.bpmn.ParallelSplitTest;
import fr.lip6.move.processGenerator.structuralConstraint.bpmn.SequenceTest;
import fr.lip6.move.processGenerator.structuralConstraint.bpmn.SimpleMergeTest;
import fr.lip6.move.processGenerator.structuralConstraint.bpmn.SynchronizationTest;
import fr.lip6.move.processGenerator.structuralConstraint.bpmn.query.QueryHelperTest;


@RunWith(Suite.class)
@SuiteClasses({SequenceTest.class,
	ParallelSplitTest.class, 
	SynchronizationTest.class, 
	ExclusiveChoiceTest.class,
	SimpleMergeTest.class, 
	QueryHelperTest.class,
	BpmnFitnessEvaluatorTest.class})
public class WholeTests {
}
