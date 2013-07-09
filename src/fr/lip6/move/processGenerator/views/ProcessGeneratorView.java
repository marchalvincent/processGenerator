package fr.lip6.move.processGenerator.views;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.wb.swt.ResourceManager;
import fr.lip6.move.processGenerator.ConfigurationManager;
import fr.lip6.move.processGenerator.EQuantity;
import fr.lip6.move.processGenerator.IEnumElement;
import fr.lip6.move.processGenerator.IHierarchicalEnum;
import fr.lip6.move.processGenerator.Utils;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.bpmn2.EBpmnElement;
import fr.lip6.move.processGenerator.bpmn2.constraints.EBpmnWorkflowPattern;
import fr.lip6.move.processGenerator.bpmn2.ga.cp.EBpmnChangePattern;
import fr.lip6.move.processGenerator.constraint.IEnumWorkflowPattern;
import fr.lip6.move.processGenerator.ga.ESelectionStrategy;
import fr.lip6.move.processGenerator.ga.IEnumChangePattern;
import fr.lip6.move.processGenerator.uml2.EUmlElement;
import fr.lip6.move.processGenerator.uml2.UmlProcess;
import fr.lip6.move.processGenerator.uml2.constraints.EUmlWorkflowPattern;
import fr.lip6.move.processGenerator.uml2.ga.cp.EUmlChangePattern;

/**
 * Représente la vue générale du plugin.
 * 
 * @author Vincent
 * 
 */
public class ProcessGeneratorView extends ViewPart {
	
	public static final String ID = "ProcessGenerator.views.ProcessGeneratorView";
	private ScrolledForm form;
	private Group groupMutationParameters, grpElementsParameters;
	private Text text_oclConstraint;
	private Combo comboTypeFile, comboStrategySelection;
	private Spinner spinner_nbNode, spinner_marginNbNode, spinnerNbPopulation, spinnerElitism, spinnerUntilSecondes,
			spinnerUntilGeneration, spinnerUntilStagnation, spinnerSizeWeight, spinnerElementWeight, spinnerWorkflowWeight,
			spinnerManualOclWeight;
	private Button btnStart, btnStop, btnSetInitialProcess, checkMutation, checkCrossover, btnOneSolutionFound, btnDuringSec,
			btnGeneration, btnUntilStagnation, btnChange;
	private Label lblResult, lblpath, lblFiletype, lblErrors;
	private FormToolkit toolkit;
	private Composite compositeTarget2;
	
	private BpmnProcess bpmnInitialProcess;
	private UmlProcess umlInitialProcess;
	private Tree treeElements, treeWorkflows, treeChangePatterns;
	
	/**
	 * The constructor.
	 */
	public ProcessGeneratorView() {}
	
	/**
	 * This is a callback that will allow us to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {
		
		GridData gd = null;
		
		toolkit = new FormToolkit(parent.getDisplay());
		gd = new GridData(SWT.FILL, SWT.FILL, true, false);
		gd.horizontalSpan = 1;
		parent.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		TabFolder tabFolder = new TabFolder(parent, SWT.NONE);
		toolkit.adapt(tabFolder);
		toolkit.paintBordersFor(tabFolder);
		
		TabItem tabRun = new TabItem(tabFolder, SWT.NONE);
		tabRun.setImage(ResourceManager.getPluginImage("ProcessGenerator", "icons/run.gif"));
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
		
		lblpath = new Label(composite_1, SWT.NONE);
		lblpath.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(lblpath, true, true);
		lblpath.setText("/model/");
		
		btnChange = new Button(composite_1, SWT.NONE);
		btnChange.setImage(ResourceManager.getPluginImage("ProcessGenerator", "icons/folder.gif"));
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
		
		spinner_nbNode = new Spinner(composite_2, SWT.BORDER);
		spinner_nbNode.setMaximum(10000);
		spinner_nbNode.setIncrement(10);
		spinner_nbNode.setMinimum(2);
		spinner_nbNode.setSelection(10);
		toolkit.adapt(spinner_nbNode);
		toolkit.paintBordersFor(spinner_nbNode);
		new Label(composite_2, SWT.NONE);
		
		Label lblMargin = new Label(composite_2, SWT.NONE);
		toolkit.adapt(lblMargin, true, true);
		lblMargin.setText("Margin (%) : ");
		
		spinner_marginNbNode = new Spinner(composite_2, SWT.BORDER);
		spinner_marginNbNode.setMaximum(500);
		spinner_marginNbNode.setSelection(10);
		toolkit.adapt(spinner_marginNbNode);
		toolkit.paintBordersFor(spinner_marginNbNode);
		
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
		
		btnStart = new Button(compRun, SWT.NONE);
		btnStart.setImage(ResourceManager.getPluginImage("ProcessGenerator", "icons/run.gif"));
		toolkit.adapt(btnStart, true, true);
		btnStart.setText("Start");
		
		btnStop = new Button(compRun, SWT.NONE);
		btnStop.setImage(ResourceManager.getPluginImage("ProcessGenerator", "icons/stop.gif"));
		toolkit.adapt(btnStop, true, true);
		btnStop.setText("Stop");
		
		lblResult = new Label(compSctnRun, SWT.NONE);
		toolkit.adapt(lblResult, true, true);
		
		lblErrors = new Label(compSctnRun, SWT.NONE);
		toolkit.adapt(lblErrors, true, true);
		
		TabItem tbtmTargetConfiguration = new TabItem(tabFolder, SWT.NONE);
		tbtmTargetConfiguration.setImage(ResourceManager.getPluginImage("ProcessGenerator", "icons/target.gif"));
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
		
		Composite composite_3 = new Composite(compositeTarget1, SWT.NONE);
		composite_3.setLayout(new GridLayout(2, false));
		composite_3.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
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
		
		grpElementsParameters = new Group(compositeTarget1, SWT.NONE);
		grpElementsParameters.setLayout(new GridLayout(1, false));
		grpElementsParameters.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		grpElementsParameters.setText("Elements parameters");
		toolkit.adapt(grpElementsParameters);
		toolkit.paintBordersFor(grpElementsParameters);
		
		treeElements = new Tree(grpElementsParameters, SWT.BORDER | SWT.CHECK);
		treeElements.setLinesVisible(true);
		treeElements.setHeaderVisible(true);
		treeElements.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		toolkit.adapt(treeElements);
		toolkit.paintBordersFor(treeElements);
		
		TreeColumn trclmnElementName = new TreeColumn(treeElements, SWT.NONE);
		trclmnElementName.setWidth(140);
		trclmnElementName.setText("Element name");
		
		TreeColumn trclmnQuantity = new TreeColumn(treeElements, SWT.NONE);
		trclmnQuantity.setWidth(140);
		trclmnQuantity.setText("Quantity");
		
		TreeColumn trclmnNumber = new TreeColumn(treeElements, SWT.NONE);
		trclmnNumber.setWidth(55);
		trclmnNumber.setText("Number");
		
		TreeColumn trclmnWeight = new TreeColumn(treeElements, SWT.NONE);
		trclmnWeight.setWidth(55);
		trclmnWeight.setText("Weight");
		
		Section sctnWorkflow = toolkit.createSection(scrolledFormTarget.getBody(), Section.TWISTIE | Section.TITLE_BAR);
		sctnWorkflow.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 1, 2));
		sctnWorkflow.marginWidth = 0;
		sctnWorkflow.marginHeight = 0;
		toolkit.paintBordersFor(sctnWorkflow);
		sctnWorkflow.setText("Workflow patterns");
		sctnWorkflow.setExpanded(true);
		
		compositeTarget2 = new Composite(sctnWorkflow, SWT.NONE);
		toolkit.adapt(compositeTarget2);
		toolkit.paintBordersFor(compositeTarget2);
		sctnWorkflow.setClient(compositeTarget2);
		compositeTarget2.setLayout(new GridLayout(1, false));
		
		treeWorkflows = new Tree(compositeTarget2, SWT.BORDER | SWT.CHECK);
		treeWorkflows.setLinesVisible(true);
		treeWorkflows.setHeaderVisible(true);
		treeWorkflows.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		toolkit.adapt(treeWorkflows);
		toolkit.paintBordersFor(treeWorkflows);
		
		TreeColumn trclmnWorkflowName = new TreeColumn(treeWorkflows, SWT.NONE);
		trclmnWorkflowName.setWidth(140);
		trclmnWorkflowName.setText("Workflow name");
		
		TreeColumn treeColumn_1 = new TreeColumn(treeWorkflows, SWT.NONE);
		treeColumn_1.setWidth(140);
		treeColumn_1.setText("Quantity");
		
		TreeColumn treeColumn_2 = new TreeColumn(treeWorkflows, SWT.NONE);
		treeColumn_2.setWidth(55);
		treeColumn_2.setText("Number");
		
		TreeColumn treeColumn_3 = new TreeColumn(treeWorkflows, SWT.NONE);
		treeColumn_3.setWidth(55);
		treeColumn_3.setText("Weight");
		
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
		tbtmGeneticAlgorithmConfiguration.setImage(ResourceManager.getPluginImage("ProcessGenerator", "icons/tool.gif"));
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
		compositeGA1.setLayout(new GridLayout(3, false));
		
		Label lblPopulation = new Label(compositeGA1, SWT.NONE);
		toolkit.adapt(lblPopulation, true, true);
		lblPopulation.setText("Population (#) :");
		
		spinnerNbPopulation = new Spinner(compositeGA1, SWT.BORDER);
		spinnerNbPopulation.setIncrement(10);
		spinnerNbPopulation.setMaximum(10000);
		spinnerNbPopulation.setMinimum(1);
		spinnerNbPopulation.setSelection(100);
		toolkit.adapt(spinnerNbPopulation);
		toolkit.paintBordersFor(spinnerNbPopulation);
		new Label(compositeGA1, SWT.NONE);
		
		btnSetInitialProcess = new Button(compositeGA1, SWT.NONE);
		btnSetInitialProcess.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1));
		btnSetInitialProcess.setImage(ResourceManager.getPluginImage("ProcessGenerator", "icons/process.gif"));
		toolkit.adapt(btnSetInitialProcess, true, true);
		btnSetInitialProcess.setText("Set initial process");
		
		lblFiletype = new Label(compositeGA1, SWT.NONE);
		toolkit.adapt(lblFiletype, true, true);
		lblFiletype.setText("(bpmn file)");
		
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
		
		spinnerElitism = new Spinner(compositeGA2, SWT.BORDER);
		spinnerElitism.setMaximum(1000);
		spinnerElitism.setSelection(5);
		toolkit.adapt(spinnerElitism);
		toolkit.paintBordersFor(spinnerElitism);
		
		Label lblStrategySelection_1 = new Label(compositeGA2, SWT.NONE);
		lblStrategySelection_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		toolkit.adapt(lblStrategySelection_1, true, true);
		lblStrategySelection_1.setText("Strategy :");
		
		comboStrategySelection = new Combo(compositeGA2, SWT.READ_ONLY);
		comboStrategySelection.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1));
		comboStrategySelection.setVisibleItemCount(10);
		toolkit.adapt(comboStrategySelection);
		toolkit.paintBordersFor(comboStrategySelection);
		new Label(compositeGA2, SWT.NONE);
		new Label(compositeGA2, SWT.NONE);
		
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
		
		checkMutation = new Button(compositeGA3, SWT.CHECK);
		checkMutation.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseDown(MouseEvent e) {
				groupMutationParameters.setVisible(!((Button) e.getSource()).getSelection());
			}
		});
		checkMutation.setText("Mutation");
		checkMutation.setBounds(0, 0, 12, 25);
		toolkit.adapt(checkMutation, true, true);
		
		checkCrossover = new Button(compositeGA3, SWT.CHECK);
		checkCrossover.setText("Crossover");
		checkCrossover.setBounds(0, 0, 12, 25);
		toolkit.adapt(checkCrossover, true, true);
		
		groupMutationParameters = new Group(compositeGA3, SWT.NONE);
		groupMutationParameters.setVisible(false);
		groupMutationParameters.setLayout(new GridLayout(1, false));
		groupMutationParameters.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		groupMutationParameters.setText("Mutation parameters");
		groupMutationParameters.setBounds(0, 0, 70, 82);
		toolkit.adapt(groupMutationParameters);
		toolkit.paintBordersFor(groupMutationParameters);
		
		treeChangePatterns = new Tree(groupMutationParameters, SWT.BORDER | SWT.CHECK);
		treeChangePatterns.setLinesVisible(true);
		treeChangePatterns.setHeaderVisible(true);
		treeChangePatterns.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		toolkit.adapt(treeChangePatterns);
		toolkit.paintBordersFor(treeChangePatterns);
		
		TreeColumn trclmnChangePattern = new TreeColumn(treeChangePatterns, SWT.NONE);
		trclmnChangePattern.setWidth(160);
		trclmnChangePattern.setText("Change pattern");
		
		TreeColumn treeColumn_5 = new TreeColumn(treeChangePatterns, SWT.NONE);
		treeColumn_5.setWidth(60);
		treeColumn_5.setText("Probability");
		
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
		
		btnOneSolutionFound = new Button(compositeGA4, SWT.CHECK);
		toolkit.adapt(btnOneSolutionFound, true, true);
		btnOneSolutionFound.setText("Until 1 solutions found");
		new Label(compositeGA4, SWT.NONE);
		new Label(compositeGA4, SWT.NONE);
		
		btnDuringSec = new Button(compositeGA4, SWT.CHECK);
		toolkit.adapt(btnDuringSec, true, true);
		btnDuringSec.setText("During # sec");
		
		spinnerUntilSecondes = new Spinner(compositeGA4, SWT.BORDER);
		spinnerUntilSecondes.setMaximum(100000);
		spinnerUntilSecondes.setMinimum(1);
		spinnerUntilSecondes.setSelection(60);
		toolkit.adapt(spinnerUntilSecondes);
		toolkit.paintBordersFor(spinnerUntilSecondes);
		new Label(compositeGA4, SWT.NONE);
		
		btnGeneration = new Button(compositeGA4, SWT.CHECK);
		toolkit.adapt(btnGeneration, true, true);
		btnGeneration.setText("Until # generations");
		
		spinnerUntilGeneration = new Spinner(compositeGA4, SWT.BORDER);
		spinnerUntilGeneration.setMaximum(100000);
		spinnerUntilGeneration.setMinimum(1);
		spinnerUntilGeneration.setSelection(100);
		toolkit.adapt(spinnerUntilGeneration);
		toolkit.paintBordersFor(spinnerUntilGeneration);
		new Label(compositeGA4, SWT.NONE);
		
		btnUntilStagnation = new Button(compositeGA4, SWT.CHECK);
		toolkit.adapt(btnUntilStagnation, true, true);
		btnUntilStagnation.setText("Until # stagnations");
		
		spinnerUntilStagnation = new Spinner(compositeGA4, SWT.BORDER);
		spinnerUntilStagnation.setMaximum(100000);
		spinnerUntilStagnation.setMinimum(1);
		spinnerUntilStagnation.setSelection(100);
		toolkit.adapt(spinnerUntilStagnation);
		toolkit.paintBordersFor(spinnerUntilStagnation);
		new Label(compositeGA4, SWT.NONE);
		
		Section sctnCustomFitness = toolkit.createSection(scrolledForm.getBody(), Section.TWISTIE | Section.TITLE_BAR);
		sctnCustomFitness.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		sctnCustomFitness.marginWidth = 0;
		sctnCustomFitness.marginHeight = 0;
		toolkit.paintBordersFor(sctnCustomFitness);
		sctnCustomFitness.setText("Custom fitness function (globals weights)");
		
		Composite composite_4 = new Composite(sctnCustomFitness, SWT.NONE);
		toolkit.adapt(composite_4);
		toolkit.paintBordersFor(composite_4);
		sctnCustomFitness.setClient(composite_4);
		composite_4.setLayout(new GridLayout(11, false));
		
		Label lblSizeWeight = new Label(composite_4, SWT.NONE);
		toolkit.adapt(lblSizeWeight, true, true);
		lblSizeWeight.setText("Size weight : ");
		
		spinnerSizeWeight = new Spinner(composite_4, SWT.BORDER);
		spinnerSizeWeight.setSelection(1);
		toolkit.adapt(spinnerSizeWeight);
		toolkit.paintBordersFor(spinnerSizeWeight);
		new Label(composite_4, SWT.NONE);
		
		Label lblElementsWeight = new Label(composite_4, SWT.NONE);
		toolkit.adapt(lblElementsWeight, true, true);
		lblElementsWeight.setText("Elements weight : ");
		
		spinnerElementWeight = new Spinner(composite_4, SWT.BORDER);
		spinnerElementWeight.setSelection(1);
		toolkit.adapt(spinnerElementWeight);
		toolkit.paintBordersFor(spinnerElementWeight);
		new Label(composite_4, SWT.NONE);
		
		Label lblWorkflowWeight = new Label(composite_4, SWT.NONE);
		toolkit.adapt(lblWorkflowWeight, true, true);
		lblWorkflowWeight.setText("Workflow weight : ");
		
		spinnerWorkflowWeight = new Spinner(composite_4, SWT.BORDER);
		spinnerWorkflowWeight.setSelection(1);
		toolkit.adapt(spinnerWorkflowWeight);
		toolkit.paintBordersFor(spinnerWorkflowWeight);
		new Label(composite_4, SWT.NONE);
		
		Label lblManualOclWeight = new Label(composite_4, SWT.NONE);
		toolkit.adapt(lblManualOclWeight, true, true);
		lblManualOclWeight.setText("Manual Ocl weight : ");
		
		spinnerManualOclWeight = new Spinner(composite_4, SWT.BORDER);
		spinnerManualOclWeight.setSelection(1);
		toolkit.adapt(spinnerManualOclWeight);
		toolkit.paintBordersFor(spinnerManualOclWeight);
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

		// les préférences utilisateur pour la partie run
		setPathDirectory(ConfigurationManager.instance.getLocation());
		getSpinnerNbNode().setSelection(ConfigurationManager.instance.getNbNodes());
		getSpinnerMargin().setSelection(ConfigurationManager.instance.getMargin());
		getComboTypeFile().select(ConfigurationManager.instance.getTypeFile());
		
		// on remplit les tableaux d'éléments, de workflows et de change pattern par défaut
		String typeFile = getComboTypeFile().getText();
		if (typeFile.toLowerCase().contains("bpmn")) {
			addElementsToTree(treeElements, EBpmnElement.values());
			addElementsToTree(treeWorkflows, EBpmnWorkflowPattern.values());
			addChangePatternToTree(EBpmnChangePattern.values());
		} else {
			addElementsToTree(treeElements, EUmlElement.values());
			addElementsToTree(treeWorkflows, EUmlWorkflowPattern.values());
			addChangePatternToTree(EUmlChangePattern.values());
		}
		
		// le nombre de population, elitism et stratégie de séléction
		getSpinnerNbPopulation().setSelection(ConfigurationManager.instance.getPopulation());
		getSpinnerElitism().setSelection(ConfigurationManager.instance.getElitism());
		
		// on remplit le combo box de la stratégie de sélection
		for (ESelectionStrategy strat : ESelectionStrategy.values()) {
			getComboStrategySelection().add(strat.toString());
		}
		getComboStrategySelection().select(ConfigurationManager.instance.getSelectionStrategy());
		
		// les préférences des checkbox des opérations d'évolutions
		boolean bool = ConfigurationManager.instance.isCheckMutation();
		getButtonCheckMutation().setSelection(bool);
		groupMutationParameters.setVisible(bool);
		
		bool = ConfigurationManager.instance.isCheckCrossover();
		getButtonCheckCrossover().setSelection(bool);
		
		// les préférences sur les conditions de terminaisons
		getButtonUntilSolutionFound().setSelection(ConfigurationManager.instance.isSolutionFound());
		getButtonDuringSeconde().setSelection(ConfigurationManager.instance.isDuringSecondes());
		getButtonUntilGeneration().setSelection(ConfigurationManager.instance.isUntilGenerations());
		getButtonUntilStagnation().setSelection(ConfigurationManager.instance.isUntilStagnations());
		
		getSpinnerUntilSeconde().setSelection(ConfigurationManager.instance.getNbSecondes());
		getSpinnerUntilGeneration().setSelection(ConfigurationManager.instance.getNbGenerations());
		getSpinnerUntilStagnation().setSelection(ConfigurationManager.instance.getNbStagnations());
		
		// les préférences sur le custom fitness
		getSpinnerSizeWeight().setSelection(ConfigurationManager.instance.getSizeWeight());
		getSpinnerElementWeight().setSelection(ConfigurationManager.instance.getElementsWeight());
		getSpinnerWorkflowWeight().setSelection(ConfigurationManager.instance.getWorkflowsWeight());
		getSpinnerManualOclWeight().setSelection(ConfigurationManager.instance.getManualOCLWeight());
		
		// les listeners
		// selection du type de fichier de sortie (bpmn, uml, etc.)
		getComboTypeFile().addSelectionListener(new SelectionFileType(this));
		btnStart.addSelectionListener(new SelectionStartExecution(this));
		btnSetInitialProcess.addSelectionListener(new SelectSetInitialProcess(this));
		btnChange.addSelectionListener(new SelectPathDirectory(this));
	}
	
	/**
	 * Remplit un Tree selon la liste des éléments passés en paramètre.
	 * 
	 * @param table
	 *            le {@link Table} à remplir.
	 * @param elements
	 *            un tableau d'{@link Object} représentant les éléments constituant le tableau.
	 */
	private void addElementsToTree(Tree tree, IHierarchicalEnum[] elements) {
		
		// les préférences utilisateurs
		String lecturePref;
		if (tree == treeElements) {
			lecturePref = ConfigurationManager.instance.getElementsAttributes();
		} else {
			lecturePref = ConfigurationManager.instance.getWorkflowsAttributes();
		}
		
		String[] lignesPref = lecturePref.split("___");
		Map<String, String[]> preferences = new HashMap<String, String[]>();
		for (String string : lignesPref) {
			if (!string.isEmpty()) {
				String[] infos = string.split("%");
				preferences.put(infos[0], infos);
			}
		}
		
		// pour chaque élément
		for (IHierarchicalEnum elem : elements) {
			// on créé le TreeItem
			TreeItem newTreeItem = addToTree(tree, elem);
			
			// maintenant on peut ajouter le menu déroulant etc.
			// on récupère les préférences utilisateurs
			String[] infos = preferences.get(elem.toString());
			
			// si infos est null, on met les valeurs par défaut
			if (infos == null || infos.length != 5) {
				infos = new String[5];
				infos[0] = "";
				infos[1] = Utils.DEFAULT_CHECK;
				infos[2] = Utils.DEFAULT_QUANTITY;
				infos[3] = Utils.DEFAULT_NUMBER;
				infos[4] = Utils.DEFAULT_WEIGHT;
			}
			
			// le check
			newTreeItem.setChecked(infos[1].equals("1"));
			
			// emplacement 0 : le nom de l'élément
			newTreeItem.setText(0, elem.toString());
			newTreeItem.setData(Utils.NAME_KEY, elem);
			
			// emplacement 1 : le combobox
			TreeEditor editor = new TreeEditor(tree);
			Combo combo = new Combo(tree, SWT.READ_ONLY);
			// on remplit le combo box
			for (EQuantity quantity : EQuantity.values()) {
				combo.add(quantity.toString().toLowerCase());
			}
			// la préférence
			try {
				combo.select(Integer.parseInt(infos[2]));
			} catch (Exception e) {
				combo.select(3);
			}
			editor.grabHorizontal = true;
			editor.setEditor(combo, newTreeItem, 1);
			// un listener nous permettra de mettre a jour la valeur du TableItem en fonction de la selection du combo
			combo.addSelectionListener(new SelectionComboInTree(newTreeItem, combo));
			newTreeItem.setData(Utils.QUANTITY_KEY, combo.getText());
			
			// emplacement 2 : le nombre
			editor = new TreeEditor(tree);
			Text text = new Text(tree, SWT.NONE);
			text.setText(infos[3]);
			editor.grabHorizontal = true;
			editor.setEditor(text, newTreeItem, 2);
			// un listener nous permettra de mettre a jour la valeur du TableItem en fonction du Text
			text.addFocusListener(new ModifyTextInTree(newTreeItem, text, Utils.NUMBER_KEY));
			newTreeItem.setData(Utils.NUMBER_KEY, text.getText());
			
			// emplacement 3 : le poids associé
			editor = new TreeEditor(tree);
			text = new Text(tree, SWT.NONE);
			text.setText(infos[4]);
			editor.grabHorizontal = true;
			editor.setEditor(text, newTreeItem, 3);
			// un listener nous permettra de mettre a jour la valeur du TableItem en fonction du Text
			text.addFocusListener(new ModifyTextInTree(newTreeItem, text, Utils.WEIGHT_KEY));
			newTreeItem.setData(Utils.WEIGHT_KEY, text.getText());
		}
	}
	
	/**
	 * Cette fonction ajoute un élément dans un arbre. Si son élément parent n'existe pas encore, alors celui ci est 
	 * automatiquement ajouté récursivement.
	 * @param tree l'arbre.
	 * @param elem l'élément à ajouter.
	 * @return {@link TreeItem} le nouvel item ajouté.
	 */
	private TreeItem addToTree(Tree tree, IHierarchicalEnum elem) {
		
		// 1ère vérif, si l'élément existe déjà, on le renvoie directement
		TreeItem newTreeItem = findItem(tree.getItems(), elem.toString());
		if (newTreeItem != null)
			return newTreeItem;
		
		// S'il n'existe pas, on cherche si le parent existe
		if (elem.getParent() != null) {
			String name = elem.getParent().toString();
			TreeItem parentItem = findItem(tree.getItems(), name);
			
			// si le parent est null c'est qu'il n'existe pas encore donc on l'ajoute récursivement
			if (parentItem == null)
				parentItem = addToTree(tree, elem.getParent());
			
			// et enfin on peut créer notre TreeItem
			newTreeItem = new TreeItem(parentItem, SWT.NONE);
			parentItem.setExpanded(true);
		} 
		// sinon, on peut ajouter l'élément directement sur l'arbre
		else {
			newTreeItem = new TreeItem(tree, SWT.NONE);
		}
		
		return newTreeItem;
	}

	/**
	 * Cherche un {@link TreeItem} parmis ceux passé en paramètre ou parmis leurs fils selon leur nom.
	 * @param items les items ainsi que leurs fils qu'il faut parcourir.
	 * @param elem le nom dont on cherche l'item.
	 * @return {@link TreeItem}.
	 */
	private TreeItem findItem(TreeItem[] items, String elem) {
		
		if (items.length == 0)
			return null;
		
		for (TreeItem item : items) {
			// si on trouve l'élément dans le tree, on renvoie vrai
			if (item.getText(0).equals(elem))
				return item;
			
			// sinon on cherche dans les fils de item
			TreeItem findItem = findItem(item.getItems(), elem);
			if (findItem != null)
				return findItem;
		}
		
		// si on a rien trouvé, on renvoie faux
		return null;
	}

	/**
	 * Rempli le Tree des change patterns selon les {@link IHierarchicalEnum} passés en paramètres.
	 * @param changePatterns les {@link IHierarchicalEnum} représentant les change patterns.
	 */
	private void addChangePatternToTree(IHierarchicalEnum[] changePatterns) {

		// on récupère les préférences utilisateurs
		String lecturePreferences = ConfigurationManager.instance.getChangePatternAttributes();
		Map<String, String[]> preferences = new HashMap<>();
		for (String ligne : lecturePreferences.split("___")) {
			if (!ligne.isEmpty()) {
				String[] infos = ligne.split("%");
				preferences.put(infos[0], infos);
			}
		}
		
		for (IHierarchicalEnum cp : changePatterns) {
			// on créé le TreeItem
			TreeItem newTreeItem = addToTree(treeChangePatterns, cp);
			
			// les préférences utilisateur
			String[] infos = preferences.get(cp.toString());
			if (infos == null || infos.length != 3) {
				infos = new String[3];
				infos[0] = "";
				// check ou pas
				infos[1] = "1";
				// proba
				infos[2] = "1";
			}
			
			// le check
			newTreeItem.setChecked(infos[1].equals("1"));
			
			// emplacement 0 : le nom
			newTreeItem.setText(0, cp.toString());
			newTreeItem.setData(Utils.NAME_KEY, cp);
			
			// emplacement 1 : le nombre de proba
			TreeEditor editor = new TreeEditor(treeChangePatterns);
			Text text = new Text(treeChangePatterns, SWT.NONE);
			text.setText(infos[2]);
			editor.grabHorizontal = true;
			editor.setEditor(text, newTreeItem, 1);
			// un listener nous permettra de mettre a jour la valeur du TableItem en fonction du Text
			text.addFocusListener(new ModifyTextInTree(newTreeItem, text, Utils.NUMBER_KEY));
			newTreeItem.setData(Utils.NUMBER_KEY, text.getText());
		}
	}
	
	public Spinner getSpinnerNbNode() {
		return spinner_nbNode;
	}
	
	public Spinner getSpinnerMargin() {
		return spinner_marginNbNode;
	}
	
	public Combo getComboTypeFile() {
		return comboTypeFile;
	}

	public Tree getTreeElements() {
		return treeElements;
	}

	public Tree getTreeWorkflows() {
		return treeWorkflows;
	}
	
	public Label getLabelLocation() {
		return lblpath;
	}
	
	public Text getTextOclConstraint() {
		return text_oclConstraint;
	}
	
	public Spinner getSpinnerNbPopulation() {
		return spinnerNbPopulation;
	}
	
	public Label getLabelSetInitialProcess() {
		return lblFiletype;
	}
	
	public BpmnProcess getInitialBpmnProcess() {
		return bpmnInitialProcess;
	}
	
	public UmlProcess getInitialUmlProcess() {
		return umlInitialProcess;
	}
	
	public Spinner getSpinnerElitism() {
		return spinnerElitism;
	}
	
	public Combo getComboStrategySelection() {
		return comboStrategySelection;
	}
	
	public Button getButtonCheckMutation() {
		return checkMutation;
	}
	
	public Button getButtonCheckCrossover() {
		return checkCrossover;
	}
	
	public Tree getTreeMutationParameters() {
		return treeChangePatterns;
	}
	
	public Button getButtonUntilSolutionFound() {
		return btnOneSolutionFound;
	}
	
	public Button getButtonDuringSeconde() {
		return btnDuringSec;
	}
	
	public Spinner getSpinnerUntilSeconde() {
		return spinnerUntilSecondes;
	}
	
	public Button getButtonUntilGeneration() {
		return btnGeneration;
	}
	
	public Spinner getSpinnerUntilGeneration() {
		return spinnerUntilGeneration;
	}
	
	public Button getButtonUntilStagnation() {
		return btnUntilStagnation;
	}
	
	public Spinner getSpinnerUntilStagnation() {
		return spinnerUntilStagnation;
	}
	
	public Label getLabelResult() {
		return lblResult;
	}
	
	public Label getLabelError() {
		return lblErrors;
	}
	
	public Button getButtonStop() {
		return btnStop;
	}
	
	public Spinner getSpinnerSizeWeight() {
		return spinnerSizeWeight;
	}
	
	public Spinner getSpinnerElementWeight() {
		return spinnerElementWeight;
	}
	
	public Spinner getSpinnerWorkflowWeight() {
		return spinnerWorkflowWeight;
	}
	
	public Spinner getSpinnerManualOclWeight() {
		return spinnerManualOclWeight;
	}
	
	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		form.setFocus();
	}
	
	public void setBpmnInitialProcess(BpmnProcess process) {
		this.bpmnInitialProcess = process;
	}
	
	public void setUmlInitialProcess(UmlProcess process) {
		this.umlInitialProcess = process;
	}
	
	public void setPathDirectory(String path) {
		this.lblpath.setText(path);
		this.lblpath.getParent().layout(true);
	}
	
	public void print(String text) {
		Display.getDefault().asyncExec(new RunnablePrintView(lblResult, text));
	}
	
	public void printError(String text) {
		Display.getDefault().asyncExec(new RunnablePrintView(lblErrors, text));
	}
	
	/**
	 * Met à jour l'arbre des éléments
	 * @param elements
	 */
	public void majTreeOfElements(IEnumElement[] elements) {
		this.newTreeElement();
		this.addElementsToTree(treeElements, elements);
	}
	
	/**
	 * Met à jour l'arbre des workflows
	 * @param elements
	 */
	public void majTreeOfWorkflows(IEnumWorkflowPattern[] elements) {
		this.newTreeWorkflow();
		this.addElementsToTree(treeWorkflows, elements);
	}
	
	/**
	 * Met à jour l'arbre des changes patterns
	 * @param eBpmnChangePatterns
	 */
	public void majTableOfChangePatterns(IEnumChangePattern<?>[] eBpmnChangePatterns) {
		this.newTreeChangePattern();
		this.addChangePatternToTree(eBpmnChangePatterns);
	}

	private void newTreeElement() {
		treeElements.dispose();
		treeElements = new Tree(grpElementsParameters, SWT.BORDER | SWT.CHECK);
		treeElements.setLinesVisible(true);
		treeElements.setHeaderVisible(true);
		treeElements.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		toolkit.adapt(treeElements);
		toolkit.paintBordersFor(treeElements);
		
		TreeColumn trclmnElementName = new TreeColumn(treeElements, SWT.NONE);
		trclmnElementName.setWidth(140);
		trclmnElementName.setText("Element name");
		
		TreeColumn trclmnQuantity = new TreeColumn(treeElements, SWT.NONE);
		trclmnQuantity.setWidth(140);
		trclmnQuantity.setText("Quantity");
		
		TreeColumn trclmnNumber = new TreeColumn(treeElements, SWT.NONE);
		trclmnNumber.setWidth(40);
		trclmnNumber.setText("Number");
		
		TreeColumn trclmnWeight = new TreeColumn(treeElements, SWT.NONE);
		trclmnWeight.setWidth(40);
		trclmnWeight.setText("Weight");
		
		grpElementsParameters.layout(true);
	}
	
	private void newTreeWorkflow() {
		
		treeWorkflows.dispose();
		treeWorkflows = new Tree(compositeTarget2, SWT.BORDER | SWT.CHECK);
		treeWorkflows.setLinesVisible(true);
		treeWorkflows.setHeaderVisible(true);
		treeWorkflows.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		toolkit.adapt(treeWorkflows);
		toolkit.paintBordersFor(treeWorkflows);
		
		TreeColumn trclmnWorkflowName = new TreeColumn(treeWorkflows, SWT.NONE);
		trclmnWorkflowName.setWidth(140);
		trclmnWorkflowName.setText("Workflow name");
		
		TreeColumn treeColumn_1 = new TreeColumn(treeWorkflows, SWT.NONE);
		treeColumn_1.setWidth(140);
		treeColumn_1.setText("Quantity");
		
		TreeColumn treeColumn_2 = new TreeColumn(treeWorkflows, SWT.NONE);
		treeColumn_2.setWidth(40);
		treeColumn_2.setText("Number");
		
		TreeColumn treeColumn_3 = new TreeColumn(treeWorkflows, SWT.NONE);
		treeColumn_3.setWidth(40);
		treeColumn_3.setText("Weight");
		
		compositeTarget2.layout(true);
	}
	
	private void newTreeChangePattern() {
		treeChangePatterns.dispose();
		
		treeChangePatterns = new Tree(groupMutationParameters, SWT.BORDER | SWT.CHECK);
		treeChangePatterns.setLinesVisible(true);
		treeChangePatterns.setHeaderVisible(true);
		treeChangePatterns.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		toolkit.adapt(treeChangePatterns);
		toolkit.paintBordersFor(treeChangePatterns);
		
		TreeColumn trclmnChangePattern = new TreeColumn(treeChangePatterns, SWT.NONE);
		trclmnChangePattern.setWidth(160);
		trclmnChangePattern.setText("Change pattern");

		TreeColumn treeColumn_5 = new TreeColumn(treeChangePatterns, SWT.NONE);
		treeColumn_5.setWidth(60);
		treeColumn_5.setText("Probability");
		
		groupMutationParameters.layout(true);
	}
}
