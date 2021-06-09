package com.nab.icom.core.order.command;

public class OrderCancelCommand {

	private final Long orderId;

	public OrderCancelCommand(Long orderId) {
		super();
		this.orderId=orderId;
	}


	public Long getOrderId() {
		return orderId;
	}


}
