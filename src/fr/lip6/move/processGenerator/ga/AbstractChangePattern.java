package fr.lip6.move.processGenerator.ga;

/**
 * Cette classe offre un niveau d'abstraction à tout les change pattern.
 * 
 * @author Vincent
 * 
 */
public abstract class AbstractChangePattern implements IChangePattern {
	
	private int probability;
	
	public AbstractChangePattern() {
		super();
	}
	
	@Override
	public void setProba (String proba) {
		try {
			int prob = Integer.parseInt(proba);
			probability = prob;
		} catch (Exception e) {
			probability = 1;
		}
	}
	
	@Override
	public int getProba () {
		return probability;
	}
}
