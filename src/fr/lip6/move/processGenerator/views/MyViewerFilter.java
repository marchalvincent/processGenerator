package fr.lip6.move.processGenerator.views;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

/**
 * Cette classe permet de filtrer les fichiers selon leurs extension.
 * Elle est utilisée lorsque l'utilisateur souhaite sélection manuellement le process
 * initial de l'algorithme génétique.
 * @author Vincent
 *
 */
public class MyViewerFilter extends ViewerFilter {

	private final String extension;
	
	public MyViewerFilter(String extension) {
		super();
		this.extension = extension;
	}
	
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof IFile) 
			return ((IFile) element).getFileExtension().equals(extension);
		else 
			return true;
	}
}
