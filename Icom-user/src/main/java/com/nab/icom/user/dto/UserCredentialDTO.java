package com.nab.icom.user.dto;

public class UserCredentialDTO {
	private String userName;
	private String password;
	public UserCredentialDTO(){

	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

}
