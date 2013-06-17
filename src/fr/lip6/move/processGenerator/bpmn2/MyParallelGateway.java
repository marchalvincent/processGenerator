package fr.lip6.move.processGenerator.bpmn2;

import org.eclipse.bpmn2.ParallelGateway;

/**
 * Permet la surcharge des {@link ParallelGateway}s de bpmn2.
 * @author Vincent
 *
 */
public interface MyParallelGateway extends ParallelGateway, MyGateway {
}
