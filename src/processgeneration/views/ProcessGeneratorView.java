package processgeneration.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;


public class ProcessGeneratorView extends ViewPart {

	public static final String ID = "processgeneration.views.ProcessGeneratorView";
	private ScrolledForm form;
	private Table tableMutationProba;
	private Group grpMutationParameters;
	private Table tableWorkflow;
	private Table tableElements;

	/**
	 * The constructor.
	 */
	public ProcessGeneratorView() {}

	/**
	 * This is a callback that will allow us to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {

		GridData gd = null;

		FormToolkit toolkit = new FormToolkit(parent.getDisplay());
		parent.setLayout(new GridLayout(1, false));
		form = toolkit.createScrolledForm(parent);
		form.setDelayedReflow(true);
		form.getBody().setLayout(new GridLayout(2, true));
		toolkit.decorateFormHeading(form.getForm());

		Section sctnTarget = toolkit.createSection(form.getBody(), Section.TWISTIE | Section.TITLE_BAR);
		GridData gd_sctnTarget = new GridData(SWT.FILL, SWT.FILL, false, false);
		gd_sctnTarget.verticalSpan = 2;
		gd_sctnTarget.horizontalSpan = 1;
		sctnTarget.setLayoutData(gd_sctnTarget);
		sctnTarget.setText("Target configuration");
		sctnTarget.setExpanded(true);
		sctnTarget.marginWidth = 0;
		sctnTarget.marginHeight = 0;

		Composite compSctnConfig = toolkit.createComposite(sctnTarget, SWT.FULL_SELECTION);

		toolkit.paintBordersFor(compSctnConfig);
		sctnTarget.setClient(compSctnConfig);
		compSctnConfig.setLayout(new GridLayout(1, false));

		Composite compSaveLocation = toolkit.createComposite(compSctnConfig, SWT.FULL_SELECTION);
		compSaveLocation.setLayout(new GridLayout(3, false));
		compSaveLocation.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblSaveLocation = new Label(compSaveLocation, SWT.NONE);
		toolkit.adapt(lblSaveLocation, true, true);
		lblSaveLocation.setText("Save location");

		Label lblPath = new Label(compSaveLocation, SWT.NONE);
		toolkit.adapt(lblPath, true, true);
		lblPath.setText("/path/");

		Button btnChange = new Button(compSaveLocation, SWT.NONE);
		toolkit.adapt(btnChange, true, true);
		btnChange.setText("Change");

		Composite compModelSize = toolkit.createComposite(compSctnConfig, SWT.FULL_SELECTION);
		compModelSize.setLayout(new GridLayout(6, false));
		compModelSize.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));

		Label lblNbNodes = new Label(compModelSize, SWT.NONE);
		toolkit.adapt(lblNbNodes, true, true);
		lblNbNodes.setText("Nb nodes :");

		Spinner spinnerNbNodes = new Spinner(compModelSize, SWT.BORDER);
		spinnerNbNodes.setIncrement(10);
		spinnerNbNodes.setMaximum(10000);
		spinnerNbNodes.setMinimum(2);
		spinnerNbNodes.setSelection(100);
		toolkit.adapt(spinnerNbNodes);
		toolkit.paintBordersFor(spinnerNbNodes);
		new Label(compModelSize, SWT.NONE);

		Label lblMargin = new Label(compModelSize, SWT.NONE);
		toolkit.adapt(lblMargin, true, true);
		lblMargin.setText("Margin :");

		Spinner spinner = new Spinner(compModelSize, SWT.BORDER);
		spinner.setIncrement(5);
		spinner.setMaximum(500);
		spinner.setSelection(10);
		toolkit.adapt(spinner);
		toolkit.paintBordersFor(spinner);

		Label label = new Label(compModelSize, SWT.NONE);
		toolkit.adapt(label, true, true);
		label.setText("%");

		Composite compWorkflowPatterns = new Composite(compSctnConfig, SWT.NONE);
		GridData gd_compWorkflowPatterns = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_compWorkflowPatterns.widthHint = 307;
		compWorkflowPatterns.setLayoutData(gd_compWorkflowPatterns);
		toolkit.adapt(compWorkflowPatterns);
		toolkit.paintBordersFor(compWorkflowPatterns);
		compWorkflowPatterns.setLayout(new GridLayout(1, false));

		Group grpWorkflowPatterns = new Group(compWorkflowPatterns, SWT.NONE);
		grpWorkflowPatterns.setLayout(new GridLayout(1, false));
		GridData gd_grpWorkflowPatterns = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_grpWorkflowPatterns.widthHint = 301;
		grpWorkflowPatterns.setLayoutData(gd_grpWorkflowPatterns);
		grpWorkflowPatterns.setText("Workflow patterns");
		toolkit.adapt(grpWorkflowPatterns);
		toolkit.paintBordersFor(grpWorkflowPatterns);

		tableWorkflow = new Table(grpWorkflowPatterns, SWT.BORDER | SWT.FULL_SELECTION);
		tableWorkflow.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(tableWorkflow);
		toolkit.paintBordersFor(tableWorkflow);
		tableWorkflow.setHeaderVisible(true);
		tableWorkflow.setLinesVisible(true);

		TableColumn tblclmnWorkflowName = new TableColumn(tableWorkflow, SWT.NONE);
		tblclmnWorkflowName.setWidth(148);
		tblclmnWorkflowName.setText("Workflow name");

		TableColumn tblclmnQuantity = new TableColumn(tableWorkflow, SWT.NONE);
		tblclmnQuantity.setWidth(115);
		tblclmnQuantity.setText("Quantity");

		Composite compElements = new Composite(compSctnConfig, SWT.NONE);
		compElements.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(compElements);
		toolkit.paintBordersFor(compElements);
		compElements.setLayout(new GridLayout(1, false));

		Group grpElements = new Group(compElements, SWT.NONE);
		grpElements.setLayout(new GridLayout(1, false));
		grpElements.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpElements.setText("Elements");
		toolkit.adapt(grpElements);
		toolkit.paintBordersFor(grpElements);

		tableElements = new Table(grpElements, SWT.BORDER | SWT.FULL_SELECTION);
		tableElements.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(tableElements);
		toolkit.paintBordersFor(tableElements);
		tableElements.setHeaderVisible(true);
		tableElements.setLinesVisible(true);

		TableColumn tblclmnElementName = new TableColumn(tableElements, SWT.NONE);
		tblclmnElementName.setWidth(100);
		tblclmnElementName.setText("Element name");

		TableColumn tblclmnQuantity_1 = new TableColumn(tableElements, SWT.NONE);
		tblclmnQuantity_1.setWidth(100);
		tblclmnQuantity_1.setText("Quantity");

		TableColumn tblclmnNumber = new TableColumn(tableElements, SWT.NONE);
		tblclmnNumber.setWidth(100);
		tblclmnNumber.setText("Number");

		Composite compAddOCL = new Composite(compSctnConfig, SWT.NONE);
		toolkit.adapt(compAddOCL);
		toolkit.paintBordersFor(compAddOCL);
		compAddOCL.setLayout(new GridLayout(1, false));

		Button btnAddOclConstraints = new Button(compAddOCL, SWT.NONE);
		toolkit.adapt(btnAddOclConstraints, true, true);
		btnAddOclConstraints.setText("Add OCL constraints");

		Section sctnRun = toolkit.createSection(form.getBody(), Section.TWISTIE | Section.TITLE_BAR);
		sctnRun.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		sctnRun.marginWidth = 0;
		sctnRun.marginHeight = 0;
		toolkit.paintBordersFor(sctnRun);
		sctnRun.setText("Run");
		sctnRun.setExpanded(true);

		Composite compSctnRun = new Composite(sctnRun, SWT.NONE);
		toolkit.adapt(compSctnRun);
		toolkit.paintBordersFor(compSctnRun);
		sctnRun.setClient(compSctnRun);
		compSctnRun.setLayout(new GridLayout(1, false));

		Composite compRun = new Composite(compSctnRun, SWT.NONE);
		compRun.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(compRun);
		toolkit.paintBordersFor(compRun);
		compRun.setLayout(new GridLayout(2, false));

		Button btnStart = new Button(compRun, SWT.NONE);
		toolkit.adapt(btnStart, true, true);
		btnStart.setText("Start");

		Button btnStop = new Button(compRun, SWT.NONE);
		toolkit.adapt(btnStop, true, true);
		btnStop.setText("Stop");

		Label lblResult = new Label(compRun, SWT.NONE);
		toolkit.adapt(lblResult, true, true);
		new Label(compRun, SWT.NONE);
		gd = new GridData(SWT.FILL, SWT.FILL, true, false);
		gd.horizontalSpan = 1;

		Section sctnAlgoConfig = toolkit.createSection(form.getBody(), Section.TWISTIE | Section.TITLE_BAR);
		sctnAlgoConfig.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
		sctnAlgoConfig.marginWidth = 0;
		sctnAlgoConfig.marginHeight = 0;
		toolkit.paintBordersFor(sctnAlgoConfig);
		sctnAlgoConfig.setText("Genetic algorithm configuration");
		sctnAlgoConfig.setExpanded(true);

		Composite compSctnAlgoConfig = new Composite(sctnAlgoConfig, SWT.NONE);
		toolkit.adapt(compSctnAlgoConfig);
		toolkit.paintBordersFor(compSctnAlgoConfig);
		sctnAlgoConfig.setClient(compSctnAlgoConfig);
		compSctnAlgoConfig.setLayout(new GridLayout(1, false));

		Composite algoInit = new Composite(compSctnAlgoConfig, SWT.NONE);
		GridData gd_algoInit = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_algoInit.widthHint = 309;
		algoInit.setLayoutData(gd_algoInit);
		toolkit.adapt(algoInit);
		toolkit.paintBordersFor(algoInit);
		GridLayout gl_algoInit = new GridLayout(5, false);
		gl_algoInit.marginHeight = 3;
		algoInit.setLayout(gl_algoInit);

		Label lblPopulationSize = new Label(algoInit, SWT.NONE);
		toolkit.adapt(lblPopulationSize, true, true);
		lblPopulationSize.setText("Population size :");

		Spinner spinnerPopulationSize = new Spinner(algoInit, SWT.BORDER);
		spinnerPopulationSize.setIncrement(10);
		spinnerPopulationSize.setMaximum(1000);
		spinnerPopulationSize.setMinimum(1);
		spinnerPopulationSize.setSelection(20);
		toolkit.adapt(spinnerPopulationSize);
		toolkit.paintBordersFor(spinnerPopulationSize);
		new Label(algoInit, SWT.NONE);

		Label lblElitism = new Label(algoInit, SWT.NONE);
		toolkit.adapt(lblElitism, true, true);
		lblElitism.setText("Elitism :");

		Spinner spinnerElitism = new Spinner(algoInit, SWT.BORDER);
		spinnerElitism.setSelection(2);
		toolkit.adapt(spinnerElitism);
		toolkit.paintBordersFor(spinnerElitism);

		Button btnSetInitialProcess = new Button(algoInit, SWT.NONE);
		btnSetInitialProcess.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		toolkit.adapt(btnSetInitialProcess, true, true);
		btnSetInitialProcess.setText("Set initial process");
		new Label(algoInit, SWT.NONE);
		new Label(algoInit, SWT.NONE);
		new Label(algoInit, SWT.NONE);

		Composite algoEvolution = new Composite(compSctnAlgoConfig, SWT.NONE);
		algoEvolution.setLayout(new GridLayout(1, false));
		GridData gd_algoEvolution = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd_algoEvolution.widthHint = 309;
		algoEvolution.setLayoutData(gd_algoEvolution);
		toolkit.adapt(algoEvolution);
		toolkit.paintBordersFor(algoEvolution);

		Group grpEvolutionaryOperations = new Group(algoEvolution, SWT.NONE);
		grpEvolutionaryOperations.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		grpEvolutionaryOperations.setText("Evolutionary operations");
		toolkit.adapt(grpEvolutionaryOperations);
		toolkit.paintBordersFor(grpEvolutionaryOperations);
		grpEvolutionaryOperations.setLayout(new GridLayout(2, true));

		Button btnMutation = new Button(grpEvolutionaryOperations, SWT.CHECK);
		btnMutation.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				grpMutationParameters.setVisible(!((Button) e.getSource()).getSelection());
			}
		});
		toolkit.adapt(btnMutation, true, true);
		btnMutation.setText("Mutation");

		Button btnCrossover = new Button(grpEvolutionaryOperations, SWT.CHECK);
		toolkit.adapt(btnCrossover, true, true);
		btnCrossover.setText("Crossover");

		grpMutationParameters = new Group(grpEvolutionaryOperations, SWT.NONE);
		grpMutationParameters.setVisible(false);
		grpMutationParameters.setText("Mutation parameters");
		grpMutationParameters.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		toolkit.adapt(grpMutationParameters);
		toolkit.paintBordersFor(grpMutationParameters);
		grpMutationParameters.setLayout(new GridLayout(2, false));

		tableMutationProba = new Table(grpMutationParameters, SWT.BORDER | SWT.FULL_SELECTION);
		tableMutationProba.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		toolkit.adapt(tableMutationProba);
		toolkit.paintBordersFor(tableMutationProba);
		tableMutationProba.setHeaderVisible(true);
		tableMutationProba.setLinesVisible(true);

		TableColumn tblclmnChangePattern = new TableColumn(tableMutationProba, SWT.NONE);
		tblclmnChangePattern.setWidth(100);
		tblclmnChangePattern.setText("Change pattern");

		TableColumn tblclmnProbability = new TableColumn(tableMutationProba, SWT.NONE);
		tblclmnProbability.setWidth(100);
		tblclmnProbability.setText("Probability");

		Button btnAutoAdaptMutation = new Button(grpMutationParameters, SWT.CHECK);
		btnAutoAdaptMutation.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		toolkit.adapt(btnAutoAdaptMutation, true, true);
		btnAutoAdaptMutation.setText("auto adapt mutation with workflow patterns");

		Composite algoSelection = new Composite(compSctnAlgoConfig, SWT.NONE);
		algoSelection.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(algoSelection);
		toolkit.paintBordersFor(algoSelection);
		GridLayout gl_algoSelection = new GridLayout(2, false);
		gl_algoSelection.marginHeight = 3;
		algoSelection.setLayout(gl_algoSelection);

		Label lblStrategySelection = new Label(algoSelection, SWT.NONE);
		lblStrategySelection.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblStrategySelection.setBounds(0, 0, 55, 15);
		toolkit.adapt(lblStrategySelection, true, true);
		lblStrategySelection.setText("Strategy selection :");

		Combo combo = new Combo(algoSelection, SWT.NONE);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(combo);
		toolkit.paintBordersFor(combo);
		combo.setText("RouletteWheelSelection");

		Composite algoTerminaison = new Composite(compSctnAlgoConfig, SWT.NONE);
		algoTerminaison.setLayout(new GridLayout(1, false));
		algoTerminaison.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		toolkit.adapt(algoTerminaison);
		toolkit.paintBordersFor(algoTerminaison);

		Group groupTerminaison = new Group(algoTerminaison, SWT.NONE);
		groupTerminaison.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		groupTerminaison.setText("Terminaison condition");
		groupTerminaison.setBounds(0, 0, 70, 82);
		toolkit.adapt(groupTerminaison);
		toolkit.paintBordersFor(groupTerminaison);
		new Label(form.getBody(), SWT.NONE);
		new Label(form.getBody(), SWT.NONE);
		new Label(form.getBody(), SWT.NONE);
		new Label(form.getBody(), SWT.NONE);
		new Label(form.getBody(), SWT.NONE);
		new Label(form.getBody(), SWT.NONE);
		new Label(form.getBody(), SWT.NONE);
		new Label(form.getBody(), SWT.NONE);
		new Label(form.getBody(), SWT.NONE);
		new Label(form.getBody(), SWT.NONE);
		new Label(form.getBody(), SWT.NONE);
		new Label(form.getBody(), SWT.NONE);
		new Label(form.getBody(), SWT.NONE);
		new Label(form.getBody(), SWT.NONE);
		new Label(form.getBody(), SWT.NONE);
		new Label(form.getBody(), SWT.NONE);
		new Label(form.getBody(), SWT.NONE);
		new Label(form.getBody(), SWT.NONE);
		new Label(form.getBody(), SWT.NONE);
		new Label(form.getBody(), SWT.NONE);
		new Label(form.getBody(), SWT.NONE);
		new Label(form.getBody(), SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 2;
		GridData gd_spnNode = new GridData(GridData.VERTICAL_ALIGN_CENTER);
		gd_spnNode.widthHint = 23;
		GridData gd_spnMarge = new GridData(GridData.VERTICAL_ALIGN_CENTER);
		gd_spnMarge.widthHint = 19;

	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		form.setFocus();
	}
}