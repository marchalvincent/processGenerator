package fr.lip6.move.processGenerator.bpmn2.ga.cp;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({BpmnConditionalInsertTest.class, BpmnLoopInsertTest.class, BpmnParallelInsertTest.class, BpmnRemoveTest.class,
		BpmnSerialInsertTest.class, BpmnThreadInsertTest.class, GatewayManagerTest.class})
public class AllBpmnCpTests {}
