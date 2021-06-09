package com.nab.icom.order.history.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.nab.icom.order.history.model.Order;

@RepositoryRestResource(collectionResourceRel = "orderHistory", path = "orderHistory")
public interface OrderHistoryRepository extends MongoRepository<Order, String>  {

	@RestResource(exported = false)
	public Order findByOrderId(@Param("orderId") Long orderId);

	public List<Order> findByUserId(@Param("userId") String userId);

	public List<Order> findByOrderStatus(@Param("orderStatus") String orderStatus);

    @Override
    public Order findOne(String id);

    @Override
    @RestResource(exported = false)
    public Page<Order> findAll(Pageable pageable);

}
