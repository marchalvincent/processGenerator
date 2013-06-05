package fr.lip6.move.processGenerator.geneticAlgorithm;

/**
 * Cette classe Helper va nous permettre de stocker le poids associé à chaque élément de la fonction fitness
 * par l'utilisateur depuis l'interface graphique.
 * @author Vincent
 *
 */
public class FitnessWeightHelper {
	
	private double sizePercent, elementPercent, workflowPercent, manualOclPercent;

	public FitnessWeightHelper(double sizeWeight, double elementWeight, double workflowWeight, double manualOclWeight) {
		super();
		double total = sizeWeight + elementWeight + workflowWeight + manualOclWeight;
		this.sizePercent = sizeWeight / total * 100;
		this.elementPercent = elementWeight / total * 100;
		this.workflowPercent = workflowWeight / total * 100;
		this.manualOclPercent = manualOclWeight / total * 100;
	}
	
	public double getSizePercent() {
		return sizePercent;
	}
	
	public double getElementPercent() {
		return elementPercent;
	}
	
	public double getWorkflowPercent() {
		return workflowPercent;
	}
	
	public double getManualOclPercent() {
		return manualOclPercent;
	}
}
