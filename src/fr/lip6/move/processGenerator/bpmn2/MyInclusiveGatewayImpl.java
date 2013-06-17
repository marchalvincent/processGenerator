package fr.lip6.move.processGenerator.bpmn2;

import org.eclipse.bpmn2.impl.InclusiveGatewayImpl;

/**
 * Implémente une "inclusive gateway" tout en offrant la possibilité de lier cet objet à une autre {@link MyGateway}.
 * @author Vincent
 *
 */
public class MyInclusiveGatewayImpl extends InclusiveGatewayImpl implements MyInclusiveGateway {

	private MyGateway twin;

	public MyInclusiveGatewayImpl() {
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
