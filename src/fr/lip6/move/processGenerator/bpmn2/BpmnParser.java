package fr.lip6.move.processGenerator.bpmn2;

import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.eclipse.bpmn2.DocumentRoot;
import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.FlowNode;
import org.eclipse.bpmn2.Gateway;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import fr.lip6.move.processGenerator.bpmn2.ga.cp.BpmnGatewayManager;

/**
 * Cette classe permet de parser un fichier ayant pour extension .bpmn et d'y récupérer un {@link BpmnProcess}.
 * 
 * @author Vincent
 * 
 */
public class BpmnParser {
	
	public static final BpmnParser instance = new BpmnParser();
	
	private BpmnParser() {}
	
	/**
	 * Renvoie un {@link BpmnProcess} à partir du path d'un fichier.
	 * 
	 * @param path
	 *            String, le chemin vers le fichier à parser.
	 * @return {@link BpmnProcess}.
	 */
	public BpmnProcess getBpmnProcess(String path) {
		
		DocumentRoot documentRoot = null;
		Process process = null;
		URI uri = URI.createFileURI(path);
		
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = (Resource) resourceSet.getResource(uri, true);
		
		TreeIterator<EObject> tree = resource.getAllContents();
		while (tree.hasNext() && (documentRoot == null || process == null)) {
			EObject eo = tree.next();
			if (eo instanceof DocumentRoot) {
				documentRoot = (DocumentRoot) eo;
			} else if (eo instanceof Process) {
				process = (Process) eo;
			}
		}
		
		// si on a trouvé un documentRoot
		if (documentRoot != null) {
			
			BpmnProcess bpmnProcess = new BpmnProcess(documentRoot, process);
			// on est obligé de faire un second parsing car le précédent ne retient pas les liens sources/targets des
			// sequenceFlow
			this.getSequenceFlowDetails(uri, bpmnProcess);
			// cette fois on va essayer de récupérer les gateways jumelles
			this.linkGateways(bpmnProcess);
			// on renomme chaque element pour ne pas avoir de conflits avec notre BpmnNameManager
			this.renameIds(bpmnProcess);
			return bpmnProcess;
		}
		
		return BpmnBuilder.instance.initialFinal();
	}
	
	/**
	 * Renomme tous les ids des éléments pour éviter d'entrer en conflit de nom avec le {@link BpmnNameManager}.
	 * 
	 * @param bpmnProcess
	 *            le {@link BpmnProcess} dont on doit renommer les ids.
	 */
	private void renameIds(BpmnProcess bpmnProcess) {
		for (FlowElement element : bpmnProcess.getProcess().getFlowElements()) {
			element.setId("perso_" + element.getId());
			element.setName("perso_" + element.getName());
		}
	}
	
	/**
	 * Utilise un {@link DocumentBuilder} pour parser le fichier XML et récupérer chaque source/destination des
	 * {@link SequenceFlow} afin de les lier avec les {@link FlowNode} du process.
	 * 
	 * @param uri
	 *            l'{@link URI} du fichier correspondant au process sélectionné.
	 * @param process
	 *            le {@link BpmnProcess} récupéré par le 1er parsing.
	 */
	private void getSequenceFlowDetails(URI uri, BpmnProcess process) {
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder db = factory.newDocumentBuilder();
			Document doc = db.parse(uri.toString());
			
			// on récupère la liste de tous les sequenceFlows
			NodeList list = doc.getElementsByTagName("bpmn2:sequenceFlow");
			for (int i = 0; i < list.getLength(); i++) {
				Node elem = list.item(i);
				
				NamedNodeMap map = elem.getAttributes();
				Node idItem = map.getNamedItem("id");
				Node sourceItem = map.getNamedItem("sourceRef");
				Node targetItem = map.getNamedItem("targetRef");
				if (idItem == null || sourceItem == null || targetItem == null)
					continue;
				
				// on récupère l'id, la source et la destination
				String id = idItem.getNodeValue();
				String source = sourceItem.getNodeValue();
				String target = targetItem.getNodeValue();
				
				process.setSequenceFlowInformations(id, source, target);
			}
		} catch (ParserConfigurationException | SAXException | IOException | BpmnException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Essaye de lier les gateways qui sont jumelles.
	 * 
	 * @param bpmnProcess
	 *            le {@link BpmnProcess} à parcourir.
	 */
	private void linkGateways(BpmnProcess bpmnProcess) {
		for (FlowElement element : bpmnProcess.getProcess().getFlowElements()) {
			if (element instanceof Gateway) {
				Gateway gate = (Gateway) element;
				Gateway twin = BpmnGatewayManager.instance.findTwinGateway(bpmnProcess, gate);
				if (twin != null) {
					bpmnProcess.linkGateways(gate, twin);
				}
			}
		}
	}
	
	/**
	 * Renvoie un {@link BpmnProcess} à partir d'un {@link IFile}.
	 * 
	 * @param ifile
	 *            le {@link IFile} est un descripteur de fichier selon eclipse.
	 * @return {@link BpmnProcess}.
	 */
	public BpmnProcess getBpmnProcess(IFile ifile) {
		
		if (ifile != null) {
			return this.getBpmnProcess(ifile.getRawLocationURI().getPath());
		}
		return BpmnBuilder.instance.initialFinal();
	}
}
