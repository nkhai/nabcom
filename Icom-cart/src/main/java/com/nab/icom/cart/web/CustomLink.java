package com.nab.icom.cart.web;

import org.springframework.hateoas.Link;


public class CustomLink extends  Link{
	 public CustomLink(Link link) {
	        super(link.getHref().replaceAll("%7B", "{").replaceAll("%7D", "}"), link.getRel());
	    }
}
