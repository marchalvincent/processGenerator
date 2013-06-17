package fr.lip6.move.processGenerator.bpmn2;

import org.eclipse.bpmn2.Gateway;

/**
 * Cette interface permet de surcharger les {@link Gateway}s déjà existantes de bpmn2.
 * @author Vincent
 *
 */
public interface MyGateway extends Gateway {
	
	/**
	 * Permet d'associer une gateway à une autre (typiquement ouvrante / fermante).
	 * @param twin {@link MyGateway}.
	 */
	public void setTwin(MyGateway twin);
	
	/**
	 * Renvoie la gateway associée à celle dont on fait l'appel.
	 * @return {@link MyGateway}.
	 */
	public MyGateway getTwin();
}
