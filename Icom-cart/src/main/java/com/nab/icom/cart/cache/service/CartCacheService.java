package com.nab.icom.cart.cache.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import com.nab.icom.cart.dto.CustomerCartDTO;

@Component
public class CartCacheService {
	 private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CacheManager cacheManager;
	/**
	 * Adding user cart to cache
	 */
	public void updateUserCartInCache(CustomerCartDTO userCart){
		logger.debug("updating/adding user  "+ userCart.getUserId() +"  into cache---->");
		cacheManager.getCache("customerCache").put(userCart.getUserId(), userCart);
	}

	/**
	 * Getting user cart from cache
	 * @param userId
	 */
	public CustomerCartDTO getUserCartFromCache(String userId){

		ValueWrapper value = cacheManager.getCache("customerCache").get(userId);
		if(value != null){
			logger.debug("Returning cache for the  customer ----->  "+ userId );
			return (CustomerCartDTO) value.get();
		}else{
			logger.debug("Customer is not existin the cache  ----->  "+ userId );
			CustomerCartDTO userCartDto = new CustomerCartDTO(userId);
			updateUserCartInCache(userCartDto);
			logger.debug("Customer is added to the cache  ----->  "+ userId );
			return userCartDto;
		}
	}
}
