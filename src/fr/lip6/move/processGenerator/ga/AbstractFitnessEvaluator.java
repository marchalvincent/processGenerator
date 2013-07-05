package fr.lip6.move.processGenerator.ga;

import java.util.List;
import org.uncommons.watchmaker.framework.FitnessEvaluator;
import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.constraint.StructuralConstraintChecker;

/**
 * Cette classe offre un niveau d'abstraction à tous les évaluateur de fitness, quelque soit le type de candidat qu'il
 * évaluent.
 * 
 * @author Vincent
 * 
 * @param <T>
 *            le paramètre dynamique de candidat que la classe évalue.
 */
public abstract class AbstractFitnessEvaluator<T> implements FitnessEvaluator<T> {
	
	private int nbNodes;
	private int margin;
	private List<StructuralConstraintChecker> contraintesElements, contraintesWorkflows;
	private StructuralConstraintChecker manualOclChecker;
	private FitnessWeightHelper weightHelper;
	private boolean bool = false;
	
	public AbstractFitnessEvaluator(Integer nbNodes, Integer margin, List<StructuralConstraintChecker> contraintesElements,
			List<StructuralConstraintChecker> contraintesWorkflows, StructuralConstraintChecker manualOclChecker,
			FitnessWeightHelper weightHelper) {
		super();
		this.nbNodes = nbNodes;
		this.margin = margin;
		this.contraintesElements = contraintesElements;
		this.contraintesWorkflows = contraintesWorkflows;
		this.manualOclChecker = manualOclChecker;
		this.weightHelper = weightHelper;
	}
	
	/**
	 * Renvoie la taille du process candidat à évaluer.
	 * 
	 * @return double.
	 */
	public abstract double getSizeCandidate(T candidate);
	
	@Override
	public double getFitness(T candidate, List<? extends T> population) {
		
		double sizeFitness = getSizeFitness(getSizeCandidate(candidate));
		double constElements = getconstraintFitness(candidate, population, contraintesElements);
		double constWorkflow = getconstraintFitness(candidate, population, contraintesWorkflows);
		
		double consManual = 0;
		try {
			if (manualOclChecker != null && manualOclChecker.check(candidate))
				consManual = 1;
			else if (manualOclChecker == null)
				consManual = 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (bool) {
			System.out.println("sizeFitness : " + sizeFitness);
			System.out.println("constElements : " + constElements);
			System.out.println("constWorkflow : " + constWorkflow);
			System.out.println("consManual : " + consManual);
			System.out
					.println("total : "
							+ (sizeFitness * weightHelper.getSizePercent() + constElements * weightHelper.getElementPercent()
									+ constWorkflow * weightHelper.getWorkflowPercent() + consManual
									* weightHelper.getManualOclPercent()));
			System.out.println();
			System.out.println();
			System.out.println();
		}
		
		return (sizeFitness * weightHelper.getSizePercent() + constElements * weightHelper.getElementPercent() + constWorkflow
				* weightHelper.getWorkflowPercent() + consManual * weightHelper.getManualOclPercent());
		
	}
	
	@Override
	public boolean isNatural() {
		return true;
	}
	
	/**
	 * Cette fonction se charge de calculer la "fitness" du candidat en fonction de la taille prédéfinie par
	 * l'utilisateur. Plus la taille correspond à l'attente, plus la valeur renvoyée se rapproche de 1. A contrario, la
	 * valeur renvoyée s'approchera de 0.
	 * 
	 * @param size
	 *            la taille actuelle du candidat.
	 * @return double, la fitness du candidat par rapport à sa taille.
	 */
	public double getSizeFitness(double size) {
		
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
	 * Cette fonction se charge de calculer la "fitness" du candidat en fonction de la liste de constraintes
	 * structurelles passée en paramètre.
	 * 
	 * @param candidate
	 *            {@link BpmnProcess}. Le candidat à évaluer.
	 * @param population
	 *            {@link List} de {@link BpmnProcess}. Le reste de la population.
	 * @param constraints
	 *            la liste des {@link StructuralConstraintChecker} à valider sur le candidat.
	 * @return double la valeur de fitness correspondant aux contraintes vérifiées sur le candidat. La valeur est entre
	 *         0 et 1.
	 */
	private double getconstraintFitness(T candidate, List<? extends T> population, List<StructuralConstraintChecker> constraints) {
		
		if (constraints.isEmpty())
			return 1;
		
		double totalConstraints = 0;
		double constraintsMatches = 0;
		for (StructuralConstraintChecker constr : constraints) {
			totalConstraints += constr.getWeight();
			try {
				if (constr.check(candidate))
					constraintsMatches += constr.getWeight();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
		
		return (constraintsMatches / totalConstraints);
	}
}
