package fr.lip6.move.processGenerator.uml2.ga.cp;

import fr.lip6.move.processGenerator.ga.IChangePattern;
import fr.lip6.move.processGenerator.ga.IEnumChangePattern;
import fr.lip6.move.processGenerator.uml2.UmlProcess;

/**
 * Cette énumération représente les change patterns que peut sélectionner l'utilisateur sur l'interface graphique.
 * Chaque change pattern est associé à un {@link IChangePattern}.
 * 
 * @author Vincent
 * 
 */
public enum EUmlChangePattern implements IEnumChangePattern<UmlProcess> {
	
	SERIAL_INSERT(UmlSerialInsert.class);
	
	private Class<? extends IChangePattern<UmlProcess>> clazz;
	
	private EUmlChangePattern(Class<? extends IChangePattern<UmlProcess>> clazz) {
		this.clazz = clazz;
	}
	
	@Override
	public IChangePattern<UmlProcess> newInstance(String proba) throws Exception {
		IChangePattern<UmlProcess> cp = this.clazz.newInstance();
		cp.setProba(proba);
		return cp;
	}
}
