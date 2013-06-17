package fr.lip6.move.processGenerator.bpmn2;

import org.eclipse.bpmn2.InclusiveGateway;


/**
 * Permet la surcharge des {@link InclusiveGateway}s de bpmn2.
 * @author Vincent
 *
 */
public interface MyInclusiveGateway extends InclusiveGateway, MyGateway {
}
