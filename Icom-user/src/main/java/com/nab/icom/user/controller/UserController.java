package com.nab.icom.user.controller;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nab.icom.user.dto.CustomerDTO;
import com.nab.icom.user.model.OnlineUser;
import com.nab.icom.user.model.User;
import com.nab.icom.user.repository.OnlineUserRepository;
import com.nab.icom.user.service.CustomerService;

@RestController
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private CustomerService customerService;

	@Autowired
	private OnlineUserRepository onlineUserRepository;
	// ------------------- Create a customer
	// --------------------------------------------------------
	@RequestMapping(value = "/customer", method = RequestMethod.POST)
	public ResponseEntity<Resource<String>> postCustomer(@RequestBody CustomerDTO customer) {

    	LOGGER.debug("Creating Customer with First Name: {}", customer.getFirstName());
		try {
			customerService.saveCustomer(customer);
			Resource<String> userRes = new Resource<String>("SUCCESS");
			return new ResponseEntity<Resource<String>>(userRes, HttpStatus.OK);
		} catch (Exception ex) {
			LOGGER.error(" Customer existing  with samename: " + customer.getUserName());
			Resource<String> userRes = new Resource<String>("FAIL");
			return new ResponseEntity<Resource<String>>(userRes, HttpStatus.OK);
		}
	}
	@RequestMapping(value = "/customer", method = RequestMethod.GET)
	public ResponseEntity<Resource<String>> getCustomer() {

    	Resource<String> userRes = new Resource<String>("SUCCESS");
    	return new ResponseEntity<Resource<String>>(userRes, HttpStatus.OK);
	}
	/**
	 * Retrieve customer information
	 * @param authentication
	 * @return
	 */

	@RequestMapping(value = "/validateCustomer", method = RequestMethod.POST)
	public ResponseEntity<Resource<User>> validateCredential(@RequestParam String userId,@RequestParam String password) {


		LOGGER.debug("Retriving customer information for  userId: {}" + userId);
		OnlineUser onlineUser=onlineUserRepository.findByScreenName(userId);
		if(onlineUser!=null && StringUtils.equals(onlineUser.getPassword(), password)){
		User user=customerService.findCustomer(userId);
		Resource<User> userRes = new Resource<User>(user);
		return new ResponseEntity<Resource<User>>(userRes,HttpStatus.OK);
		}else{
			return null;
		}
	}

}
