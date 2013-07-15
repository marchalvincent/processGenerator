package fr.lip6.move.processGenerator.bpmn2.constraints;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ArbitraryCyclesTest.class, ElementConstraintTest.class, ExclusiveChoiceTest.class, ExpliciteTerminationTest.class,
		ImpliciteTerminationTest.class, MultiChoiceTest.class, MultiMergeTest.class, ParallelSplitTest.class, SequenceTest.class,
		SimpleMergeTest.class, StructuredSynchronizingMergeTest.class, SynchronizationTest.class})
public class AllBpmnConstraintsTests {}
