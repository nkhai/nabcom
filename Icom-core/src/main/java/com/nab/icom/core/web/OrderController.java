package com.nab.icom.core.web;

import javax.transaction.Transactional;

import org.axonframework.commandhandling.CommandExecutionException;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nab.icom.common.core.dto.OrderDTO;
import com.nab.icom.core.exception.OutOfStockException;
import com.nab.icom.core.order.command.OrderCancelCommand;
import com.nab.icom.core.order.command.OrderCreateCommand;

@RestController
@RequestMapping("/order")
public class OrderController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CommandGateway commandGateway;

	@RequestMapping(method = RequestMethod.POST)
	@Transactional
	@ResponseBody
	public OrderCreationStatus createOrder(@RequestBody OrderDTO orderDTO) {

		try {
			OrderCreateCommand orderCommand = new OrderCreateCommand(orderDTO.getUserId(), orderDTO.getLineItems());
			logger.info("send and waitting command");
			commandGateway.sendAndWait(orderCommand);
			return OrderCreationStatus.SUCCESS;
	     } catch (CommandExecutionException ex) {
				Throwable e = ex.getCause();
				logger.error("Error while creating new order",e);
				if (e instanceof OutOfStockException) {
					return OrderCreationStatus.OUT_OF_STOCK;
				} else {
					return OrderCreationStatus.FAILED;
				}
			}


	}

	@RequestMapping(value = "{orderId}", method = RequestMethod.DELETE)
	@Transactional
	@ResponseBody
	public void cancelOrder(@PathVariable Long orderId) {
		OrderCancelCommand orderCommand = new OrderCancelCommand(orderId);
		commandGateway.send(orderCommand);
	}
}
