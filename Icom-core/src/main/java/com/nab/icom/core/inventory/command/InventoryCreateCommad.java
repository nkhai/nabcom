
package com.nab.icom.core.inventory.command;

public class InventoryCreateCommad {

	private final String sku;

	private final Integer quantity;

	public InventoryCreateCommad(String sku, Integer quantity) {
		this.sku = sku;
		this.quantity = quantity;
	}

	public String getSku() {
		return sku;
	}

	public Integer getQuantity() {
		return quantity;
	}

}
