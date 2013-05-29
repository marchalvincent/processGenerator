package fr.lip6.move.processGenerator;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import fr.lip6.move.processGenerator.bpmn2.workflowPattern.ExclusiveChoiceTest;
import fr.lip6.move.processGenerator.bpmn2.workflowPattern.ParallelSplitTest;
import fr.lip6.move.processGenerator.bpmn2.workflowPattern.SequenceTest;
import fr.lip6.move.processGenerator.bpmn2.workflowPattern.SimpleMergeTest;
import fr.lip6.move.processGenerator.bpmn2.workflowPattern.SynchronizationTest;
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
