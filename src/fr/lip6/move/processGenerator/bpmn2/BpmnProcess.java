package fr.lip6.move.processGenerator.bpmn2;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.eclipse.bpmn2.Bpmn2Factory;
import org.eclipse.bpmn2.DocumentRoot;
import org.eclipse.bpmn2.EndEvent;
import org.eclipse.bpmn2.ExclusiveGateway;
import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.FlowNode;
import org.eclipse.bpmn2.Gateway;
import org.eclipse.bpmn2.GatewayDirection;
import org.eclipse.bpmn2.InclusiveGateway;
import org.eclipse.bpmn2.ParallelGateway;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.ScriptTask;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.StartEvent;
import org.eclipse.bpmn2.Task;
import org.eclipse.bpmn2.util.Bpmn2ResourceFactoryImpl;
import org.eclipse.bpmn2.util.Bpmn2XMLProcessor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.uncommons.maths.random.MersenneTwisterRNG;

/**
 * Représente un fichier BPMN dans sa globalité.
 * 
 * @author Vincent
 * 
 */
public class BpmnProcess {
	
	private final static Random rng = new MersenneTwisterRNG();
	
	/**
	 * Permet de contenir des informations sur la définition XML du document.
	 */
	private DocumentRoot documentRoot;
	
	/**
	 * Contient toutes les informations relatives au process, noeuds, arcs, etc.
	 */
	private Process process;
	
	/**
	 * Cette map permet de lier deux {@link Gateway} entre elles et donc de faciliter leurs manipulations. En général
	 * les deux gateways liées sont une ouvrante et une fermante.
	 */
	private Map<String, String> gatewaysLinked = new HashMap<>();
	
	/**
	 * Créé un process vide.
	 */
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
		process.setName(BpmnNameManager.instance.getProcessName());
		documentRoot.getDefinitions().getRootElements().add(process);
	}
	
	/**
	 * Créé un process à partir d'un autre. La copie se fait en profondeur.
	 * 
	 * @param processToCopy
	 *            le {@link BpmnProcess} à copier.
	 * @throws BpmnException
	 *             lorsque l'on a pas réussit a trouver de {@link Process} dans le paramètre.
	 */
	public BpmnProcess(BpmnProcess processToCopy) throws BpmnException {
		super();
		documentRoot = EcoreUtil.copy(processToCopy.getDocumentRoot());
		if (documentRoot.getDefinitions().getRootElements().isEmpty()
				|| !(documentRoot.getDefinitions().getRootElements().get(0) instanceof Process)) {
			throw new BpmnException("Impossible to copy the process of the bpmn document.");
		} else {
			process = (Process) documentRoot.getDefinitions().getRootElements().get(0);
		}
		
		for (String key : processToCopy.gatewaysLinked.keySet()) {
			gatewaysLinked.put(new String(key), new String(processToCopy.gatewaysLinked.get(key)));
		}
	}

	/**
	 * Créé un BpmnProcess à partir d'un {@link DocumentRoot} et d'un {@link Process}.
	 * 
	 * @param documentRoot
	 * @param process
	 */
	public BpmnProcess(DocumentRoot documentRoot, Process process) {
		super();
		this.documentRoot = documentRoot;
		this.process = process;
		process.setName(BpmnNameManager.instance.getProcessName());
	}
	
	public DocumentRoot getDocumentRoot() {
		return documentRoot;
	}
	
	public Process getProcess() {
		return process;
	}
	
	public void setProcess(Process process) {
		this.process = process;
	}
	
	/**
	 * Créé et associé un {@link StartEvent} au process.
	 */
	public StartEvent buildStartEvent() {
		StartEvent start = Bpmn2Factory.eINSTANCE.createStartEvent();
		
		String name = BpmnNameManager.instance.getFlowElementName(start);
		start.setId("id_" + name);
		start.setName(name);
		
		process.getFlowElements().add(start);
		return start;
	}
	
	/**
	 * Créé et associé un {@link EndEvent} au process.
	 */
	public EndEvent buildEndEvent() {
		EndEvent end = Bpmn2Factory.eINSTANCE.createEndEvent();
		
		String name = BpmnNameManager.instance.getFlowElementName(end);
		end.setId("id_" + name);
		end.setName(name);
		
		process.getFlowElements().add(end);
		return end;
	}
	
	/**
	 * Créé et associé une {@link Task} choisie au hasard au process.
	 */
	public Task buildTask() {
		
		Task task = BpmnProcess.buildRandomTask();
		
		String name = BpmnNameManager.instance.getFlowElementName(task);
		task.setId("id_" + name);
		task.setName(name);
		
		process.getFlowElements().add(task);
		return task;
	}
	
	/**
	 * Nomme et associe la Task passée en paramètre au process.
	 * 
	 * @param createScriptTask
	 * @return
	 */
	public Task buildTask(Task task) {
		
		String name = BpmnNameManager.instance.getFlowElementName(task);
		task.setId("id_" + name);
		task.setName(name);
		
		process.getFlowElements().add(task);
		return task;
	}
	
	/**
	 * Renvoie une {@link Task} au hasard parmis celle existante dans BPMN.
	 */
	public static Task buildRandomTask() {
		// TODO voir si possible sendTask, BusinessRuleTask
		switch (rng.nextInt(5)) {
			case 0:
				return Bpmn2Factory.eINSTANCE.createManualTask();
			case 1:
				return Bpmn2Factory.eINSTANCE.createReceiveTask();
			case 2:
				ScriptTask scriptTask = Bpmn2Factory.eINSTANCE.createScriptTask();
				scriptTask.setScript("example of script");
				scriptTask.setScriptFormat("script format");
				return scriptTask;
			case 3:
				return Bpmn2Factory.eINSTANCE.createServiceTask();
			case 4:
				return Bpmn2Factory.eINSTANCE.createUserTask();
			default:
				break;
		}
		return Bpmn2Factory.eINSTANCE.createTask();
	}
	
	/**
	 * Créé et associé un {@link ParallelGateway} divergente au process.
	 */
	public ParallelGateway buildParallelGatewayDiverging() {
		return this.buildParallelGateway(GatewayDirection.DIVERGING);
	}
	
	/**
	 * Créé et associé un {@link ParallelGateway} convergente au process.
	 */
	public ParallelGateway buildParallelGatewayConverging() {
		return this.buildParallelGateway(GatewayDirection.CONVERGING);
	}
	
	/**
	 * Renvoie une {@link ParallelGateway} avec une direction prédéfinie.
	 * 
	 * @param direction
	 *            la {@link GatewayDirection} de la Gateway.
	 * @return la parallel gateway.
	 */
	private ParallelGateway buildParallelGateway(GatewayDirection direction) {
		ParallelGateway parallel = Bpmn2Factory.eINSTANCE.createParallelGateway();
		parallel.setGatewayDirection(direction);
		
		String name = BpmnNameManager.instance.getGatewayName(parallel, direction.toString());
		parallel.setId("id_" + name);
		parallel.setName(name);
		
		process.getFlowElements().add(parallel);
		return parallel;
	}
	
	/**
	 * Créé et associé un {@link ExclusiveGateway} divergente au process.
	 */
	public ExclusiveGateway buildExclusiveGatewayDiverging() {
		return this.buildExclusiveGateway(GatewayDirection.DIVERGING);
	}
	
	/**
	 * Créé et associé un {@link ExclusiveGateway} convergente au process.
	 */
	public ExclusiveGateway buildExclusiveGatewayConverging() {
		return this.buildExclusiveGateway(GatewayDirection.CONVERGING);
	}
	
	/**
	 * Renvoie une {@link ExclusiveGateway} avec une direction prédéfinie.
	 * 
	 * @param direction
	 *            la {@link GatewayDirection} que doit avoir la gateway.
	 * @return l'exclusive gateway.
	 */
	private ExclusiveGateway buildExclusiveGateway(GatewayDirection direction) {
		ExclusiveGateway exclusive = Bpmn2Factory.eINSTANCE.createExclusiveGateway();
		exclusive.setGatewayDirection(direction);
		
		String name = BpmnNameManager.instance.getGatewayName(exclusive, direction.toString());
		exclusive.setId("id_" + name);
		exclusive.setName(name);
		
		process.getFlowElements().add(exclusive);
		return exclusive;
	}
	
	/**
	 * Créé et associé un {@link InclusiveGateway} divergente au process.
	 */
	public InclusiveGateway buildInclusiveGatewayDiverging() {
		return this.buildInclusiveGateway(GatewayDirection.DIVERGING);
	}
	
	/**
	 * Créé et associé un {@link InclusiveGateway} convergente au process.
	 */
	public InclusiveGateway buildInclusiveGatewayConverging() {
		return this.buildInclusiveGateway(GatewayDirection.CONVERGING);
	}
	
	/**
	 * Renvoie une {@link InclusiveGateway} avec une direction prédéfinie.
	 * 
	 * @param direction
	 *            la {@link GatewayDirection} que doit avoir la gateway.
	 * @return l'inclusive gateway.
	 */
	private InclusiveGateway buildInclusiveGateway(GatewayDirection direction) {
		InclusiveGateway inclusive = Bpmn2Factory.eINSTANCE.createInclusiveGateway();
		inclusive.setGatewayDirection(direction);
		
		String name = BpmnNameManager.instance.getGatewayName(inclusive, direction.toString());
		inclusive.setId("id_" + name);
		inclusive.setName(name);
		
		process.getFlowElements().add(inclusive);
		return inclusive;
	}
	
	/**
	 * Créé et associé un {@link SequenceFlow} sans source ni destination au process.
	 */
	private SequenceFlow buildSequenceFlow() {
		SequenceFlow sequence = Bpmn2Factory.eINSTANCE.createSequenceFlow();
		
		String name = BpmnNameManager.instance.getFlowElementName(sequence);
		sequence.setId("id_" + name);
		sequence.setName(name);
		
		process.getFlowElements().add(sequence);
		return sequence;
	}
	
	/**
	 * Créé et associe un {@link SequenceFlow} avec une source et une destination spécifique.
	 * 
	 * @param source
	 *            le {@link FlowNode} source de l'arc.
	 * @param target
	 *            le {@link FlowNode} destination de l'arc.
	 * @return l'arc créé et associé au process.
	 */
	public SequenceFlow buildSequenceFlow(FlowNode source, FlowNode target) {
		SequenceFlow sequence = this.buildSequenceFlow();
		sequence.setSourceRef(source);
		sequence.setTargetRef(target);
		return sequence;
	}
	
	/**
	 * Lie une {@link SequenceFlow} avec deux {@link FlowNode} pour source/destination.
	 * 
	 * @param id
	 *            l'id de la SequenceFlow.
	 * @param source
	 *            l'id de la source.
	 * @param target
	 *            l'id de la destination.
	 * @throws BpmnException
	 *             dans le cas où un des FlowElements est introuvable dans le process.
	 */
	public void setSequenceFlowInformations(String id, String source, String target) throws BpmnException {
		FlowNode sourceNode = this.getElementById(FlowNode.class, source);
		FlowNode targetNode = this.getElementById(FlowNode.class, target);
		SequenceFlow sequence = this.getElementById(SequenceFlow.class, id);
		
		if (sourceNode == null || targetNode == null || sequence == null)
			throw new BpmnException("One of the FlowElement is not found in the process.");
		
		sequence.setSourceRef(sourceNode);
		sequence.setTargetRef(targetNode);
	}
	
	/**
	 * Renvoie l'élément du process selon sa classe et son id.
	 * 
	 * @param clazz
	 *            la classe de l'élément attendu.
	 * @param id
	 *            l'id de l'élément à renvoyer.
	 * @return
	 */
	private <T> T getElementById(Class<T> clazz, String id) {
		for (FlowElement element : getProcess().getFlowElements()) {
			if (element.getId().equals(id) && clazz.isInstance(element))
				return clazz.cast(element);
		}
		return null;
	}
	
	/**
	 * Supprime un {@link FlowNode} du process. Si le flow node possède un noeud "jumeau" alors ils sont tous les deux supprimés
	 * de la map les contenants.
	 * 
	 * @param flowNode
	 *            le {@link FlowNode} à supprimer.
	 * @return true si le noeud à été supprimé, false sinon.
	 */
	public boolean removeFlowNode(FlowNode flowNode) {
		// on le supprime des twins éventuelles
		Gateway twin = getTwin(flowNode.getId());
		if (twin != null) {
			gatewaysLinked.remove(flowNode);
			gatewaysLinked.remove(twin);
		}
		return process.getFlowElements().remove(flowNode);
	}
	
	/**
	 * Supprime un {@link SequenceFlow} du process.
	 * 
	 * @param sequence
	 *            le {@link SequenceFlow} à supprimer.
	 * @return true si l'arc à été supprimé, false sinon.
	 */
	public boolean removeSequenceFlow(SequenceFlow sequence) {
		sequence.setSourceRef(null);
		sequence.setTargetRef(null);
		return process.getFlowElements().remove(sequence);
	}
	
	/**
	 * Sauvegarde le process dans un fichier dont le path est précisé en paramètre.
	 * 
	 * @param nameFile
	 *            le path du fichier.
	 * @throws IOException
	 *             en cas de problème d'entrée sortie dans la création du fichier.
	 */
	public void save(String nameFile) {
		
		Bpmn2ResourceFactoryImpl resourceFactory = new Bpmn2ResourceFactoryImpl();
		File tempFile = null;
		try {
			tempFile = File.createTempFile("bpmn20convert", "tmp");
			
			Resource resource = resourceFactory.createResource(URI.createFileURI(tempFile.getAbsolutePath()));
			resource.getContents().add(getDocumentRoot());
			
			Bpmn2XMLProcessor proc = new Bpmn2XMLProcessor();
			Map<Object, Object> options = Collections.emptyMap();
			
			File f = new File(nameFile);
			f.createNewFile();
			
			OutputStream out = new FileOutputStream(f);
			proc.save(out, resource, options);
			out.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (tempFile != null)
				tempFile.delete();
		}
	}
	
	/**
	 * Lie deux gateways
	 * 
	 * @param diverging
	 * @param converging
	 */
	public void linkGateways(Gateway diverging, Gateway converging) {
		gatewaysLinked.put(diverging.getId(), converging.getId());
		gatewaysLinked.put(converging.getId(), diverging.getId());
	}
	
	/**
	 * Ajoute plusieurs liens entre deux gateways
	 * 
	 * @param links
	 */
	public void addLinksGateways(Map<String, String> links) {
		gatewaysLinked.putAll(links);
	}
	
	/**
	 * Renvoie la gateway jumelle de celle dont l'id est passé en paramètre
	 * 
	 * @param gatewayId
	 * @return
	 */
	public Gateway getTwin(String gatewayId) {
		String twinId = gatewaysLinked.get(gatewayId);
		if (twinId != null) {
			for (FlowElement element : getProcess().getFlowElements()) {
				if (element instanceof Gateway && element.getId().equals(twinId))
					return (Gateway) element;
			}
		}
		return null;
	}
}
