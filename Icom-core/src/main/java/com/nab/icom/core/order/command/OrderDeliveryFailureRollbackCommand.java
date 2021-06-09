package com.nab.icom.core.order.command;

public class OrderDeliveryFailureRollbackCommand {

	private final Long orderId;
	private final String failureReason;


	public OrderDeliveryFailureRollbackCommand(Long orderId, String failureReason) {
		super();
		this.orderId=orderId;
		this.failureReason=failureReason;
	}


	public Long getOrderId() {
		return orderId;
	}


	public String getFailureReason() {
		return failureReason;
	}

}
