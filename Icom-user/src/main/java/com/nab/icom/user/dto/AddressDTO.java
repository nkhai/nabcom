package com.nab.icom.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class AddressDTO {

	private String appartment;
	private String street;
	private String province;
	private String state;
	private String pin;
	private String country;	
	@Override
	public String toString() {
		return "Address [ appartment=" + appartment + ", street=" + street + ", province=" + province
				+ ", state=" + state + ", pin=" + pin + ", country=" + country + "]";
	}

}
