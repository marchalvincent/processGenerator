package fr.lip6.move.processGenerator.geneticAlgorithm.uml;

import fr.lip6.move.processGenerator.geneticAlgorithm.IChangePattern;
import fr.lip6.move.processGenerator.uml.UmlProcess;


public interface IUmlChangePattern extends IChangePattern {
	
	UmlProcess apply(UmlProcess process);
}
