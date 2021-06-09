package com.nab.icom.core.inventory.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.axonframework.domain.AbstractAggregateRoot;

import com.nab.icom.common.core.inventory.event.InventoryUpdateEvent;
import com.nab.icom.core.exception.OutOfStockException;
import com.nab.icom.core.order.model.ProductStockOperation;

@Entity
@Table(name = "INVENTORY")
public class Inventory extends AbstractAggregateRoot<Long> {

	private static final long serialVersionUID = 2717666342574509152L;

	@Id
	private Long id;

	@Column(name="SKU")
	private String sku;

	@Column(name="QUANTITY")
	private Integer quantity;

	public Inventory() {

	}

	public Inventory(Long id, String sku, Integer quantity) {
		super();
		this.id = id;
		this.sku = sku;
		this.quantity = quantity;
		registerEvent(new InventoryUpdateEvent(id, sku, quantity));
	}

	@Override
	public Long getIdentifier() {
		return this.id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public void updateProductStock(Integer count, ProductStockOperation stockoperation) {
		if (stockoperation.equals(ProductStockOperation.DEPRECIATE)) {
			if (this.quantity - count >= 0) {
				this.quantity = this.quantity - count;
			} else {
				throw new OutOfStockException(this.id);
			}
		} else {
			this.quantity = this.quantity + count;
		}
		registerEvent(new InventoryUpdateEvent(id, sku, quantity));
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Inventory [id=").append(id).append(", sku=").append(sku).append(", quantity=").append(quantity)
				.append("]");
		return builder.toString();
	}

}
