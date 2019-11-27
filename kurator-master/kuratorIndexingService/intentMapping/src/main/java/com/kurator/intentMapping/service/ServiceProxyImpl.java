package com.kurator.intentMapping.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurator.library.model.Document;
import com.kurator.library.model.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

@Service
public class ServiceProxyImpl {
    @Autowired
    SemanticServiceProxy semanticServiceProxy;
    @Autowired
    DocumentServiceProxy documentServiceProxy;
    @Autowired
    ObjectMapper objectMapper;
    public ArrayList<String> fetchTermsFromSemanticService(){
        return semanticServiceProxy.getTerms();
    }
    public ArrayList<String> fetchConceptsFromSemanticService(){
        return semanticServiceProxy.getConcepts();
    }
    public Document gettingDocumentById(UUID docId)
    {
//        System.out.println(documentServiceProxy.getById(docId).getBody());
//         ObjectMapper mapper = new ObjectMapper();
//        System.out.println(documentServiceProxy.getById(docId));
        ResponseModel responseModel =  objectMapper.convertValue(documentServiceProxy.getById(docId).getBody(), ResponseModel.class);
//        System.out.println("");
        Collection<Document> documentList= responseModel.getResultList();
        System.out.println("printing the document"+documentList);
        ArrayList<Document> arrayList=new ArrayList<>(documentList);
        Document document=arrayList.get(0);
//        System.out.println(document.getAddedOn().getClass().getName());
//        System.out.println(document);
        return document;

    }

}
