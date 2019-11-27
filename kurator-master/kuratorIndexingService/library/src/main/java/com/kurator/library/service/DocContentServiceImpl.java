package com.kurator.library.service;

//import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurator.library.documentContentRepo.DocumentContentRepository;
//import com.kurator.library.model.Document;
import com.kurator.library.model.DocumentContent;
//import com.kurator.library.model.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
//import java.util.Collection;
import java.util.UUID;

@Service
public class DocContentServiceImpl implements DocContentService {

    @Autowired
    DocumentContentRepository docContentRepo;


//DocumentContent doc = new DocumentContent(1,)

    public void saveAllCrawlingDetails(DocumentContent documentContent) {
        docContentRepo.save(documentContent);
    }
    public DocumentContent fetchContent(UUID documentId) {
        return docContentRepo.findByDocumentId(documentId);
    }


//DocumentContent doc = new DocumentContent(1,)

    public ArrayList<String> findingTermsByDocId(UUID docId)
    {
        return docContentRepo.findTermsListByDocumentId(docId);
    }
    public DocumentContent findingDocumentContentByDocId(UUID docId)
    {
        return docContentRepo.findDocumentByDocumentId(docId);
    }
    public void saveAll(DocumentContent documentContent)
    {
        docContentRepo.save(documentContent);
    }
//    public ArrayList<String> gettingIntentFromDocumentId(UUID docId)
//    {
//        return docContentRepo.findIntentListByDocumentId(docId);
//    }


}
