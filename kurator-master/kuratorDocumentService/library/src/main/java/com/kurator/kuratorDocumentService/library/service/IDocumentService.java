package com.kurator.kuratorDocumentService.library.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import com.kurator.kuratorDocumentService.library.model.Document;

import javax.servlet.http.HttpServletRequest;

public interface IDocumentService {

	ArrayList<Document> findAllDocuments();
	void saveDocument(Document document);
	Document findById(UUID documentId);
	ArrayList<Document> findByUrl(String url);
	boolean checkURL(Document document, String username);
	boolean existsByUrl(String url);
	String findUserName(HttpServletRequest request);
    ArrayList<Document> findByDate(LocalDate date);
	ArrayList<Document> findByAddedBy(HttpServletRequest request);
}
