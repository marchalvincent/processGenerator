package fr.lip6.move.processGenerator;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import fr.lip6.move.processGenerator.bpmn2.constraints.AllBpmnConstraintsTests;
import fr.lip6.move.processGenerator.bpmn2.ga.AllBpmnGaTests;
import fr.lip6.move.processGenerator.bpmn2.ga.cp.AllBpmnCpTests;
import fr.lip6.move.processGenerator.bpmn2.jung.AllBpmnJungTests;

@RunWith(Suite.class)
@SuiteClasses({
	AllBpmnConstraintsTests.class,
	AllBpmnGaTests.class, 
	AllBpmnCpTests.class,
	AllBpmnJungTests.class,
	//TODO uml2.0
	
})
public class WholeTests {}
