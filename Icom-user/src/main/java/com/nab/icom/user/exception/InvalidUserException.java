package com.nab.icom.user.exception;

public class InvalidUserException extends RuntimeException{
	private static final long serialVersionUID = -8326578133257869324L;
	private String user;
	public InvalidUserException(String user){
		this.user=user;
	}

	@Override
	public String getMessage() {
		return "InvalidUser ->" + user;
	}
}
