package com.nab.icom.core.order.saga.handler;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.saga.annotation.AbstractAnnotatedSaga;
import org.axonframework.saga.annotation.EndSaga;
import org.axonframework.saga.annotation.SagaEventHandler;
import org.axonframework.saga.annotation.StartSaga;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.nab.icom.common.core.order.event.OrderCancelledEvent;
import com.nab.icom.common.core.order.event.OrderCreatedEvent;
import com.nab.icom.common.delivery.event.OrderDeliveredEvent;
import com.nab.icom.common.delivery.event.OrderDeliveryFailedEvent;
import com.nab.icom.common.shipping.event.OrderShippedEvent;
import com.nab.icom.core.order.command.OrderDeliveryFailureRollbackCommand;
import com.nab.icom.core.order.command.OrderUpdateCommand;
import com.nab.icom.core.order.model.OrderStatus;

public class OrderProcessSaga extends AbstractAnnotatedSaga {
	private final Logger logger = LoggerFactory.getLogger(OrderProcessSaga.class);
	private static final long serialVersionUID = -7209131793034337691L;
	private Long orderId;

	@Autowired
	private  transient CommandGateway commandGateway;

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}


	@StartSaga
	@SagaEventHandler(associationProperty = "orderId")
	public void handleOrderCreationEvent(OrderCreatedEvent orderCreatedEvent) {
		logger.debug("New order created event request recieved ---->" + orderCreatedEvent.getOrderId());
		this.orderId = orderCreatedEvent.getOrderId();

	}

	@SagaEventHandler(associationProperty = "orderId")
	public void handleOrderShippedEvent(OrderShippedEvent orderShippedEvent) {
		System.out.println("OrderProcessSaga.handleOrderShippedEvent");
		logger.debug("Order shipping event request recieved  for order---->" + orderShippedEvent.getOrderId());
		commandGateway.send(new OrderUpdateCommand(orderShippedEvent.getOrderId(),OrderStatus.SHIPPED));
	}

	@EndSaga
	@SagaEventHandler(associationProperty = "orderId")
	public void handleOrderCanceledEvent(OrderCancelledEvent orderCancelledEvent) {
		System.out.println("EndSaga: OrderProcessSaga.handleOrderCanceledEvent");
		logger.debug("Order cancelled by the user---->" + orderCancelledEvent.getOrderId());
	}

	@EndSaga
	@SagaEventHandler(associationProperty = "orderId")
	public void handleOrderDeliveredEvent(OrderDeliveredEvent orderDeliveredEvent) {
		System.out.println("EndSaga: OrderProcessSaga.handleOrderDeliveredEvent");
		logger.debug("Order delivered  event request recieved  for order---->" + orderDeliveredEvent.getOrderId());
		commandGateway.send(new OrderUpdateCommand(orderDeliveredEvent.getOrderId(),OrderStatus.DELIVERED));
	}

	@EndSaga
	@SagaEventHandler(associationProperty = "orderId")
	public void handleOrderDeliveryFailureEvent(OrderDeliveryFailedEvent orderDeliveryFailedEvent) {
		System.out.println("EndSaga: OrderProcessSaga.handleOrderDeliveryFailureEvent");
		logger.debug("Order delivery failed for order---->" + orderDeliveryFailedEvent.getOrderId());
		commandGateway.send(new OrderDeliveryFailureRollbackCommand(orderDeliveryFailedEvent.getOrderId(),
				orderDeliveryFailedEvent.getFailureReason()));
	}

	public CommandGateway getCommandGateway() {
		return commandGateway;
	}

	public void setCommandGateway(CommandGateway commandGateway) {
		this.commandGateway = commandGateway;
	}
}
