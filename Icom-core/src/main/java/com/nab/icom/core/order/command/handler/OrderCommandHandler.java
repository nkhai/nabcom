package com.nab.icom.core.order.command.handler;

import java.util.Date;
import java.util.Random;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.nab.icom.common.core.dto.LineItemDTO;
import com.nab.icom.core.inventory.model.Inventory;
import com.nab.icom.core.order.command.OrderCancelCommand;
import com.nab.icom.core.order.command.OrderCreateCommand;
import com.nab.icom.core.order.command.OrderDeliveryFailureRollbackCommand;
import com.nab.icom.core.order.command.OrderUpdateCommand;
import com.nab.icom.core.order.model.LineItem;
import com.nab.icom.core.order.model.Order;
import com.nab.icom.core.order.model.OrderStatus;
import com.nab.icom.core.order.model.ProductStockOperation;

@Component
public class OrderCommandHandler {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier(value = "inventoryRepository")
	private Repository<Inventory> inventoryRepository;

	@Autowired
	@Qualifier(value = "orderRepository")
	private Repository<Order> orderRepository;

	@CommandHandler
	public void handleNewOrder(OrderCreateCommand orderCreatedCommand) {
		logger.debug("OrderCreationCommandHandler/create new order is executing.....");
		Order order = new Order(Long.valueOf(new Random().nextInt()));
		order.setOrderDate(new Date());
		order.setOrderStatus(OrderStatus.PAID);
		order.setUserId(orderCreatedCommand.getUserId());
		double total = 0;
		System.out.println("[Logger]orderCreatedCommand.getLineItems() : " + orderCreatedCommand.getLineItems());
		if (orderCreatedCommand.getLineItems() != null) {
			for (LineItemDTO lineItemDto : orderCreatedCommand.getLineItems()) {
				if (lineItemDto.getInventoryId() != null) {
					LineItem lineItem = new LineItem(new Random().nextLong(), lineItemDto.getProductId(),
							lineItemDto.getQuantity(), lineItemDto.getPrice(), lineItemDto.getInventoryId());
					total = total + lineItemDto.getPrice();
					order.addLineItem(lineItem);
					Inventory inventory = inventoryRepository.load(lineItemDto.getInventoryId());
					inventory.updateProductStock(lineItemDto.getQuantity(), ProductStockOperation.DEPRECIATE);
				}
			}
		}
		order.setTotal(total);
		order.notifyOrderCreation();
		orderRepository.add(order);
	}

	@CommandHandler
	public void handleOrderUpdate(OrderUpdateCommand orderUpdateCommand) {
		logger.debug("OrderUpdate command is executing..... "+orderUpdateCommand.getOrderId()+"/"+orderUpdateCommand.getOrderStatus());
		Order order = orderRepository.load(orderUpdateCommand.getOrderId());
		order.updateOrderStatus(orderUpdateCommand.getOrderStatus());
	}

	@CommandHandler
	public void handleOrderCancel(OrderCancelCommand orderCancelCommand) {
		logger.debug("Order cancelling command is executing..... "+orderCancelCommand.getOrderId());
		Order order = orderRepository.load(orderCancelCommand.getOrderId());
		order.cancelOrder();
		rollbackInventory(order);
	}

	@CommandHandler
	public void handleOrderDeliveryFailure(OrderDeliveryFailureRollbackCommand  orderDeliveryFailureRollbackCommand) {
		logger.debug("Order delivery failure command is executing..... "+orderDeliveryFailureRollbackCommand.getOrderId());
		Order order = orderRepository.load(orderDeliveryFailureRollbackCommand.getOrderId());
		order.updateOrderStatus(OrderStatus.DELIVERY_FAILED);
		rollbackInventory(order);
	}

	private void rollbackInventory(Order order){
		for(LineItem lineItem:order.getLineItems()){
			Inventory inventory = inventoryRepository.load(lineItem.getInventoryId());
			inventory.updateProductStock(lineItem.getQuantity(), ProductStockOperation.ADD);
		}
	}

}
