package com.nab.icom.user.service.impl;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nab.icom.user.controller.UserController;
import com.nab.icom.user.dto.CustomerDTO;
import com.nab.icom.user.dto.UserCredentialDTO;
import com.nab.icom.user.exception.InvalidUserException;
import com.nab.icom.user.model.Address;
import com.nab.icom.user.model.OnlineUser;
import com.nab.icom.user.model.RoleEnum;
import com.nab.icom.user.model.User;
import com.nab.icom.user.model.UserRole;

import com.nab.icom.user.repository.OnlineUserRepository;
import com.nab.icom.user.repository.UserRepository;
import com.nab.icom.user.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private OnlineUserRepository onlineUserRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public void saveCustomer(CustomerDTO customer) {

		LOGGER.info("Start");
    	LOGGER.debug("Saving Customer with First Name: {}", customer.getFirstName());

		OnlineUser onlineUser=new OnlineUser();
		onlineUser.setScreenName(customer.getUserName());
		onlineUser.setPassword(customer.getPassword());
		onlineUser.setActive(true);
		onlineUser.addRole(new UserRole(RoleEnum.CUSTOMER_READ));
		onlineUser.addRole(new UserRole(RoleEnum.ORDER_READ));
		onlineUser.addRole(new UserRole(RoleEnum.PRODUCT_READ));
		onlineUser.addRole(new UserRole(RoleEnum.ORDER_WRITE));
		onlineUserRepository.save(onlineUser);

		User user=new User();
		user.setUserId(customer.getUserName());
		user.setFirstName(customer.getFirstName());
		user.setLastName(customer.getLastName());
		user.setEmail(customer.getEmail());
		user.setPhone(customer.getPhone());
		if(customer.getBillingAddress()!=null){
			Address billAddress=new Address();
			billAddress.setAppartment(customer.getBillingAddress().getAppartment());
			billAddress.setCountry(customer.getBillingAddress().getCountry());
			billAddress.setPin(customer.getBillingAddress().getPin());
			billAddress.setProvince(customer.getBillingAddress().getProvince());
			billAddress.setState(customer.getBillingAddress().getState());
			billAddress.setStreet(customer.getBillingAddress().getStreet());
			user.setBillingAddress(billAddress);
		}
		if(customer.getShippingAddress()!=null){
			Address shippingAddress=new Address();
			shippingAddress.setAppartment(customer.getBillingAddress().getAppartment());
			shippingAddress.setCountry(customer.getBillingAddress().getCountry());
			shippingAddress.setPin(customer.getBillingAddress().getPin());
			shippingAddress.setProvince(customer.getBillingAddress().getProvince());
			shippingAddress.setState(customer.getBillingAddress().getState());
			shippingAddress.setStreet(customer.getBillingAddress().getStreet());
			user.setShippingAddress(shippingAddress);
		}
		userRepository.save(user);
		LOGGER.info("End");
	}

	@Override
	public User findCustomer(String customerId) {

		LOGGER.info("Start");
    	LOGGER.debug("Finding Customer with customerId: {}", customerId);
		User user=userRepository.findByUserId(customerId);
		if(user==null){
		 throw new InvalidUserException(customerId);
		}
		LOGGER.info("Ending");
     	return user;
	}


	public User validateCustomer(UserCredentialDTO userCredentialDTO) {

		LOGGER.info("Start");
    	LOGGER.debug("Validaing Customer with Username: {}", userCredentialDTO.getUserName());
		OnlineUser onlineUser=onlineUserRepository.findByScreenName(userCredentialDTO.getUserName());
		if(onlineUser==null||(!userCredentialDTO.getPassword().equals(onlineUser.getPassword()))){
		 throw new InvalidUserException(userCredentialDTO.getUserName());
		}
		User user=userRepository.findByUserId(userCredentialDTO.getUserName());
		LOGGER.info("Ending");
		return user;
	}

}
