package com.nab.icom.core.order.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.axonframework.domain.AbstractAggregateRoot;

@Entity
@Table(name = "LINE_ITEM")
public class LineItem extends AbstractAggregateRoot<Long> {

	private static final long serialVersionUID = 2717666342574509152L;

	@Id
	private Long id;

	@Column(name="PRODUCT")
	private String product;

	@Column(name="QUANTITY")
	private Integer quantity;

	@Column(name="PRICE")
	private Double price;

	@Column(name="INVENTORY_ID")
	private Long inventoryId;

	@ManyToOne
	@JoinColumn(name="ORDER_ID", nullable=false)
	private Order order;

	@Override
	public Long getIdentifier() {
		return id;
	}

	public LineItem(){
		super();
	}

	public LineItem(Long id, String product, Integer quantity, Double price, Long inventoryId) {
		super();
		this.id = id;
		this.product = product;
		this.quantity = quantity;
		this.price = price;
		this.inventoryId = inventoryId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((product == null) ? 0 : product.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LineItem other = (LineItem) obj;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		return true;
	}

	public Long getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(Long inventoryId) {
		this.inventoryId = inventoryId;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

}
