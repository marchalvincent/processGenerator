package fr.lip6.move.processGenerator.views;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;
import fr.lip6.move.processGenerator.Element;
import fr.lip6.move.processGenerator.WorkflowPattern;
import fr.lip6.move.processGenerator.Quantity;
import fr.lip6.move.processGenerator.bpmn2.BpmnElement;
import fr.lip6.move.processGenerator.bpmn2.workflowPattern.AbstractBpmnWorkflowPattern;
import fr.lip6.move.processGenerator.uml.UmlElement;
import fr.lip6.move.processGenerator.uml.workflowPattern.AbstractUmlWorkflowPattern;


public class ProcessGeneratorView extends ViewPart {

	public static final String ID = "processgeneration.views.ProcessGeneratorView";
	private ScrolledForm form;
	private Table tableWorkflowBpmn, tableWorkflowUml;
	private Table tableMutationParameters;
	private Group groupMutationParameters;
	private Table tableBpmnElements, tableUmlElements;
	private Section sctnBpmnElements, sctnUmlElements;
	private Section sectionWorkflowBpmn, sectionWorkflowUml;
	private Text text_oclConstraint;
	private Combo comboTypeFile;


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
		form.getBody().setLayout(new GridLayout(2, true));
		toolkit.decorateFormHeading(form.getForm());

		Section sectionSizeLocation = toolkit.createSection(form.getBody(), Section.TITLE_BAR);
		sectionSizeLocation.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		sectionSizeLocation.marginWidth = 0;
		sectionSizeLocation.marginHeight = 0;
		toolkit.paintBordersFor(sectionSizeLocation);
		sectionSizeLocation.setText("Size and location");

		Composite composite = new Composite(sectionSizeLocation, SWT.NONE);
		toolkit.adapt(composite);
		toolkit.paintBordersFor(composite);
		sectionSizeLocation.setClient(composite);
		composite.setLayout(new GridLayout(1, false));

		Composite composite_1 = new Composite(composite, SWT.NONE);
		composite_1.setLayout(new GridLayout(3, false));
		composite_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		toolkit.adapt(composite_1);
		toolkit.paintBordersFor(composite_1);

		Label lblSaveLocation = new Label(composite_1, SWT.NONE);
		toolkit.adapt(lblSaveLocation, true, true);
		lblSaveLocation.setText("Save location : ");

		Label lblpath_1 = new Label(composite_1, SWT.NONE);
		toolkit.adapt(lblpath_1, true, true);
		lblpath_1.setText("/path/");

		Button btnChange = new Button(composite_1, SWT.NONE);
		toolkit.adapt(btnChange, true, true);
		btnChange.setText("Change");

		Composite composite_2 = new Composite(composite, SWT.NONE);
		composite_2.setLayout(new GridLayout(5, false));
		composite_2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		toolkit.adapt(composite_2);
		toolkit.paintBordersFor(composite_2);

		Label lblNode = new Label(composite_2, SWT.NONE);
		toolkit.adapt(lblNode, true, true);
		lblNode.setText("Node (#) : ");

		Spinner spinner_6 = new Spinner(composite_2, SWT.BORDER);
		spinner_6.setMaximum(10000);
		spinner_6.setIncrement(10);
		spinner_6.setMinimum(2);
		spinner_6.setSelection(100);
		toolkit.adapt(spinner_6);
		toolkit.paintBordersFor(spinner_6);
		new Label(composite_2, SWT.NONE);

		Label lblMargin = new Label(composite_2, SWT.NONE);
		toolkit.adapt(lblMargin, true, true);
		lblMargin.setText("Margin (%) : ");

		Spinner spinner_7 = new Spinner(composite_2, SWT.BORDER);
		spinner_7.setMaximum(500);
		spinner_7.setSelection(5);
		toolkit.adapt(spinner_7);
		toolkit.paintBordersFor(spinner_7);

		Section sctnRun = toolkit.createSection(form.getBody(), Section.TITLE_BAR);
		sctnRun.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
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

		Section sctnFile = toolkit.createSection(scrolledFormTarget.getBody(), Section.TITLE_BAR);
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

		Composite composite_3 = new Composite(compositeTarget1, SWT.NONE);
		composite_3.setLayout(new GridLayout(2, false));
		composite_3.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		toolkit.adapt(composite_3);
		toolkit.paintBordersFor(composite_3);

		Label lblFileType = new Label(composite_3, SWT.NONE);
		lblFileType.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		toolkit.adapt(lblFileType, true, true);
		lblFileType.setText("File type : ");

		comboTypeFile = new Combo(composite_3, SWT.READ_ONLY);
		comboTypeFile.setItems(new String[] {"BPMN 2.0", "UML 2.0 AD"});
		comboTypeFile.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(comboTypeFile);
		toolkit.paintBordersFor(comboTypeFile);
		comboTypeFile.select(0);

		Composite compositeLigne3 = new Composite(compositeTarget1, SWT.NONE);
		compositeLigne3.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		toolkit.adapt(compositeLigne3);
		toolkit.paintBordersFor(compositeLigne3);
		compositeLigne3.setLayout(new GridLayout(1, false));

		sctnBpmnElements = toolkit.createSection(compositeLigne3, Section.TWISTIE | Section.TITLE_BAR);
		sctnBpmnElements.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		sctnBpmnElements.marginWidth = 0;
		sctnBpmnElements.marginHeight = 0;
		toolkit.paintBordersFor(sctnBpmnElements);
		sctnBpmnElements.setText("Bpmn Elements");
		sctnBpmnElements.setExpanded(true);

		tableBpmnElements = new Table(sctnBpmnElements, SWT.BORDER | SWT.FULL_SELECTION);
		tableBpmnElements.setLinesVisible(true);
		tableBpmnElements.setHeaderVisible(true);
		toolkit.adapt(tableBpmnElements);
		toolkit.paintBordersFor(tableBpmnElements);
		sctnBpmnElements.setClient(tableBpmnElements);

		TableColumn tableColumn_2 = new TableColumn(tableBpmnElements, SWT.NONE);
		tableColumn_2.setWidth(20);

		TableColumn tableColumn_3 = new TableColumn(tableBpmnElements, SWT.NONE);
		tableColumn_3.setWidth(100);
		tableColumn_3.setText("Element name");

		TableColumn tableColumn_4 = new TableColumn(tableBpmnElements, SWT.NONE);
		tableColumn_4.setWidth(100);
		tableColumn_4.setText("Quantity");

		TableColumn tableColumn_5 = new TableColumn(tableBpmnElements, SWT.NONE);
		tableColumn_5.setWidth(100);
		tableColumn_5.setText("Number");

		sctnUmlElements = toolkit.createSection(compositeLigne3, Section.TWISTIE | Section.TITLE_BAR);
		sctnUmlElements.setEnabled(false);
		sctnUmlElements.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		sctnUmlElements.marginWidth = 0;
		sctnUmlElements.marginHeight = 0;
		toolkit.paintBordersFor(sctnUmlElements);
		sctnUmlElements.setText("Uml Elements");

		tableUmlElements = new Table(sctnUmlElements, SWT.BORDER | SWT.FULL_SELECTION);
		tableUmlElements.setLinesVisible(true);
		tableUmlElements.setHeaderVisible(true);
		toolkit.adapt(tableUmlElements);
		toolkit.paintBordersFor(tableUmlElements);
		sctnUmlElements.setClient(tableUmlElements);

		TableColumn tableColumn = new TableColumn(tableUmlElements, SWT.NONE);
		tableColumn.setWidth(20);

		TableColumn tableColumn_6 = new TableColumn(tableUmlElements, SWT.NONE);
		tableColumn_6.setWidth(100);
		tableColumn_6.setText("Element name");

		TableColumn tableColumn_7 = new TableColumn(tableUmlElements, SWT.NONE);
		tableColumn_7.setWidth(100);
		tableColumn_7.setText("Quantity");

		TableColumn tableColumn_8 = new TableColumn(tableUmlElements, SWT.NONE);
		tableColumn_8.setWidth(100);
		tableColumn_8.setText("Number");

		Section sctnWorkflow = toolkit.createSection(scrolledFormTarget.getBody(), Section.TITLE_BAR);
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

		sectionWorkflowBpmn = toolkit.createSection(compositeTarget2, Section.TWISTIE | Section.TITLE_BAR);
		sectionWorkflowBpmn.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		sectionWorkflowBpmn.marginWidth = 0;
		sectionWorkflowBpmn.marginHeight = 0;
		toolkit.paintBordersFor(sectionWorkflowBpmn);
		sectionWorkflowBpmn.setText("Bpmn workflow patterns");
		sectionWorkflowBpmn.setExpanded(true);

		tableWorkflowBpmn = new Table(sectionWorkflowBpmn, SWT.BORDER | SWT.FULL_SELECTION);
		sectionWorkflowBpmn.setClient(tableWorkflowBpmn);
		tableWorkflowBpmn.setLinesVisible(true);
		tableWorkflowBpmn.setHeaderVisible(true);
		tableWorkflowBpmn.setBounds(0, 0, 85, 85);
		toolkit.adapt(tableWorkflowBpmn);
		toolkit.paintBordersFor(tableWorkflowBpmn);

		TableColumn tableColumn_1_checkbox = new TableColumn(tableWorkflowBpmn, SWT.NONE);
		tableColumn_1_checkbox.setWidth(20);

		TableColumn tableColumnName = new TableColumn(tableWorkflowBpmn, SWT.NONE);
		tableColumnName.setWidth(148);
		tableColumnName.setText("Workflow name");

		TableColumn tableColumnQuantity = new TableColumn(tableWorkflowBpmn, SWT.NONE);
		tableColumnQuantity.setWidth(115);
		tableColumnQuantity.setText("Quantity");

		TableColumn tblclmnNumber = new TableColumn(tableWorkflowBpmn, SWT.NONE);
		tblclmnNumber.setWidth(100);
		tblclmnNumber.setText("Number");

		sectionWorkflowUml = toolkit.createSection(compositeTarget2, Section.TWISTIE | Section.TITLE_BAR);
		sectionWorkflowUml.setEnabled(false);
		sectionWorkflowUml.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		sectionWorkflowUml.marginWidth = 0;
		sectionWorkflowUml.marginHeight = 0;
		toolkit.paintBordersFor(sectionWorkflowUml);
		sectionWorkflowUml.setText("Uml workflow patterns");

		tableWorkflowUml = new Table(sectionWorkflowUml, SWT.BORDER | SWT.FULL_SELECTION);
		sectionWorkflowUml.setClient(tableWorkflowUml);
		tableWorkflowUml.setLinesVisible(true);
		tableWorkflowUml.setHeaderVisible(true);
		toolkit.adapt(tableWorkflowUml);
		toolkit.paintBordersFor(tableWorkflowUml);

		TableColumn tableColumn_1 = new TableColumn(tableWorkflowUml, SWT.NONE);
		tableColumn_1.setWidth(20);

		TableColumn tableColumn_9 = new TableColumn(tableWorkflowUml, SWT.NONE);
		tableColumn_9.setWidth(148);
		tableColumn_9.setText("Workflow name");

		TableColumn tableColumn_10 = new TableColumn(tableWorkflowUml, SWT.NONE);
		tableColumn_10.setWidth(115);
		tableColumn_10.setText("Quantity");

		TableColumn tableColumn_11 = new TableColumn(tableWorkflowUml, SWT.NONE);
		tableColumn_11.setWidth(100);
		tableColumn_11.setText("Number");

		Section sctnOclConstraints = toolkit.createSection(scrolledFormTarget.getBody(), Section.TWISTIE | Section.TITLE_BAR);
		sctnOclConstraints.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1));
		sctnOclConstraints.marginWidth = 0;
		sctnOclConstraints.marginHeight = 0;
		toolkit.paintBordersFor(sctnOclConstraints);
		sctnOclConstraints.setText("Add OCL constraints");
		sctnOclConstraints.setExpanded(true);

		Composite compositeTarget3 = new Composite(sctnOclConstraints, SWT.NONE);
		toolkit.adapt(compositeTarget3);
		toolkit.paintBordersFor(compositeTarget3);
		sctnOclConstraints.setClient(compositeTarget3);
		compositeTarget3.setLayout(new GridLayout(1, false));

		text_oclConstraint = new Text(compositeTarget3, SWT.BORDER | SWT.V_SCROLL | SWT.MULTI);
		GridData gd_text_oclConstraint = new GridData(SWT.FILL, SWT.FILL, true, false, 1, 1);
		gd_text_oclConstraint.heightHint = 71;
		text_oclConstraint.setLayoutData(gd_text_oclConstraint);
		toolkit.adapt(text_oclConstraint, true, true);

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
		btnOneSolutionFound.setText("Until # solutions found");

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
		btnGeneration.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		toolkit.adapt(btnGeneration, true, true);
		btnGeneration.setText("Until # generations");

		Spinner spinner_3 = new Spinner(compositeGA4, SWT.BORDER);
		spinner_3.setMaximum(100000);
		spinner_3.setMinimum(1);
		spinner_3.setSelection(100);
		toolkit.adapt(spinner_3);
		toolkit.paintBordersFor(spinner_3);
		new Label(compositeGA4, SWT.NONE);

		Button btnUntilStagnation = new Button(compositeGA4, SWT.CHECK);
		toolkit.adapt(btnUntilStagnation, true, true);
		btnUntilStagnation.setText("Until # stagnations");

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


		manualCode();
	}

	/**
	 * Toutes les opérations codées à la main
	 */
	private void manualCode() {

		// on remplit les tableaux d'éléments BPMN et UML
		List<String> elements = new ArrayList<String>(BpmnElement.values().length);
		for (Element elem : BpmnElement.values()) {
			elements.add(elem.toString().toLowerCase());
		}
		this.addElementToTable(tableBpmnElements, elements);

		elements = new ArrayList<String>(UmlElement.values().length);
		for (Element elem : UmlElement.values()) {
			elements.add(elem.toString().toLowerCase());
		}
		this.addElementToTable(tableUmlElements, elements);

		// on remplit les tableaux des workflow patterns
		this.setWorkflowPatternToTable(tableWorkflowBpmn, AbstractBpmnWorkflowPattern.patterns);
		this.setWorkflowPatternToTable(tableWorkflowUml, AbstractUmlWorkflowPattern.patterns);

		// les listeners
		// selection du type de fichier de sortie (bpmn, uml, etc.)
		comboTypeFile.addSelectionListener(new SelectionFileType(this));

	}

	/**
	 * Remplit le tableau des workflow patterns dynamiquement.
	 * @param table le tableau à remplir 
	 * @param liste une liste de class représentant les workflow patterns.
	 */
	private void setWorkflowPatternToTable(Table table, List<Class<? extends WorkflowPattern>> liste) {
		List<String> elements = new ArrayList<String>(liste.size());
		for (Class<? extends WorkflowPattern> clazz : liste) {
			elements.add(clazz.getSimpleName().replace("Bpmn", "").replace("Uml", ""));
		}
		this.addElementToTable(table, elements);
	}

	/**
	 * Remplit un tableau selon la liste des éléments passés en paramètre.
	 * @param table le {@link Table} à remplir.
	 * @param elements la {@link List} des éléments constituant le tableau.
	 */
	private void addElementToTable(Table table, List<String> elements) {

		// on construit les lignes
		for (int i = 0 ; i < elements.size() ; i++) {
			new TableItem(table, SWT.NONE);
		}

		// pour chaque ligne...
		TableItem[] lignes = table.getItems();
		for (int i = 0 ; i < lignes.length ; i++) {

			// la case a cocher
			Button check = new Button(table, SWT.CHECK);
			check.setText("");
			TableEditor editor = new TableEditor(table);
			editor.grabHorizontal = true;
			editor.setEditor(check, lignes[i], 0);

			// le nom de l'élément
			lignes[i].setText(1, elements.get(i));

			// le combobox
			editor = new TableEditor(table);
			CCombo combo = new CCombo(table, SWT.NONE);
			for (Quantity quantity : Quantity.values()) {
				combo.add(quantity.toString().toLowerCase());
			}
			combo.select(0);
			combo.setEditable(false);
			editor.grabHorizontal = true;
			editor.setEditor(combo, lignes[i], 2);

			// le nombre
			editor = new TableEditor(table);
			Text text = new Text(table, SWT.NONE);
			text.setText("1");
			text.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent me) {
					Text t = (Text) me.getSource();
					try {
						Integer.parseInt(t.getText());
					} catch (Exception ex) {
						t.setText("1");
					}
				}
			});
			editor.grabHorizontal = true;
			editor.setEditor(text, lignes[i], 3);
		}
	}

	public Section getBpmnElements() {
		return sctnBpmnElements;
	}

	public Section getUmlElements() {
		return sctnUmlElements;
	}

	public Section getSectionWorkflowBpmn() {
		return sectionWorkflowBpmn;
	}
	
	public Section getSectionWorkflowUml() {
		return sectionWorkflowUml;
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		form.setFocus();
	}
}