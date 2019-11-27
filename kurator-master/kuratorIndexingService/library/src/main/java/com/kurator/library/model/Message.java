package com.kurator.library.model;

import java.util.UUID;

public class Message {
    private UUID documentId;
    private String url;
    public Message() {
        super();
    }
    public Message(UUID documentId, String url) {
        super();
        this.documentId = documentId;
        this.url = url;
    }
    public UUID getDocumentId() {
        return documentId;
    }
    public void setDocumentId(UUID documentId) {
        this.documentId = documentId;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
}

