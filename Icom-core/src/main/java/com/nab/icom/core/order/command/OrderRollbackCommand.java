package com.nab.icom.core.order.command;

public class OrderRollbackCommand {

	private final Long orderId;


	public OrderRollbackCommand(Long orderId) {
		super();
		this.orderId=orderId;
	}


	public Long getOrderId() {
		return orderId;
	}

}
