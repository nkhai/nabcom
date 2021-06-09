package com.nab.icom.cart.web;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nab.icom.cart.cache.service.CartCacheService;
import com.nab.icom.cart.dto.CustomerCartDTO;


@RestController
@RequestMapping(path="/customerCart")
public class CustomerCartController {

	@Autowired
	private CartCacheService cacheService;


	 @RequestMapping(method=RequestMethod.POST)
     public HttpEntity<Void> updateCustomerCartToCache( @RequestBody CustomerCartDTO cusomerCart ) {
		    cacheService.updateUserCartInCache(cusomerCart);
	        return new ResponseEntity<Void>( HttpStatus.OK);
	  }

	 @RequestMapping(path="/{userId}",method=RequestMethod.GET)
     public HttpEntity<CustomerCartDTO> getCustomerCartFromCache( @PathVariable String userId ) {
		  CustomerCartDTO customerCartDto= cacheService.getUserCartFromCache(userId);
	       return new ResponseEntity<CustomerCartDTO>(customerCartDto, HttpStatus.OK);
	  }
}
