package com.kurator.kuratorUserService.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "kuratorDocumentWWWService")
public interface DocumentServiceProxy {

    @GetMapping("/documents/{documentId}")
    ResponseEntity<?> getBydocumentId(@PathVariable("documentId") UUID documentId);


}
