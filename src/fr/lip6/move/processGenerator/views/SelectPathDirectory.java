package fr.lip6.move.processGenerator.views;

import java.io.File;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * Adapteur déclenché par le changement du dossier de destination des fichiers.
 * 
 * @author Vincent
 * 
 */
public class SelectPathDirectory extends SelectionAdapter {
	
	private ProcessGeneratorView view;
	
	public SelectPathDirectory(ProcessGeneratorView view) {
		super();
		this.view = view;
	}
	
	@Override
	public void widgetSelected (SelectionEvent e) {
		
		DirectoryDialog directoryD = new DirectoryDialog(new Shell());
		String chemin = directoryD.open();
		if (chemin != null) {
			chemin += File.separator;
			view.setPathDirectory(chemin);
		}
	}
}
