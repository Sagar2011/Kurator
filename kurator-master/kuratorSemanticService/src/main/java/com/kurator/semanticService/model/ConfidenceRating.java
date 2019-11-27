package com.kurator.semanticService.model;


import java.util.HashMap;
import java.util.UUID;

public class ConfidenceRating {

    UUID documentId;
    HashMap<String, Float> confidenceRating = new HashMap<>();

    HashMap<String,Integer> conceptsList = new HashMap<>();

    public ConfidenceRating() {
    }

    public ConfidenceRating(UUID documentId, HashMap<String, Float> confidenceRating, HashMap<String, Integer> conceptsList) {
        this.documentId = documentId;
        this.confidenceRating = confidenceRating;
        this.conceptsList = conceptsList;
    }

    public UUID getDocumentId() {
        return documentId;
    }

    public void setDocumentId(UUID documentId) {
        this.documentId = documentId;
    }

    public HashMap<String, Float> getConfidenceRating() {
        return confidenceRating;
    }

    public void setConfidenceRating(HashMap<String, Float> confidenceRating) {
        this.confidenceRating = confidenceRating;
    }

    public HashMap<String, Integer> getConceptsList() {
        return conceptsList;
    }

    public void setConceptsList(HashMap<String, Integer> conceptsList) {
        this.conceptsList = conceptsList;
    }

    @Override
    public String toString() {
        return "ConfidenceRating{" +
                "documentId=" + documentId +
                ", confidenceRating=" + confidenceRating +
                ", conceptsList=" + conceptsList +
                '}';
    }
}
