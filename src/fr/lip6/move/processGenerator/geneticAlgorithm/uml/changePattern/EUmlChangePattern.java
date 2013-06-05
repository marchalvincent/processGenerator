package fr.lip6.move.processGenerator.geneticAlgorithm.uml.changePattern;

import fr.lip6.move.processGenerator.geneticAlgorithm.IChangePattern;
import fr.lip6.move.processGenerator.geneticAlgorithm.IEnumChangePattern;


public enum EUmlChangePattern implements IEnumChangePattern {
	
	SERIAL_INSERT(UmlSerialInsert.class);

	private Class<? extends IChangePattern> clazz;

	private EUmlChangePattern(Class<? extends IChangePattern> clazz) {
		this.clazz = clazz;
	}
	
	@Override
	public IChangePattern newInstance(String proba) throws Exception {
		IChangePattern cp = this.clazz.newInstance();
		cp.setProba(proba);
		return cp;
	}
}
