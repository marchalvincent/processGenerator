package fr.lip6.move.processGenerator.uml2.ga.cp;

import fr.lip6.move.processGenerator.ga.IChangePattern;
import fr.lip6.move.processGenerator.ga.IEnumChangePattern;

/**
 * Cette énumération représente les change patterns que peut sélectionner l'utilisateur sur l'interface graphique.
 * Chaque change pattern est associé à un {@link IChangePattern}.
 * 
 * @author Vincent
 * 
 */
public enum EUmlChangePattern implements IEnumChangePattern {
	
	SERIAL_INSERT(UmlSerialInsert.class);
	
	private Class<? extends IChangePattern> clazz;
	
	private EUmlChangePattern(Class<? extends IChangePattern> clazz) {
		this.clazz = clazz;
	}
	
	@Override
	public IChangePattern newInstance (String proba) throws Exception {
		IChangePattern cp = this.clazz.newInstance();
		cp.setProba(proba);
		return cp;
	}
}
