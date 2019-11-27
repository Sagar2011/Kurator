package com.kurator.kuratorDocumentService.library.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import com.kurator.kuratorDocumentService.library.model.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

@Repository
public interface DocumentRepository extends MongoRepository<Document, String> {
	Document findByDocumentId(UUID id);
	ArrayList<Document> findByAddedBy(String addedBy);
	ArrayList<Document> findByUrl(String url);
	boolean existsByUrl(String url);
	ArrayList<Document> findByAddedOnBetween(LocalDateTime date1, LocalDateTime date2);
}
