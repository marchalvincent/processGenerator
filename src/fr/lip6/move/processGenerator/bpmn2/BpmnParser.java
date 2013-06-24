package fr.lip6.move.processGenerator.bpmn2;

import org.eclipse.bpmn2.DocumentRoot;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

/**
 * Cette classe permet de parser un fichier ayant pour extension .bpmn 
 * et d'y récupérer un {@link BpmnProcess}.
 * @author Vincent
 *
 */
public class BpmnParser {

	private static final BpmnParser instance = new BpmnParser();
	private BpmnParser() {}
	public static BpmnParser getInstance() {
		return instance;
	}
	
	/**
	 * Renvoie un {@link BpmnProcess} à partir d'un path.
	 * @param path String, le chemin vers le fichier à parser.
	 * @return {@link BpmnProcess}.
	 */
	public BpmnProcess getBpmnProcess(String path) {
		
		DocumentRoot documentRoot = null;
		URI uri = URI.createFileURI(path);

		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = (Resource) resourceSet.getResource(uri, true);

		TreeIterator<EObject> tree = resource.getAllContents();
		while (tree.hasNext()) {
			EObject eo = tree.next();
			if (eo instanceof DocumentRoot) {
				documentRoot = (DocumentRoot) eo;
				break;
			}
		}

		// si on a trouvé un documentRoot
		if (documentRoot != null) {
			return new BpmnProcess(documentRoot);
		}
		
		return BpmnBuilder.instance.initialFinal();
	}

	/**
	 * Renvoie un {@link BpmnProcess} à partir d'un {@link IFile}.
	 * @param ifile le {@link IFile} est un descripteur de fichier selon eclipse.
	 * @return {@link BpmnProcess}.
	 */
	public BpmnProcess getBpmnProcess(IFile ifile) {

		if (ifile != null) {
			return this.getBpmnProcess(ifile.getRawLocationURI().getPath());
		}
		return BpmnBuilder.instance.initialFinal();
	}
}
