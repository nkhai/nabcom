package com.nab.icom.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class CustomerDTO {

	private String userName;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private AddressDTO billingAddress;
	private AddressDTO shippingAddress;
	private String password;



}
