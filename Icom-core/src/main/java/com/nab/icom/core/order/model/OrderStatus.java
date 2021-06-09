package com.nab.icom.core.order.model;

public enum OrderStatus {

	PAID(1), CANCELLED(5), SHIPPED(2), DELIVERED(3), DELIVERY_FAILED(4);

	private int value;

	private OrderStatus(int value) {
	    this.value = value;
	}

	public int getValue() {
		    return this.value;
	}


}
