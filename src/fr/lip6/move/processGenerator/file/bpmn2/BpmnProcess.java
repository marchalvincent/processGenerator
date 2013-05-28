package fr.lip6.move.processGenerator.file.bpmn2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
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


public class BpmnProcess {

	private DocumentRoot documentRoot;
	private Process process;

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
			throw new BpmnException("Impossible de copier le process du document bpmn.");
		else
			process = (Process) documentRoot.getDefinitions().getRootElements().get(0);
	}
	
	public DocumentRoot getDocumentRoot() {
		return documentRoot;
	}

	public Process getProcess() {
		return process;
	}
	
	public StartEvent buildStartEvent() {
		StartEvent start = Bpmn2Factory.eINSTANCE.createStartEvent();
		
		String name = NameManager.getStartName();
		start.setId("id_" + name);
		start.setName(name);
		
		process.getFlowElements().add(start);
		return start;
	}
	
	public EndEvent buildEndEvent() {
		EndEvent end = Bpmn2Factory.eINSTANCE.createEndEvent();
		
		String name = NameManager.getEndName();
		end.setId("id_" + name);
		end.setName(name);
		
		process.getFlowElements().add(end);
		return end;
	}
	
	public Task buildTask() {
		Task task = Bpmn2Factory.eINSTANCE.createTask();
		
		String name = NameManager.getTaskName();
		task.setId("id_" + name);
		task.setName(name);
		
		process.getFlowElements().add(task);
		return task;
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
		
		String name = NameManager.getParallelName(direction.toString());
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
		
		String name = NameManager.getExclusiveName(direction.toString());
		exclusive.setId("id" + name);
		exclusive.setName(name);
		
		process.getFlowElements().add(exclusive);
		return exclusive;
	}

	public SequenceFlow buildSequenceFlow() {
		SequenceFlow sequence = Bpmn2Factory.eINSTANCE.createSequenceFlow();
		
		String name = NameManager.getSequenceName();
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

	public void save(DocumentRoot model, String nameFile) throws IOException {
		Bpmn2ResourceFactoryImpl resourceFactory = new Bpmn2ResourceFactoryImpl();
		File tempFile = File.createTempFile("bpmn20convert", "tmp");
		try {
			Resource resource = resourceFactory.createResource(URI.createFileURI(tempFile.getAbsolutePath()));
			resource.getContents().add(model);
			Bpmn2XMLProcessor proc = new Bpmn2XMLProcessor();
			Map<Object, Object> options = new HashMap<Object, Object>();

			File f = new File(nameFile);
			OutputStream outt = new FileOutputStream(f);
			proc.save(outt, resource, options);
		}
		finally {
			tempFile.delete();
		}
	}

	public static void main(String[] args) throws IOException, BpmnException {
		BpmnProcess bpmn = BpmnBuilder.createExampleWithParallel();
		try {
			bpmn.save(bpmn.getDocumentRoot(), "vincent.bpmn");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
