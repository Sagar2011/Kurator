package com.kurator.www.controller;

import com.kurator.library.model.DocumentContent;
import com.kurator.library.service.DocContentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

@RestController
public class DocumentContentController {
    @Autowired
    private DocContentServiceImpl docContentServiceImpl;

    @GetMapping("/hello")
    public String testing()
    {
        return "hello";
    }
    @GetMapping("/indexingStatus")
    public String getIndexingStatus(@RequestParam String documentId){
        UUID id= UUID.fromString(documentId);
        DocumentContent documentContent= docContentServiceImpl.fetchContent(id);
        String status= documentContent.getIndexingStatus().name();
        return status;
    }

    @GetMapping("/indexedOn")
    public ArrayList<String> getIndexedOn(@RequestParam String documentId){
        UUID id= UUID.fromString(documentId);
        DocumentContent documentContent=docContentServiceImpl.fetchContent(id);
        HashMap<String,Integer> listOfIntentAndConfidence=documentContent.getIntentList();
        String intent="introduction";
        int max=0;
        Set<String> listOfIntent = listOfIntentAndConfidence.keySet();
        for(String demoIntent :listOfIntent)
        {
            int value=listOfIntentAndConfidence.get(demoIntent);
            if(value>max)
            {
                max=value;
                intent=demoIntent;
            }
        }
        ArrayList<String> listOfAnswer=new ArrayList<>();
        listOfAnswer.add(documentContent.getIndexedOn().toString());
        listOfAnswer.add(intent);
        listOfAnswer.add(Integer.toString(max));
        return listOfAnswer;
    }
}
