package com.kurator.kuratorUserService.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class ResponseModel {

    int statusCode;
    String message;
    ArrayList<?> result;


    public ResponseModel(){

    }

    public ResponseModel(int statusCode, String message, ArrayList<User> result) {
        this.statusCode = statusCode;
        this.message = message;
        this.result = result;
    }



    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<?> getResult() {
        return result;
    }

    public void setResult(ArrayList<?> result) {
        this.result = result;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }


}
