package fr.lip6.move.processGenerator.bpmn2;

import org.eclipse.bpmn2.impl.ParallelGatewayImpl;

/**
 * Implémente une "parallel gateway" tout en offrant la possibilité de lier cet objet à une autre {@link MyGateway}.
 * @author Vincent
 *
 */
public class MyParallelGatewayImpl extends ParallelGatewayImpl implements MyParallelGateway {
	
	private MyGateway twin;
	
	public MyParallelGatewayImpl() {
		super();
	}
	
	@Override
	public void setTwin(MyGateway twin) {
		this.twin = twin;
	}
	
	@Override
	public MyGateway getTwin() {
		return this.twin;
	}
}
