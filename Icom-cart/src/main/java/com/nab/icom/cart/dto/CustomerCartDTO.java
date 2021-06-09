package com.nab.icom.cart.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;


public class CustomerCartDTO implements Serializable {

	public CustomerCartDTO(){

	}

	public CustomerCartDTO(String userId) {
		super();
		this.userId = userId;
	}

	private static final long serialVersionUID = -7998886810561987498L;

	private String userId;

	private Timestamp activeSince;

	private String coupen;

	private List<LineItem> lineItems;


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public Timestamp getActiveSince() {
		return activeSince;
	}


	public void setActiveSince(Timestamp activeSince) {
		this.activeSince = activeSince;
	}


	public String getCoupen() {
		return coupen;
	}


	public void setCoupen(String coupen) {
		this.coupen = coupen;
	}


	public List<LineItem> getLineItems() {
		return lineItems;
	}


	public void setLineItems(List<LineItem> lineItem) {
		this.lineItems = lineItem;
	}




}
