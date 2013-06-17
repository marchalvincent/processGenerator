package fr.lip6.move.processGenerator.bpmn2;

import org.eclipse.bpmn2.impl.ExclusiveGatewayImpl;

/**
 * Implémente une "exclusive gateway" tout en offrant la possibilité de lier cet objet à une autre {@link MyGateway}.
 * @author Vincent
 *
 */
public class MyExclusiveGatewayImpl extends ExclusiveGatewayImpl implements MyExclusiveGateway {

	private MyGateway twin;

	public MyExclusiveGatewayImpl() {
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
