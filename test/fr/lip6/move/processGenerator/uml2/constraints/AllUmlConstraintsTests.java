package fr.lip6.move.processGenerator.uml2.constraints;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({UmlAbritraryCycleTest.class, UmlElementConstraintTest.class, UmlExclusiveChoiceTest.class,
		UmlExplicitTerminationTest.class, UmlImplicitTerminationTest.class, UmlParallelSplitTest.class, UmlSequenceTest.class,
		UmlSimpleMergeTest.class, UmlSynchronizationTest.class})
public class AllUmlConstraintsTests {}
