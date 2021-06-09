package com.nab.icom.cart.web;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nab.icom.cart.dto.CustomerCartDTO;

@RestController
public class CustomerCartApplicationController {
	 @RequestMapping(path="/",method=RequestMethod.GET)
     public HttpEntity<ResourceSupport> getCustomerCartInfo(){
		 ResourceSupport resourceSupport=new ResourceSupport();
		 resourceSupport.add(linkTo(methodOn(CustomerCartApplicationController.class).getCustomerCartInfo()).withSelfRel());
		 resourceSupport.add(linkTo(methodOn(CustomerCartController.class).updateCustomerCartToCache(new CustomerCartDTO())).withRel("updateCustomerCart"));
		 Link link = linkTo(CustomerCartController.class).slash("/{userId}").withRel("getCustomerCart");
		 resourceSupport.add(new CustomLink(link));
		 return new ResponseEntity<ResourceSupport>(resourceSupport, HttpStatus.OK);
	  }
}
