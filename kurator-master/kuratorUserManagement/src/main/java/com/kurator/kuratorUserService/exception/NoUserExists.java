package com.kurator.kuratorUserService.exception;

public class NoUserExists extends Exception {
     String message;
    public NoUserExists() {
    }

    public NoUserExists(String message) {
        super();
        this.message=message;
    }
}
