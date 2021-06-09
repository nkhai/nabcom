package com.nab.icom.common.core.order.event;

public class OrderCancelledEvent {
	private final Long orderId;

	public OrderCancelledEvent(Long orderId) {
		super();
		this.orderId = orderId;
	}

	public Long getOrderId() {
		return orderId;
	}

}
