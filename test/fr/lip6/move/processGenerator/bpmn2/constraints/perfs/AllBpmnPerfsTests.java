package fr.lip6.move.processGenerator.bpmn2.constraints.perfs;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({ElementPerfsTest.class, ExclusiveChoicePerfsTest.class, MultiChoicePerfsTest.class, ParallelSplitPerfsTest.class,
		SequencePerfsTest.class, SimpleMergePerfsTest.class, SynchronizationPerfsTest.class})
public class AllBpmnPerfsTests {
	
}
