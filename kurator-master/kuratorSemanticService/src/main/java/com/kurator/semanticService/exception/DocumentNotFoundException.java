package com.kurator.semanticService.exception;



public class DocumentNotFoundException extends Exception {

    String message;

    public DocumentNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    public DocumentNotFoundException() {
        super();
    }
}
