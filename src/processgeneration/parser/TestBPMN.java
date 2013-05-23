package processgeneration.parser;

import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.Task;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.ui.dialogs.WorkspaceResourceDialog;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.swt.widgets.Shell;


public class TestBPMN {

	public TestBPMN() {
		IFile file[] = WorkspaceResourceDialog.openFileSelection(new Shell(), 
																"Selectionnez le fichier bpmn", 
																null, 
																true, 
																null, 
																null);
		IFile f = file[0];
		
		URI uri = URI.createFileURI(f.getRawLocationURI().getPath());
		ResourceSet resourceSet = new ResourceSetImpl();
		Resource resource = (Resource) resourceSet.getResource(uri, true);

		TreeIterator<EObject> tree = resource.getAllContents();
		while (tree.hasNext()) {
			EObject eo = tree.next();
			if (eo instanceof Task) {
				Task e = (Task) eo;
				System.out.println("task " + e.getName());
			} else if (eo instanceof SequenceFlow) {
				SequenceFlow e = (SequenceFlow) eo;
				System.out.println("flow " + e.getName());
			}
		}
	}
	
	public static void main(String[] args) {
		new TestBPMN();
	}
}
