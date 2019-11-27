package com.kurator.library.documentContentRepo;

import com.kurator.library.model.DocumentContent;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.UUID;

@Repository
public interface DocumentContentRepository extends MongoRepository<DocumentContent,String> {

    DocumentContent findDocumentContentByUrl(String url);
    //    ArrayList<String> findIntentListByDocumentId(UUID docId);
//    ArrayList<String> findIntentListByDo
    ArrayList<String> findTermsListByDocumentId(UUID docId);
    DocumentContent findDocumentByDocumentId(UUID docId);
    DocumentContent findByDocumentId(UUID documentId);
}
