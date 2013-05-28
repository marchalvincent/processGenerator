package fr.lip6.move.processGenerator.file.bpmn2;

import java.util.HashMap;
import java.util.Map;


public class BpmnDefinition {

	public static final String expressionLanguage = "http://www.mvel.org/2.0";
	public static final String targetNamespace = "http://www.jboss.org/drools";
	public static final String typeLanguage = "http://www.java.com/javaTypes";
	
	public static final Map<String, String> xmlInformation = new HashMap<String, String>();
	static {
		xmlInformation.put("xsi", "http://www.w3.org/2001/XMLSchema-instance");
		xmlInformation.put("bpmn2", "http://www.omg.org/spec/BPMN/20100524/MODEL");
		xmlInformation.put("bpmndi", "http://www.omg.org/spec/BPMN/20100524/DI");
		xmlInformation.put("dc", "http://www.omg.org/spec/DD/20100524/DC");
		xmlInformation.put("di", "http://www.omg.org/spec/DD/20100524/DI");
		xmlInformation.put("tns", "http://www.jboss.org/drools");
		xmlInformation.put("xmlns", "http://www.jboss.org/drools");
		xmlInformation.put("schemaLocation", "http://www.omg.org/spec/BPMN/20100524/MODEL BPMN20.xsd http://www.jboss.org/drools drools.xsd http://www.bpsim.org/schemas/1.0 bpsim.xsd");
	}
	
	private BpmnDefinition() {}
}
