package fr.lip6.move.processGenerator.bpmn2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.bpmn2.Bpmn2Factory;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.DocumentRoot;
import org.eclipse.bpmn2.EndEvent;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.StartEvent;
import org.eclipse.bpmn2.Task;
import org.eclipse.bpmn2.di.BPMNDiagram;
import org.eclipse.bpmn2.di.BPMNShape;
import org.eclipse.bpmn2.di.BpmnDiFactory;
import org.eclipse.bpmn2.di.impl.BpmnDiFactoryImpl;
import org.eclipse.bpmn2.impl.StartEventImpl;
import org.eclipse.bpmn2.util.Bpmn2ResourceFactoryImpl;
import org.eclipse.bpmn2.util.Bpmn2XMLProcessor;
import org.eclipse.dd.dc.Bounds;
import org.eclipse.dd.dc.DcFactory;
import org.eclipse.dd.dc.impl.DcFactoryImpl;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;


public class BpmnProcess {

	private DocumentRoot documentRoot;
	private BpmnDiFactory diFactory = new BpmnDiFactoryImpl();
	private DcFactory dcFactory = new DcFactoryImpl();

	public BpmnProcess() {
		documentRoot = Bpmn2Factory.eINSTANCE.createDocumentRoot();
	}

	public void createInitialABfinal() {
		StartEvent start = (StartEventImpl) Bpmn2Factory.eINSTANCE.createStartEvent();
		Task A = Bpmn2Factory.eINSTANCE.createTask();
		Task B = Bpmn2Factory.eINSTANCE.createTask();
		EndEvent end = Bpmn2Factory.eINSTANCE.createEndEvent();

		SequenceFlow startA = Bpmn2Factory.eINSTANCE.createSequenceFlow();
		SequenceFlow AB = Bpmn2Factory.eINSTANCE.createSequenceFlow();
		SequenceFlow Bend = Bpmn2Factory.eINSTANCE.createSequenceFlow();

		startA.setSourceRef(start);
		startA.setTargetRef(A);

		AB.setSourceRef(A);
		AB.setTargetRef(B);

		Bend.setSourceRef(B);
		Bend.setTargetRef(end);

		documentRoot.setStartEvent(start);
		documentRoot.setEndEvent(end);
		documentRoot.setTask(A);
	}

	public void createModel() throws IOException {
		DocumentRoot documentRoot  = Bpmn2Factory.eINSTANCE.createDocumentRoot();
		documentRoot = Bpmn2Factory.eINSTANCE.createDocumentRoot();
		documentRoot.setDefinitions(Bpmn2Factory.eINSTANCE.createDefinitions());
		documentRoot.getDefinitions().setTargetNamespace("_test");

		//Create the process
		Process process = Bpmn2Factory.eINSTANCE.createProcess();
		process.setId("_model");
		documentRoot.getDefinitions().getRootElements().add(process);

//		//Create the diagram:
//		BPMNDiagram diagram = diFactory.createBPMNDiagram();
//		diagram.setPlane(diFactory.createBPMNPlane());
//		diagram.setName("Test Diagram");
//		documentRoot.getDefinitions().getDiagrams().add(diagram);

		//Add a start event:
		StartEvent start = Bpmn2Factory.eINSTANCE.createStartEvent();
		start.setId("_start");
		process.getFlowElements().add(start);

//		//Toss it in the Diagram:
//		BPMNShape shape = diFactory.createBPMNShape();
//		Bounds bounds = dcFactory.createBounds();
//		bounds.setHeight(20);
//		bounds.setWidth(20);
//		bounds.setX(20);
//		bounds.setY(20);
//		shape.setBounds(bounds);
//		shape.setBpmnElement(start);
//		diagram.getPlane().getPlaneElement().add(shape);

		//Serialize it:
		save(documentRoot, System.out);
	}

	public void save(DocumentRoot model, OutputStream out) throws IOException {
		Bpmn2ResourceFactoryImpl resourceFactory = new Bpmn2ResourceFactoryImpl();
		File tempFile = File.createTempFile("bpmn20convert", "tmp");
		try {
			Resource resource = resourceFactory.createResource(URI.createFileURI(tempFile.getAbsolutePath()));
			resource.getContents().add(model);
			Bpmn2XMLProcessor proc = new Bpmn2XMLProcessor();
			Map<Object, Object> options = new HashMap<Object, Object>();

			File f = new File("vincent.bpmn");
			OutputStream outt = new FileOutputStream(f);
			proc.save(outt, resource, options);
		}
		finally {
			tempFile.delete();
		}
	}

	public static void main(String[] args) throws IOException {
//		new BpmnProcess().createInitialABfinal();
		BpmnProcess bpmn = new BpmnProcess();
		bpmn.createModel();
	}
}
