package fr.lip6.move.processGenerator.geneticAlgorithm.uml;

import fr.lip6.move.processGenerator.geneticAlgorithm.IChangePattern;
import fr.lip6.move.processGenerator.uml.UmlProcess;

/**
 * Cette interface définit le comportement des change pattern implémentés pour 
 * fonctionner sur les fichiers UML2.0.
 * @author Vincent
 *
 */
public interface IUmlChangePattern extends IChangePattern {
	
	/**
	 * Cette méthode permet au change pattern de modifier le candidat.
	 * @param oldProcess le candidat avant la mutation.
	 * @return {@link UmlProcess} le candidat après la mutation.
	 */
	UmlProcess apply(UmlProcess oldProcess);
}
