package fr.lip6.move.processGenerator.bpmn2;

import org.eclipse.bpmn2.ExclusiveGateway;

/**
 * Permet la surcharge des {@link ExclusiveGateway}s de bpmn2.
 * @author Vincent
 *
 */
public interface MyExclusiveGateway extends ExclusiveGateway, MyGateway {
}
