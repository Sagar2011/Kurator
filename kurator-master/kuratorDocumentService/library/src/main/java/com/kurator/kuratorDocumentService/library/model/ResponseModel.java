package com.kurator.kuratorDocumentService.library.model;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Component;

@Component
public class ResponseModel {

    int status;
    String message;
    Collection<Document> resultList;
    int count;


    public ResponseModel(){

    }


	public ResponseModel(int statusCode, String message, ArrayList<Document> resultList, int count) {
		super();
		this.status = statusCode;
		this.message = message;
		this.resultList = resultList;
		this.count = count;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int statusCode) {
		this.status = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Collection<Document> getResultList() {
		return resultList;
	}


	public void setResultList(Collection<Document> resultList) {
		this.resultList = resultList;
	}


	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}
	
}
