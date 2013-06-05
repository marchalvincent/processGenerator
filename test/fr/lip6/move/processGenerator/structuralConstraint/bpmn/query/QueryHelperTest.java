package fr.lip6.move.processGenerator.structuralConstraint.bpmn.query;

import static org.junit.Assert.assertTrue;
import org.junit.Test;
import fr.lip6.move.processGenerator.bpmn2.BpmnException;
import fr.lip6.move.processGenerator.structuralConstraint.bpmn.query.BpmnQueryReaderHelper;


public class QueryHelperTest {

	@Test
	public void test() throws BpmnException {
		String query = BpmnQueryReaderHelper.read("sequence");
		assertTrue(query.length() > 0);
	}
	
	@Test(expected=BpmnException.class)
	public void test2() throws BpmnException {
		BpmnQueryReaderHelper.read("sequenceblabla");
	}
}
