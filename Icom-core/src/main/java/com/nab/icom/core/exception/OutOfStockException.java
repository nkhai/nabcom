package com.nab.icom.core.exception;

public class OutOfStockException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Long inventoryId;

	public OutOfStockException(Long inventoryId) {
		super();
		this.inventoryId = inventoryId;

	}

	@Override
	public String getMessage() {
		return "No stock for prouct ->" + inventoryId;
	}

	public Long getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(Long inventoryId) {
		this.inventoryId = inventoryId;
	}

}
