package fr.lip6.move.processGenerator.geneticAlgorithm.bpmn;

import fr.lip6.move.processGenerator.bpmn2.BpmnProcess;
import fr.lip6.move.processGenerator.geneticAlgorithm.IChangePattern;


public interface IBpmnChangePatterns extends IChangePattern {
	
	BpmnProcess apply();
}
