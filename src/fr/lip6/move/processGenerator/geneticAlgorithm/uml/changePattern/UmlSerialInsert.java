package fr.lip6.move.processGenerator.geneticAlgorithm.uml.changePattern;

import org.eclipse.uml2.uml.Activity;

import fr.lip6.move.processGenerator.geneticAlgorithm.AbstractChangePattern;
import fr.lip6.move.processGenerator.geneticAlgorithm.uml.IUmlChangePattern;
import fr.lip6.move.processGenerator.uml.UmlProcess;


/**
 * Ce change pattern insert une {@link Activity} au hasard dans le process.
 * @author Vincent
 *
 */
public class UmlSerialInsert extends AbstractChangePattern implements IUmlChangePattern {

	@Override
	public UmlProcess apply(UmlProcess process) {
		// TODO non implémenté
		return process;
	}
}
