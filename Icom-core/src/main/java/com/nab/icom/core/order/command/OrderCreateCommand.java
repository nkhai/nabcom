package com.nab.icom.core.order.command;

import java.util.List;

import com.nab.icom.common.core.dto.LineItemDTO;

public class OrderCreateCommand {

	private final String userId;

	private final List<LineItemDTO> lineItems;

	public String getUserId() {
		return userId;
	}

	public OrderCreateCommand(String userId, List<LineItemDTO> lineItems) {
		super();
		this.userId = userId;
		this.lineItems = lineItems;
	}

	public List<LineItemDTO> getLineItems() {
		return lineItems;
	}
}
