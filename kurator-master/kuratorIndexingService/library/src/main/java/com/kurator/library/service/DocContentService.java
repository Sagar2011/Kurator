package com.kurator.library.service;

import com.kurator.library.model.DocumentContent;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface DocContentService {

    void saveAllCrawlingDetails(DocumentContent documentContent);
    DocumentContent fetchContent(UUID documentId);
}
