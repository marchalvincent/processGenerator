package fr.lip6.move.processGenerator.geneticAlgorithm;

import java.util.ArrayList;
import java.util.List;
import fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.changePattern.BpmnConditionalInsert;
import fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.changePattern.BpmnParallelInsert;
import fr.lip6.move.processGenerator.geneticAlgorithm.bpmn.changePattern.BpmnSerialInsert;
import fr.lip6.move.processGenerator.geneticAlgorithm.uml.changePattern.UmlSerialInsert;


public class ChangePatternFactory {
	
	/**
	 * Les classes de change pattern implémentée pour le type de fichier bpmn
	 */
	private List<Class<? extends IChangePattern>> changePatternsBpmn;
	
	/**
	 * Les classes de change pattern implémentée pour le type de fichier uml
	 */
	private List<Class<? extends IChangePattern>> changePatternsUml;
	
	private static ChangePatternFactory instance = new ChangePatternFactory();
	public static ChangePatternFactory getInstance() {
		return instance;
	}
	
	private ChangePatternFactory() {
		super();
		
		// les change patterns de bpmn
		changePatternsBpmn = new ArrayList<Class<? extends IChangePattern>>();
		changePatternsBpmn.add(BpmnConditionalInsert.class);
		changePatternsBpmn.add(BpmnParallelInsert.class);
		changePatternsBpmn.add(BpmnSerialInsert.class);
		
		// les change patterns de uml
		changePatternsUml = new ArrayList<Class<? extends IChangePattern>>();
		changePatternsUml.add(UmlSerialInsert.class);
	}
	
	public List<Class<? extends IChangePattern>> getBpmnChangePatterns() {
		return changePatternsBpmn;
	}
	
	public List<Class<? extends IChangePattern>> getUmlChangePatterns() {
		return changePatternsUml;
	}
	
	public IChangePattern getChangePattern(String typeFile, String changePatternName, String proba) {
		if (typeFile.toLowerCase().contains("bpmn")) {
			return this.getDynamicChangePattern(changePatternsBpmn, changePatternName, proba);
		} else {
			return this.getDynamicChangePattern(changePatternsUml, changePatternName, proba);
		}
	}

	private IChangePattern getDynamicChangePattern(List<Class<? extends IChangePattern>> changePatterns, String changePatternName, String proba) {
		
		for (Class<? extends IChangePattern> clazz : changePatterns) {
			if (clazz.getSimpleName().toLowerCase().contains(changePatternName.toLowerCase())) {
				try {
					IChangePattern cp = clazz.newInstance();
					cp.setProba(proba);
					return cp;
				} catch (InstantiationException | IllegalAccessException e) {
					System.err.println("Impossible d'instancier dynamiquement le change pattern '" + changePatternName + "'.");
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
