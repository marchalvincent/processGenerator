package fr.lip6.move.processGenerator.geneticAlgorithm.bpmn;

import java.util.List;
import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.FlowNode;
import org.uncommons.watchmaker.framework.FitnessEvaluator;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.geneticAlgorithm.FitnessWeightHelper;
import fr.lip6.move.processGenerator.structuralConstraint.StructuralConstraintChecker;

/**
 * L'évaluateur d'un candidat. C'est une fonction de fitness "naturelle", c'est à dire
 * que plus la valeur de la fitness est élevée, plus le candidat correspond aux attentes.
 * @author Vincent
 *
 */
public class BpmnFitnessEvaluator implements FitnessEvaluator<BpmnProcess> {

	private int nbNodes;
	private int margin;
	private List<StructuralConstraintChecker> contraintesElements, contraintesWorkflows;
	private StructuralConstraintChecker manualOclChecker;
	private FitnessWeightHelper weightHelper;
	private final boolean debug = false;

	public BpmnFitnessEvaluator(Integer nbNodes, Integer margin,
			List<StructuralConstraintChecker> contraintesElements,
			List<StructuralConstraintChecker> contraintesWorkflows,
			StructuralConstraintChecker manualOclChecker, FitnessWeightHelper weightHelper) {
		super();
		this.nbNodes = nbNodes;
		this.margin = margin;
		this.contraintesElements = contraintesElements;
		this.contraintesWorkflows = contraintesWorkflows;
		this.manualOclChecker = manualOclChecker;
		this.weightHelper = weightHelper;
	}

	@Override
	public double getFitness(BpmnProcess candidate, List<? extends BpmnProcess> population) {

		double sizeFitness = getSizeFitness(candidate, population);
		double constElements = getconstraintFitness(candidate, population, contraintesElements);
		double constWorkflow = getconstraintFitness(candidate, population, contraintesWorkflows);

		double consManual = 0;
		try {
			if (manualOclChecker != null && manualOclChecker.check(candidate.getProcess()))
				consManual = 1;
			else if (manualOclChecker == null)
				consManual = 1;
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		if (debug) {
			System.out.println("sizeFitness : " + sizeFitness);
			System.out.println("constElements : " + constElements);
			System.out.println("constWorkflow : " + constWorkflow);
			System.out.println("consManual : " + consManual);
			System.out.println("total : " + (sizeFitness * weightHelper.getSizePercent() + constElements * weightHelper.getElementPercent() +
					constWorkflow * weightHelper.getWorkflowPercent() + consManual * weightHelper.getManualOclPercent()));
			System.out.println();
			System.out.println();
			System.out.println();
		}

		return (sizeFitness * weightHelper.getSizePercent() + constElements * weightHelper.getElementPercent() +
				constWorkflow * weightHelper.getWorkflowPercent() + consManual * weightHelper.getManualOclPercent());
	}

	@Override
	public boolean isNatural() {
		return true;
	}

	/**
	 * Cette fonction se charge de calculer la "fitness" du candidat en fonction de la taille prédéfinie par l'utilisateur.
	 * Plus la taille correspond à l'attente, plus la valeur renvoyée se rapproche de 1. A contrario, la valeur renvoyée
	 * s'approchera de 0.
	 * @param candidate {@link BpmnProcess}. Le candidat à évaluer.
	 * @param population {@link List} de {@link BpmnProcess}. Le reste de la population.
	 * @return double la valeur de fitness correspondant à la taille du candidat.
	 */
	public double getSizeFitness(BpmnProcess candidate, List<? extends BpmnProcess> population) {

		// on compte combien on a de FlowNode
		double size = 0;
		for (FlowElement elem : candidate.getProcess().getFlowElements()) {
			if (elem instanceof FlowNode)
				size++;
		}

		// on définit les marges
		double marginMin = this.nbNodes - (this.nbNodes * this.margin / 100);
		if (marginMin < 0)
			marginMin = 0;
		double marginMax = this.nbNodes + (this.nbNodes * this.margin / 100);

		// cas particulier si marginMin == size == 0, le process correspond
		if (marginMin == 0 && size == 0) 
			return 1;
		// si 0 <= size < marginMin
		if (0 <= size && size < marginMin)
			return (size / marginMin);
		// si marginMin <= size <= marginMax
		if (marginMin <= size && size <= marginMax)
			return 1;
		// si marginMax < size < marginMax + marginMin (pour que le ratio redescende progressivement)
		if (marginMax < size && size < (marginMax + marginMin))
			return ((marginMax + marginMin) - size) / marginMin;
		// enfin si on dépasse de trop, (marginMax + marginMin) <= size
		if ((marginMax + marginMin) <= size)
			return 0;

		return 0;
	}

	/**
	 * Cette fonction se charge de calculer la "fitness" du candidat en fonction de la liste de constraintes structurelles
	 * passée en paramètre.
	 * @param candidate {@link BpmnProcess}. Le candidat à évaluer.
	 * @param population {@link List} de {@link BpmnProcess}. Le reste de la population.
	 * @param constraints la liste des {@link StructuralConstraintChecker} à valider sur le candidat.
	 * @return double la valeur defitness correspondant aux contraintes vérifiées sur le candidat. La valeur est entre 0 et 1.
	 */
	public double getconstraintFitness(BpmnProcess candidate, List<? extends BpmnProcess> population,
			List<StructuralConstraintChecker> constraints) {

		if (constraints.isEmpty())
			return 1;

		double totalConstraints = 0;
		double constraintsMatches = 0;
		for (StructuralConstraintChecker constr : constraints) {
			totalConstraints += constr.getWeight();
			try {
				if (constr.check(candidate.getProcess()))
					constraintsMatches += constr.getWeight();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
		System.out.println("match " + constraintsMatches + " sur " + totalConstraints);
		return (constraintsMatches / totalConstraints);
	}
}
