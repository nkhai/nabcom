package com.nab.icom.user.service;

import com.nab.icom.user.dto.CustomerDTO;
import com.nab.icom.user.model.User;

public interface CustomerService {

	void saveCustomer(CustomerDTO customer);

	User findCustomer(String customerId);
}
