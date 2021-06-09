package com.nab.icom.product.inventory.event.handler;

import org.axonframework.domain.Message;
import org.axonframework.eventhandling.annotation.EventHandler;
import org.axonframework.eventhandling.annotation.Timestamp;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nab.icom.common.core.inventory.event.InventoryUpdateEvent;
import com.nab.icom.product.inventory.model.Inventory;
import com.nab.icom.product.inventory.repository.InventoryRepository;

@Component
public class InventoryEventHandler {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private InventoryRepository inventoryRepository;

	@EventHandler
	public void handleInventoryUpdates(InventoryUpdateEvent event, Message eventMessage,
			@Timestamp DateTime moment) {
		logger.debug("Inventory creation/update  message recieved -------->" + event.getId() + "/" + event.getSku());
		Inventory inventory= inventoryRepository.findByInventoryId(event.getId());
		if(inventory==null){
			logger.debug("Inventory not existing - creating new one............");
			inventory=new Inventory();
			inventory.setInventoryId(event.getId());
			inventory.setSku(event.getSku());
		}else{
			logger.debug("Inventory  existing - updating...............");
		}
		inventory.setQuantity(event.getQuantity());
		inventoryRepository.save(inventory);

	}

}
