package fr.lip6.move.processGenerator.views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;


public class ProcessGeneratorView extends ViewPart {

	public static final String ID = "processgeneration.views.ProcessGeneratorView";
	private ScrolledForm form;
	private Table tableWorkflow;
	private Table tableElements;
	private Table tableMutationParameters;
	private Group groupMutationParameters;

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
		gd = new GridData(SWT.FILL, SWT.FILL, true, false);
		gd.horizontalSpan = 1;
		parent.setLayout(new FillLayout(SWT.HORIZONTAL));

		TabFolder tabFolder = new TabFolder(parent, SWT.NONE);
		toolkit.adapt(tabFolder);
		toolkit.paintBordersFor(tabFolder);

		TabItem tabRun = new TabItem(tabFolder, SWT.NONE);
		tabRun.setText("Run");
		form = toolkit.createScrolledForm(tabFolder);
		tabRun.setControl(form);
		form.setDelayedReflow(true);
		form.getBody().setLayout(new GridLayout(1, true));
		toolkit.decorateFormHeading(form.getForm());

		Section sctnRun = toolkit.createSection(form.getBody(), Section.TITLE_BAR);
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

		Label lblResult = new Label(compSctnRun, SWT.NONE);
		toolkit.adapt(lblResult, true, true);

		TabItem tbtmTargetConfiguration = new TabItem(tabFolder, SWT.NONE);
		tbtmTargetConfiguration.setText("Target configuration");

		ScrolledForm scrolledFormTarget = toolkit.createScrolledForm(tabFolder);
		scrolledFormTarget.setDelayedReflow(true);
		tbtmTargetConfiguration.setControl(scrolledFormTarget);
		toolkit.paintBordersFor(scrolledFormTarget);
		scrolledFormTarget.getBody().setLayout(new GridLayout(2, true));

		Section sctnFile = toolkit.createSection(scrolledFormTarget.getBody(), Section.TWISTIE | Section.TITLE_BAR);
		sctnFile.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		sctnFile.marginWidth = 0;
		sctnFile.marginHeight = 0;
		toolkit.paintBordersFor(sctnFile);
		sctnFile.setText("File information");
		sctnFile.setExpanded(true);

		Composite compositeTarget1 = new Composite(sctnFile, SWT.NONE);
		toolkit.adapt(compositeTarget1);
		toolkit.paintBordersFor(compositeTarget1);
		sctnFile.setClient(compositeTarget1);
		compositeTarget1.setLayout(new GridLayout(1, false));

		Composite compositeLigne1 = new Composite(compositeTarget1, SWT.NONE);
		compositeLigne1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		toolkit.adapt(compositeLigne1);
		toolkit.paintBordersFor(compositeLigne1);
		compositeLigne1.setLayout(new GridLayout(3, false));

		Label lblSaveLocation_1 = new Label(compositeLigne1, SWT.NONE);
		toolkit.adapt(lblSaveLocation_1, true, true);
		lblSaveLocation_1.setText("Save location");

		Label lblpath = new Label(compositeLigne1, SWT.NONE);
		lblpath.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		toolkit.adapt(lblpath, true, true);
		lblpath.setText("/path/");

		Button btnChange_1 = new Button(compositeLigne1, SWT.NONE);
		toolkit.adapt(btnChange_1, true, true);
		btnChange_1.setText("Change");

		Composite compositeLigne2 = new Composite(compositeTarget1, SWT.NONE);
		compositeLigne2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		toolkit.adapt(compositeLigne2);
		toolkit.paintBordersFor(compositeLigne2);
		compositeLigne2.setLayout(new GridLayout(5, false));

		Label lblNodes = new Label(compositeLigne2, SWT.NONE);
		toolkit.adapt(lblNodes, true, true);
		lblNodes.setText("Nodes (#) :");

		Spinner spinnerNodes = new Spinner(compositeLigne2, SWT.BORDER);
		spinnerNodes.setMaximum(10000);
		spinnerNodes.setIncrement(10);
		spinnerNodes.setMinimum(2);
		spinnerNodes.setSelection(100);
		toolkit.adapt(spinnerNodes);
		toolkit.paintBordersFor(spinnerNodes);
		new Label(compositeLigne2, SWT.NONE);

		Label lblMargin_1 = new Label(compositeLigne2, SWT.NONE);
		toolkit.adapt(lblMargin_1, true, true);
		lblMargin_1.setText("Margin (%) :");

		Spinner spinnerMargin = new Spinner(compositeLigne2, SWT.BORDER);
		spinnerMargin.setSelection(5);
		toolkit.adapt(spinnerMargin);
		toolkit.paintBordersFor(spinnerMargin);
		
		Composite compositeLigne3 = new Composite(compositeTarget1, SWT.NONE);
		compositeLigne3.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		toolkit.adapt(compositeLigne3);
		toolkit.paintBordersFor(compositeLigne3);
		compositeLigne3.setLayout(new GridLayout(1, false));
		
		Group groupFileType = new Group(compositeLigne3, SWT.NONE);
		groupFileType.setText("File type");
		groupFileType.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		toolkit.adapt(groupFileType);
		toolkit.paintBordersFor(groupFileType);
		groupFileType.setLayout(new GridLayout(2, true));
		
		Button btnBpmn = new Button(groupFileType, SWT.CHECK);
		btnBpmn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnBpmn.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		toolkit.adapt(btnBpmn, true, true);
		btnBpmn.setText("BPMN 2.0");
		
		Button btnUmlAd = new Button(groupFileType, SWT.CHECK);
		btnUmlAd.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(btnUmlAd, true, true);
		btnUmlAd.setText("UML2.0 AD");

		Section sctnWorkflow = toolkit.createSection(scrolledFormTarget.getBody(), Section.TWISTIE | Section.TITLE_BAR);
		sctnWorkflow.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 2));
		sctnWorkflow.marginWidth = 0;
		sctnWorkflow.marginHeight = 0;
		toolkit.paintBordersFor(sctnWorkflow);
		sctnWorkflow.setText("Workflow patterns");
		sctnWorkflow.setExpanded(true);

		Composite compositeTarget2 = new Composite(sctnWorkflow, SWT.NONE);
		toolkit.adapt(compositeTarget2);
		toolkit.paintBordersFor(compositeTarget2);
		sctnWorkflow.setClient(compositeTarget2);
		compositeTarget2.setLayout(new GridLayout(1, false));

		tableWorkflow = new Table(compositeTarget2, SWT.BORDER | SWT.FULL_SELECTION);
		tableWorkflow.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		tableWorkflow.setLinesVisible(true);
		tableWorkflow.setHeaderVisible(true);
		tableWorkflow.setBounds(0, 0, 85, 85);
		toolkit.adapt(tableWorkflow);
		toolkit.paintBordersFor(tableWorkflow);

		TableColumn tableColumnName = new TableColumn(tableWorkflow, SWT.NONE);
		tableColumnName.setWidth(148);
		tableColumnName.setText("Workflow name");

		TableColumn tableColumnQuantity = new TableColumn(tableWorkflow, SWT.NONE);
		tableColumnQuantity.setWidth(115);
		tableColumnQuantity.setText("Quantity");

		Section sctnElements = toolkit.createSection(scrolledFormTarget.getBody(), Section.TWISTIE | Section.TITLE_BAR);
		sctnElements.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		sctnElements.marginWidth = 0;
		sctnElements.marginHeight = 0;
		toolkit.paintBordersFor(sctnElements);
		sctnElements.setText("Elements");
		sctnElements.setExpanded(true);

		Composite compositeTarget3 = new Composite(sctnElements, SWT.NONE);
		toolkit.adapt(compositeTarget3);
		toolkit.paintBordersFor(compositeTarget3);
		sctnElements.setClient(compositeTarget3);
		compositeTarget3.setLayout(new GridLayout(1, false));

		tableElements = new Table(compositeTarget3, SWT.BORDER | SWT.FULL_SELECTION);
		tableElements.setLinesVisible(true);
		tableElements.setHeaderVisible(true);
		tableElements.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		toolkit.adapt(tableElements);
		toolkit.paintBordersFor(tableElements);

		TableColumn tableColumn = new TableColumn(tableElements, SWT.NONE);
		tableColumn.setWidth(100);
		tableColumn.setText("Element name");

		TableColumn tableColumn_1 = new TableColumn(tableElements, SWT.NONE);
		tableColumn_1.setWidth(100);
		tableColumn_1.setText("Quantity");

		TableColumn tableColumn_2 = new TableColumn(tableElements, SWT.NONE);
		tableColumn_2.setWidth(100);
		tableColumn_2.setText("Number");

		Button btnAddOclConstraints_1 = new Button(compositeTarget3, SWT.NONE);
		toolkit.adapt(btnAddOclConstraints_1, true, true);
		btnAddOclConstraints_1.setText("Add OCL constraints");

		TabItem tbtmGeneticAlgorithmConfiguration = new TabItem(tabFolder, SWT.NONE);
		tbtmGeneticAlgorithmConfiguration.setText("Genetic algorithm configuration");

		ScrolledForm scrolledForm = toolkit.createScrolledForm(tabFolder);
		scrolledForm.setDelayedReflow(true);
		tbtmGeneticAlgorithmConfiguration.setControl(scrolledForm);
		toolkit.paintBordersFor(scrolledForm);
		scrolledForm.getBody().setLayout(new GridLayout(2, false));

		Section sctnInitial = toolkit.createSection(scrolledForm.getBody(), Section.TWISTIE | Section.TITLE_BAR);
		sctnInitial.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		sctnInitial.marginWidth = 0;
		sctnInitial.marginHeight = 0;
		toolkit.paintBordersFor(sctnInitial);
		sctnInitial.setText("Initial population");
		sctnInitial.setExpanded(true);

		Composite compositeGA1 = new Composite(sctnInitial, SWT.NONE);
		toolkit.adapt(compositeGA1);
		toolkit.paintBordersFor(compositeGA1);
		sctnInitial.setClient(compositeGA1);
		compositeGA1.setLayout(new GridLayout(4, false));

		Label lblPopulation = new Label(compositeGA1, SWT.NONE);
		toolkit.adapt(lblPopulation, true, true);
		lblPopulation.setText("Population (#) :");

		Spinner spinner = new Spinner(compositeGA1, SWT.BORDER);
		spinner.setIncrement(10);
		spinner.setMaximum(10000);
		spinner.setMinimum(1);
		spinner.setSelection(100);
		toolkit.adapt(spinner);
		toolkit.paintBordersFor(spinner);
		new Label(compositeGA1, SWT.NONE);

		Button btnSetInitialProcess_1 = new Button(compositeGA1, SWT.NONE);
		toolkit.adapt(btnSetInitialProcess_1, true, true);
		btnSetInitialProcess_1.setText("Set initial process");

		Section sctnSelection = toolkit.createSection(scrolledForm.getBody(), Section.TWISTIE | Section.TITLE_BAR);
		sctnSelection.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		sctnSelection.marginWidth = 0;
		sctnSelection.marginHeight = 0;
		toolkit.paintBordersFor(sctnSelection);
		sctnSelection.setText("Elitism and selection strategies");
		sctnSelection.setExpanded(true);

		Composite compositeGA2 = new Composite(sctnSelection, SWT.NONE);
		toolkit.adapt(compositeGA2);
		toolkit.paintBordersFor(compositeGA2);
		sctnSelection.setClient(compositeGA2);
		compositeGA2.setLayout(new GridLayout(2, false));

		Label lblElitism_1 = new Label(compositeGA2, SWT.NONE);
		toolkit.adapt(lblElitism_1, true, true);
		lblElitism_1.setText("Elitism :");

		Spinner spinner_1 = new Spinner(compositeGA2, SWT.BORDER);
		spinner_1.setMaximum(1000);
		spinner_1.setSelection(5);
		toolkit.adapt(spinner_1);
		toolkit.paintBordersFor(spinner_1);

		Label lblStrategySelection_1 = new Label(compositeGA2, SWT.NONE);
		lblStrategySelection_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		toolkit.adapt(lblStrategySelection_1, true, true);
		lblStrategySelection_1.setText("Strategy :");

		Combo comboStrategySelection = new Combo(compositeGA2, SWT.READ_ONLY);
		comboStrategySelection.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		comboStrategySelection.setVisibleItemCount(10);
		comboStrategySelection.setItems(new String[] {"Roulette wheel selection", "Stochastic universal sampling", "Rank selection", "Tournament selection"});
		toolkit.adapt(comboStrategySelection);
		toolkit.paintBordersFor(comboStrategySelection);
		comboStrategySelection.setText("Roulette wheel selection");

		Section sctnEvolutionary = toolkit.createSection(scrolledForm.getBody(), Section.TWISTIE | Section.TITLE_BAR);
		sctnEvolutionary.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		sctnEvolutionary.marginWidth = 0;
		sctnEvolutionary.marginHeight = 0;
		toolkit.paintBordersFor(sctnEvolutionary);
		sctnEvolutionary.setText("Evolutionary operations");
		sctnEvolutionary.setExpanded(true);

		Composite compositeGA3 = new Composite(sctnEvolutionary, SWT.NONE);
		toolkit.adapt(compositeGA3);
		toolkit.paintBordersFor(compositeGA3);
		sctnEvolutionary.setClient(compositeGA3);
		compositeGA3.setLayout(new GridLayout(2, true));

		Button checkMutation = new Button(compositeGA3, SWT.CHECK);
		checkMutation.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				groupMutationParameters.setVisible(!((Button) e.getSource()).getSelection());
			}
		});
		checkMutation.setText("Mutation");
		checkMutation.setBounds(0, 0, 12, 25);
		toolkit.adapt(checkMutation, true, true);

		Button checkCrossover = new Button(compositeGA3, SWT.CHECK);
		checkCrossover.setText("Crossover");
		checkCrossover.setBounds(0, 0, 12, 25);
		toolkit.adapt(checkCrossover, true, true);

		groupMutationParameters = new Group(compositeGA3, SWT.NONE);
		groupMutationParameters.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		groupMutationParameters.setVisible(false);
		groupMutationParameters.setText("Mutation parameters");
		groupMutationParameters.setBounds(0, 0, 70, 82);
		toolkit.adapt(groupMutationParameters);
		toolkit.paintBordersFor(groupMutationParameters);
		groupMutationParameters.setLayout(new GridLayout(1, false));

		tableMutationParameters = new Table(groupMutationParameters, SWT.BORDER | SWT.FULL_SELECTION);
		tableMutationParameters.setLinesVisible(true);
		tableMutationParameters.setHeaderVisible(true);
		tableMutationParameters.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		toolkit.adapt(tableMutationParameters);
		toolkit.paintBordersFor(tableMutationParameters);

		TableColumn tableColumnChangePatternName = new TableColumn(tableMutationParameters, SWT.NONE);
		tableColumnChangePatternName.setWidth(100);
		tableColumnChangePatternName.setText("Change pattern");

		TableColumn tableColumnProbability = new TableColumn(tableMutationParameters, SWT.NONE);
		tableColumnProbability.setWidth(100);
		tableColumnProbability.setText("Probability");

		Button button_2 = new Button(groupMutationParameters, SWT.CHECK);
		button_2.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1));
		button_2.setText("auto adapt mutation with workflow patterns");
		toolkit.adapt(button_2, true, true);

		Section sctnTermination = toolkit.createSection(scrolledForm.getBody(), Section.TWISTIE | Section.TITLE_BAR);
		sctnTermination.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		sctnTermination.marginWidth = 0;
		sctnTermination.marginHeight = 0;
		toolkit.paintBordersFor(sctnTermination);
		sctnTermination.setText("Termination condition");
		sctnTermination.setExpanded(true);

		Composite compositeGA4 = new Composite(sctnTermination, SWT.NONE);
		toolkit.adapt(compositeGA4);
		toolkit.paintBordersFor(compositeGA4);
		sctnTermination.setClient(compositeGA4);
		compositeGA4.setLayout(new GridLayout(3, false));

		Group groupInfo = new Group(compositeGA4, SWT.NONE);
		groupInfo.setText("Info");
		groupInfo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 3, 1));
		toolkit.adapt(groupInfo);
		toolkit.paintBordersFor(groupInfo);
		groupInfo.setLayout(new GridLayout(1, false));

		Label lblInfo = new Label(groupInfo, SWT.NONE);
		toolkit.adapt(lblInfo, true, true);
		lblInfo.setText("If multiple termination conditions are specified, \r\nthe evolution will stop as soon as any one of them is satisfied.");

		Button btnOneSolutionFound = new Button(compositeGA4, SWT.CHECK);
		toolkit.adapt(btnOneSolutionFound, true, true);
		btnOneSolutionFound.setText("Until # solution found");

		Spinner spinner_4 = new Spinner(compositeGA4, SWT.BORDER);
		spinner_4.setMaximum(100000);
		spinner_4.setMinimum(1);
		spinner_4.setSelection(1);
		toolkit.adapt(spinner_4);
		toolkit.paintBordersFor(spinner_4);
		new Label(compositeGA4, SWT.NONE);

		Button btnDuringSec = new Button(compositeGA4, SWT.CHECK);
		toolkit.adapt(btnDuringSec, true, true);
		btnDuringSec.setText("During # sec");

		Spinner spinner_2 = new Spinner(compositeGA4, SWT.BORDER);
		spinner_2.setMaximum(100000);
		spinner_2.setMinimum(1);
		spinner_2.setSelection(60);
		toolkit.adapt(spinner_2);
		toolkit.paintBordersFor(spinner_2);
		new Label(compositeGA4, SWT.NONE);

		Button btnGeneration = new Button(compositeGA4, SWT.CHECK);
		toolkit.adapt(btnGeneration, true, true);
		btnGeneration.setText("Until # generation");

		Spinner spinner_3 = new Spinner(compositeGA4, SWT.BORDER);
		spinner_3.setMaximum(100000);
		spinner_3.setMinimum(1);
		spinner_3.setSelection(100);
		toolkit.adapt(spinner_3);
		toolkit.paintBordersFor(spinner_3);
		new Label(compositeGA4, SWT.NONE);

		Button btnUntilStagnation = new Button(compositeGA4, SWT.CHECK);
		toolkit.adapt(btnUntilStagnation, true, true);
		btnUntilStagnation.setText("Until # stagnation");

		Spinner spinner_5 = new Spinner(compositeGA4, SWT.BORDER);
		spinner_5.setMaximum(100000);
		spinner_5.setMinimum(1);
		spinner_5.setSelection(100);
		toolkit.adapt(spinner_5);
		toolkit.paintBordersFor(spinner_5);
		new Label(compositeGA4, SWT.NONE);
		new Label(scrolledForm.getBody(), SWT.NONE);
		new Label(scrolledForm.getBody(), SWT.NONE);
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