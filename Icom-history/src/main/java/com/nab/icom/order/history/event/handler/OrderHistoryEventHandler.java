package com.nab.icom.order.history.event.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.axonframework.domain.Message;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventhandling.annotation.Timestamp;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nab.icom.common.core.dto.LineItemDTO;
import com.nab.icom.common.core.order.event.OrderCreatedEvent;
import com.nab.icom.common.core.order.event.OrderUpdatedEvent;
import com.nab.icom.order.history.model.LineItem;
import com.nab.icom.order.history.model.Order;
import com.nab.icom.order.history.repository.OrderHistoryRepository;

@Component
public class OrderHistoryEventHandler {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private OrderHistoryRepository orderHistoryRepository;

	@EventHandler
	public void handleOrderCreationEvent(OrderCreatedEvent event, Message eventMessage,
			@Timestamp DateTime moment) {
		logger.debug("New order creation  message recieved -------->" + event.getOrderId() + " for user " + event.getUserId());
		Order order =new Order();
		order.setOrderId(event.getOrderId());
		order.setUserId(event.getUserId());
		order.setCreationDate(new Date());
		order.setOrderStatus(event.getOrderStatus());
		List<LineItem> lineItems=new ArrayList<LineItem>();
		if(event.getLineItems()!=null){
			for(LineItemDTO lineItemDTO:event.getLineItems()){
				LineItem lineItem=new LineItem();
				lineItem.setProductId(lineItemDTO.getProductId());
				lineItem.setPrice(lineItemDTO.getPrice());
				lineItem.setQuantity(lineItemDTO.getQuantity());
				lineItems.add(lineItem);
			}
		}
		order.setLineItems(lineItems);
		orderHistoryRepository.save(order);

	}

	@EventHandler
	public void handleOrderUpdatedEvent(OrderUpdatedEvent event, Message eventMessage,
			@Timestamp DateTime moment) {
		logger.debug("Order update  message recieved -------->" + event.getOrderId() + "/ " + event.getOrderStatus());
		Order order =orderHistoryRepository.findByOrderId(event.getOrderId());
		order.setOrderStatus(event.getOrderStatus());
		if(event.getOrderStatus().equals("SHIPPED")){
			order.setShippedDate(event.getDate());
		}else if(event.getOrderStatus().equals("DELIVERED")){
			order.setDeliveredDate(event.getDate());
		}else if(event.getOrderStatus().equals("CANCELLED")){
			order.setCancelledDate(event.getDate());
		}else if(event.getOrderStatus().equals("DELIVERY_FAILED")){
			order.setDeliveryFailReason(event.getDescription());
		}
		orderHistoryRepository.save(order);
	}


}
