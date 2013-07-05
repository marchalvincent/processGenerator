package fr.lip6.move.processGenerator.constraint;

import java.util.List;
import java.util.Map;

/**
 * Représente un morceau de diagramme d'activité. Cette classe peut être utilisée lorsqu'on veut insérer un workflow
 * pattern directement à un candidat.
 * 
 * @author Vincent
 * 
 */
public interface IWorkflowRepresentation {

	/**
	 * Getter sur le début de la représentation
	 * @param begin
	 */
	void setBegin(Object begin);
	
	/**
	 * Setter sur la fin de la représentation
	 * @param end
	 */
	void setEnd(Object end);
	
	/**
	 * Renvoie le début de la représentation du workflow
	 * @return
	 */
	Object getBegin();

	/**
	 * Renvoie la fin de la représentation du workflow
	 * @return
	 */
	Object getEnd();

	/**
	 * Renvoie les liaisons entre les gateways/controlNode
	 * @return
	 */
	Map<String, String> getLinks();
	
	/**
	 * Renvoie la liste des noeuds de la représentation
	 * @return
	 */
	List<? extends Object> getNodes();
	
	/**
	 * Renvoie la liste des arcs de la représentation
	 * @return
	 */
	List<? extends Object> getEdges();
}
