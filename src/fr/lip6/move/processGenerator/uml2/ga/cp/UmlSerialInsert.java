package fr.lip6.move.processGenerator.uml2.ga.cp;

import org.eclipse.uml2.uml.Activity;

import fr.lip6.move.processGenerator.ga.AbstractChangePattern;
import fr.lip6.move.processGenerator.uml2.UmlProcess;
import fr.lip6.move.processGenerator.uml2.ga.IUmlChangePattern;


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
