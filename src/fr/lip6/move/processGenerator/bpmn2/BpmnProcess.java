package fr.lip6.move.processGenerator.bpmn2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.eclipse.bpmn2.Bpmn2Factory;
import org.eclipse.bpmn2.DocumentRoot;
import org.eclipse.bpmn2.EndEvent;
import org.eclipse.bpmn2.ExclusiveGateway;
import org.eclipse.bpmn2.FlowNode;
import org.eclipse.bpmn2.GatewayDirection;
import org.eclipse.bpmn2.ParallelGateway;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.StartEvent;
import org.eclipse.bpmn2.Task;
import org.eclipse.bpmn2.util.Bpmn2ResourceFactoryImpl;
import org.eclipse.bpmn2.util.Bpmn2XMLProcessor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.uncommons.maths.random.MersenneTwisterRNG;


public class BpmnProcess {

	private DocumentRoot documentRoot;
	private Process process;
	private final static Random rng = new MersenneTwisterRNG();

	public BpmnProcess() {
		super();
		
		// création du document
		documentRoot = Bpmn2Factory.eINSTANCE.createDocumentRoot();
		
		// définition du document
		documentRoot.setDefinitions(Bpmn2Factory.eINSTANCE.createDefinitions());
		documentRoot.getXMLNSPrefixMap().putAll(BpmnDefinition.xmlInformation);
		documentRoot.getDefinitions().setExpressionLanguage(BpmnDefinition.expressionLanguage);
		documentRoot.getDefinitions().setTargetNamespace(BpmnDefinition.targetNamespace);
		documentRoot.getDefinitions().setTypeLanguage(BpmnDefinition.typeLanguage);
		
		// création du process
		process = Bpmn2Factory.eINSTANCE.createProcess();
		documentRoot.getDefinitions().getRootElements().add(process);
	}
	
	public BpmnProcess(BpmnProcess processToCopy) throws BpmnException {
		super();
		documentRoot = EcoreUtil.copy(processToCopy.getDocumentRoot());
		if (documentRoot.getDefinitions().getRootElements().isEmpty() 
				|| !(documentRoot.getDefinitions().getRootElements().get(0) instanceof Process))
			throw new BpmnException("Impossible to copy the process of the bpmn document.");
		else
			process = (Process) documentRoot.getDefinitions().getRootElements().get(0);
	}
	
	public BpmnProcess(DocumentRoot documentRoot) {
		super();
		this.documentRoot = documentRoot;
		this.process = (Process) documentRoot.getDefinitions().getRootElements().get(0);
	}
	
	public DocumentRoot getDocumentRoot() {
		return documentRoot;
	}

	public Process getProcess() {
		return process;
	}
	
	public StartEvent buildStartEvent() {
		StartEvent start = Bpmn2Factory.eINSTANCE.createStartEvent();
		
		String name = BpmnNameManager.getStartName();
		start.setId("id_" + name);
		start.setName(name);
		
		process.getFlowElements().add(start);
		return start;
	}
	
	public EndEvent buildEndEvent() {
		EndEvent end = Bpmn2Factory.eINSTANCE.createEndEvent();
		
		String name = BpmnNameManager.getEndName();
		end.setId("id_" + name);
		end.setName(name);
		
		process.getFlowElements().add(end);
		return end;
	}
	
	public Task buildTask() {
//		Task task = Bpmn2Factory.eINSTANCE.createTask();
		Task task = this.buildRandomSubTask();
		
		String name = BpmnNameManager.getTaskName();
		task.setId("id_" + name);
		task.setName(name);
		
		process.getFlowElements().add(task);
		return task;
	}
	
	private Task buildRandomSubTask() {
		switch (rng.nextInt(7)) {
			case 0:
				return Bpmn2Factory.eINSTANCE.createBusinessRuleTask();
			case 1:
				return Bpmn2Factory.eINSTANCE.createManualTask();
			case 2:
				return Bpmn2Factory.eINSTANCE.createReceiveTask();
			case 3:
				return Bpmn2Factory.eINSTANCE.createScriptTask();
			case 4:
				return Bpmn2Factory.eINSTANCE.createSendTask();
			case 5:
				return Bpmn2Factory.eINSTANCE.createServiceTask();
			case 6:
				return Bpmn2Factory.eINSTANCE.createUserTask();
			default:
				break;
		}
		return Bpmn2Factory.eINSTANCE.createTask();
	}

	public ParallelGateway buildParallelGatewayDiverging() {
		return this.buildParallelGateway(GatewayDirection.DIVERGING);
	}
	
	public ParallelGateway buildParallelGatewayConverging() {
		return this.buildParallelGateway(GatewayDirection.CONVERGING);
	}
	
	private ParallelGateway buildParallelGateway(GatewayDirection direction) {
		ParallelGateway parallel = Bpmn2Factory.eINSTANCE.createParallelGateway();
		parallel.setGatewayDirection(direction);
		
		String name = BpmnNameManager.getParallelName(direction.toString());
		parallel.setId("id" + name);
		parallel.setName(name);
		
		process.getFlowElements().add(parallel);
		return parallel;
	}

	public ExclusiveGateway buildExclusiveGatewayDiverging() {
		return this.buildExclusiveGateway(GatewayDirection.DIVERGING);
	}
	
	public ExclusiveGateway buildExclusiveGatewayConverging() {
		return this.buildExclusiveGateway(GatewayDirection.CONVERGING);
	}
	
	private ExclusiveGateway buildExclusiveGateway(GatewayDirection direction) {
		ExclusiveGateway exclusive = Bpmn2Factory.eINSTANCE.createExclusiveGateway();
		exclusive.setGatewayDirection(direction);
		
		String name = BpmnNameManager.getExclusiveName(direction.toString());
		exclusive.setId("id" + name);
		exclusive.setName(name);
		
		process.getFlowElements().add(exclusive);
		return exclusive;
	}

	public SequenceFlow buildSequenceFlow() {
		SequenceFlow sequence = Bpmn2Factory.eINSTANCE.createSequenceFlow();
		
		String name = BpmnNameManager.getSequenceName();
		sequence.setId("id_" + name);
		sequence.setName(name);
		
		process.getFlowElements().add(sequence);
		return sequence;
	}
	
	public SequenceFlow buildSequenceFlow(FlowNode source, FlowNode target) {
		SequenceFlow sequence = this.buildSequenceFlow();
		sequence.setSourceRef(source);
		sequence.setTargetRef(target);
		return sequence;
	}
	
	public boolean removeFlowNode(FlowNode flowNode) {
		return process.getFlowElements().remove(flowNode);
	}
	
	public boolean removeSequenceFlow(SequenceFlow sequence) {
		sequence.setSourceRef(null);
		sequence.setTargetRef(null);
		return process.getFlowElements().remove(sequence);
	}

	public void save(String nameFile) throws IOException {
		Bpmn2ResourceFactoryImpl resourceFactory = new Bpmn2ResourceFactoryImpl();
		File tempFile = File.createTempFile("bpmn20convert", "tmp");
		try {
			Resource resource = resourceFactory.createResource(URI.createFileURI(tempFile.getAbsolutePath()));
			resource.getContents().add(this.documentRoot);
			Bpmn2XMLProcessor proc = new Bpmn2XMLProcessor();
			Map<Object, Object> options = new HashMap<Object, Object>();

			File f = new File(nameFile);
			f.createNewFile();
			OutputStream outt = new FileOutputStream(f);
			proc.save(outt, resource, options);
		}
		finally {
			tempFile.delete();
		}
	}
}
