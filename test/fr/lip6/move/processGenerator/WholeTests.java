package fr.lip6.move.processGenerator;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import fr.lip6.move.processGenerator.bpmn2.ExclusiveChoiceTest;
import fr.lip6.move.processGenerator.bpmn2.ParallelSplitTest;
import fr.lip6.move.processGenerator.bpmn2.SequenceTest;
import fr.lip6.move.processGenerator.bpmn2.SimpleMergeTest;
import fr.lip6.move.processGenerator.bpmn2.SynchronizationTest;
import fr.lip6.move.processGenerator.bpmn2.workflowPattern.query.QueryHelperTest;


@RunWith(Suite.class)
@SuiteClasses({SequenceTest.class,
	ParallelSplitTest.class, 
	SynchronizationTest.class, 
	ExclusiveChoiceTest.class,
	SimpleMergeTest.class, 
	QueryHelperTest.class})
public class WholeTests {
}
