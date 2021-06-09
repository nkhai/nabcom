package com.nab.icom.core.inventory.command.handler;

import java.util.Random;

import org.axonframework.commandhandling.annotation.CommandHandler;
import org.axonframework.repository.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.nab.icom.core.inventory.command.InventoryCreateCommad;
import com.nab.icom.core.inventory.model.Inventory;

@Component
public class InventoryCreationCommandHandler {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	@Qualifier(value = "inventoryRepository")
	private Repository<Inventory> inventoryRepository;

	@CommandHandler
	public void handleInventoryCreation(InventoryCreateCommad inventoryCreatedCommand) {
		Integer id=new Random().nextInt();
		logger.debug("InventoryCreationCommandHandler/create new inventory is executing....."+id);
		inventoryRepository.add(new Inventory(Long.valueOf(id), inventoryCreatedCommand.getSku(),inventoryCreatedCommand.getQuantity()));
	}
}
