package fr.lip6.move.processGenerator.views;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.ui.dialogs.WorkspaceResourceDialog;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Shell;
import fr.lip6.move.processGenerator.bpmn2.BpmnParser;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.uml2.UmlParser;

/**
 * Cet évènement est déclenché lorsque l'utilisateur à sélectionné le process initial de l'algo génétique.
 * @author Vincent
 *
 */
public class SelectSetInitialProcess extends SelectionAdapter {

	private ProcessGeneratorView view;
	public SelectSetInitialProcess(ProcessGeneratorView view) {
		super();
		this.view = view;
	}

	@Override
	public void widgetSelected(SelectionEvent e) {

		// on vérifie quel type de fichier l'utilisateur a sélectionné
		String typeFile = view.getComboTypeFile().getText();

		// en fonction, on adapte le filtre de fichier pour la popup
		String extension = "bpmn";
		if (typeFile.toLowerCase().contains("uml"))
			extension = "uml";
		
		// on créé le filtre
		List<ViewerFilter> filters = new ArrayList<ViewerFilter>();
		filters.add(new MyViewerFilter(extension));
		
		// on ouvre la popup
		IFile files[] = WorkspaceResourceDialog.openFileSelection(new Shell(), "Select the " + extension + " file.", null, false, null, filters);

		// puis on fait appel au setter
		if (files.length > 0) {
			if (typeFile.toLowerCase().contains("bpmn")) {
				BpmnProcess process = BpmnParser.instance.getBpmnProcess(files[0]);
				view.setBpmnInitialProcess(process);
				try {
					process.save(System.getProperty("user.home") + "/workspace/processGenerator/gen/initial.bpmn");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} else if (typeFile.toLowerCase().contains("uml")) {
				view.setUmlInitialProcess(UmlParser.instance.getUmlProcess(files[0]));
			}
		}
	}
}
