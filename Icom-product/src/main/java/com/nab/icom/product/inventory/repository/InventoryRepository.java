package com.nab.icom.product.inventory.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.nab.icom.product.inventory.model.Inventory;


@RepositoryRestResource(collectionResourceRel = "inventory", path = "inventory")
public interface InventoryRepository extends MongoRepository<Inventory, String>  {

	public Inventory findByInventoryId(@Param("inventoryId") Long  inventoryId);

	public Inventory findBySku(@Param("sku") String  sku);

}
