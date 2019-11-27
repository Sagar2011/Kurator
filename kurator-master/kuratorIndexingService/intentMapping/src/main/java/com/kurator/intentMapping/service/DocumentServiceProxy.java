package com.kurator.intentMapping.service;

import com.kurator.library.model.Document;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@FeignClient(name="kuratorDocumentWWWService")
public interface DocumentServiceProxy {

    @PostMapping("/documents")
    void addDocs(@RequestBody Document document);

    @GetMapping("/documents/{documentId}")
    ResponseEntity<?> getById(@RequestParam("documentId") UUID documentId) ;
}

