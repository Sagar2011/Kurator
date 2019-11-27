package com.kurator.kuratorUserService.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

@Component
public class ResultModel {

    private HashSet<String> topicToDocument;
    private ArrayList<HashMap<?, ?>> confidencerating;
    String documentId;
    String title;
    String description;
    String url;
    String addedBy;
    String addedOn;

    public ResultModel(String documentId, String title, String description, String url, String addedBy, String addedOn) {
        this.documentId = documentId;
        this.title = title;
        this.description = description;
        this.url = url;
        this.addedBy = addedBy;
        this.addedOn = addedOn;
    }

    public ResultModel() {
    }

    public HashSet<String> getTopicToDocument() {
        return topicToDocument;
    }

    public void setTopicToDocument(HashSet<String> topicToDocument) {
        this.topicToDocument = topicToDocument;
    }

    public ArrayList<HashMap<?, ?>> getConfidencerating() {
        return confidencerating;
    }

    public void setConfidencerating(ArrayList<HashMap<?, ?>> confidencerating) {
        this.confidencerating = confidencerating;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(String addedOn) {
        this.addedOn = addedOn;
    }
}
