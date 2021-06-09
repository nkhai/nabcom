package com.nab.icom.core.order.command;

import com.nab.icom.core.order.model.OrderStatus;

public class OrderUpdateCommand {

	private final Long orderId;

	private final OrderStatus orderStatus;

	public Long getOrderId() {
		return orderId;
	}

	public OrderUpdateCommand(Long orderId, OrderStatus orderStatus) {
		super();
		this.orderId = orderId;
		this.orderStatus = orderStatus;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
}
