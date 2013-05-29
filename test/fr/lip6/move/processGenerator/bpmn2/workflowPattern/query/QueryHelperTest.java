package fr.lip6.move.processGenerator.bpmn2.workflowPattern.query;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.bpmn2.workflowPattern.query.QueryReaderHelper;


public class QueryHelperTest {

	@Test
	public void test() throws BpmnException {
		String query = QueryReaderHelper.read("sequence");
		assertTrue(query.length() > 0);
	}
	
	@Test(expected=BpmnException.class)
	public void test2() throws BpmnException {
		QueryReaderHelper.read("sequenceblabla");
	}
}
