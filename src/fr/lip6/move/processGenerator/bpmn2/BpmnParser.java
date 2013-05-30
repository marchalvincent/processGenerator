package fr.lip6.move.processGenerator.bpmn2;

import org.eclipse.bpmn2.DocumentRoot;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;


public class BpmnParser {

	private static final BpmnParser instance = new BpmnParser();

	private BpmnParser() {}

	public static BpmnParser getInstance() {
		return instance;
	}

	public BpmnProcess getBpmnProcess(IFile ifile) {

		if (ifile != null) {
			DocumentRoot documentRoot = null;

			URI uri = URI.createFileURI(ifile.getRawLocationURI().getPath());
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
		}
		return BpmnBuilder.initialFinal();
	}
}
