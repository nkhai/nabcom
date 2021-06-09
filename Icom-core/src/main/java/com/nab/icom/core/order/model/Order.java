package com.nab.icom.core.order.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.axonframework.domain.AbstractAggregateRoot;

import com.nab.icom.common.core.dto.LineItemDTO;
import com.nab.icom.common.core.order.event.OrderCancelledEvent;
import com.nab.icom.common.core.order.event.OrderCreatedEvent;
import com.nab.icom.common.core.order.event.OrderUpdatedEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Entity(name="order")
@Table(name = "CUSTOMER_ORDER")
public class Order extends AbstractAggregateRoot<Long> {

	private transient final Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final long serialVersionUID = 2717666342574509152L;

	@Id
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name="ORDER_STATUS")
	private OrderStatus orderStatus;

	@Column(name="TOTAL")
	private Double total;

	@Column(name="ORDER_DATE")
	private Date orderDate;

	@Column(name="USER_ID")
	private String userId;

	@OneToMany(cascade = CascadeType.ALL, mappedBy="order",orphanRemoval=true)
	private Set<LineItem> lineItems;

	public Order() {

	}

	public Order(Long id) {
		super();
		this.id = id;
	}

	public Set<LineItem> getLineItems() {
		return lineItems;
	}

	public void setLineItems(Set<LineItem> lineItems) {
		this.lineItems = lineItems;
	}

	public void addLineItem(LineItem lineItem){
		if(this.lineItems==null){
			this.lineItems=new HashSet<LineItem>();
		}
		lineItem.setOrder(this);
		this.lineItems.add(lineItem);
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

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void notifyOrderCreation() {
		List<LineItemDTO> lineItemDTOs = new ArrayList<LineItemDTO>();
		for (LineItem lineItem : lineItems) {
			lineItemDTOs.add(new LineItemDTO(lineItem.getProduct(), lineItem.getPrice(), lineItem.getQuantity(),
					lineItem.getInventoryId()));
		}
		registerEvent(new OrderCreatedEvent(id, userId, orderStatus.name(), total, orderDate, lineItemDTOs));
	}

	public void updateOrderStatus(OrderStatus orderStatus){
		this.orderStatus=orderStatus;
		registerEvent(new OrderUpdatedEvent(this.id, orderStatus.name(), new Date(),null));
	}

	public void cancelOrder(){
		this.orderStatus=OrderStatus.CANCELLED;
		registerEvent(new OrderUpdatedEvent(this.id, orderStatus.name(), new Date(),null));
		logger.debug("Registered OrderUpdatedEvent");
		registerEvent(new OrderCancelledEvent(this.id));
		logger.debug("Registered OrderCancelledEvent");
	}

	public void notifyOderFailure(String failureReason){
		this.orderStatus=OrderStatus.DELIVERY_FAILED;
		registerEvent(new OrderUpdatedEvent(this.id, orderStatus.name(), new Date(),failureReason));
	}

}
