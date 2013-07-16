package fr.lip6.move.processGenerator;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import fr.lip6.move.processGenerator.bpmn2.constraints.AllBpmnConstraintsTests;
import fr.lip6.move.processGenerator.bpmn2.constraints.perfs.AllBpmnPerfsTests;
import fr.lip6.move.processGenerator.bpmn2.ga.AllBpmnGaTests;
import fr.lip6.move.processGenerator.bpmn2.ga.cp.AllBpmnCpTests;
import fr.lip6.move.processGenerator.bpmn2.jung.AllBpmnJungTests;
import fr.lip6.move.processGenerator.uml2.constraints.perfs.AllUmlPerfsTests;

@RunWith(Suite.class)
@SuiteClasses({AllBpmnConstraintsTests.class, AllBpmnPerfsTests.class, AllBpmnGaTests.class, AllBpmnCpTests.class,
		AllBpmnJungTests.class,
		// TODO uml2.0
		
		AllUmlPerfsTests.class,})
public class WholeTests {}
