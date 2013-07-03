package fr.lip6.move.processGenerator.uml2;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.uml2.uml.Activity;

/**
 * Un parseur de fichier UML.
 * 
 * @author Vincent
 * 
 */
public class UmlParser {
	
	public static final UmlParser instance = new UmlParser();
	
	private UmlParser() {}
	
	public UmlProcess getUmlProcess (IFile ifile) {
		
		if (ifile != null) {
			Activity activity = null;
			
			URI uri = URI.createFileURI(ifile.getRawLocationURI().getPath());
			ResourceSet resourceSet = new ResourceSetImpl();
			Resource resource = (Resource) resourceSet.getResource(uri, true);
			
			TreeIterator<EObject> tree = resource.getAllContents();
			while (tree.hasNext()) {
				EObject eo = tree.next();
				if (eo instanceof Activity) {
					activity = (Activity) eo;
					break;
				}
			}
			
			// si on a trouvé une activité
			if (activity != null) {
				return new UmlProcess(activity);
			}
		}
		
		return new UmlProcess();
	}
}
