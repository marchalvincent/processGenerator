package fr.lip6.move.processGenerator.bpmn2;



public class MyBpmn2Factory {

	public static final MyBpmn2Factory eINSTANCE = new MyBpmn2Factory();
	private MyBpmn2Factory() {}
	
	public MyParallelGateway createParallelGateway() {
		return new MyParallelGatewayImpl();
	}
	
	public MyInclusiveGateway createInclusiveGateway() {
		return new MyInclusiveGatewayImpl();
	}

	public MyExclusiveGateway createExclusiveGateway() {
		return new MyExclusiveGatewayImpl();
	}
}
