package com.kurator.kuratorUserService.exception;

public class UsersNotFound extends Exception {
	String message;

    public UsersNotFound()
    {
    	super();
    }
    
    public UsersNotFound(String message) {
    	super(message);
    	this.message = message;
    }
}
