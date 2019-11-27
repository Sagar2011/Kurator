package com.kurator.library.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class SemanticDocument {
    private UUID documentId;//unique id for each document
    private String title;//title of the document from crawled data
    private String url;//user provided url or generated url
    private String description;//description of the document from crawled data
    private String addedBy;//email id of the user who added the document
    private LocalDateTime addedOn;//when the document was added
    public enum Visibility{
        PRIVATE, PUBLIC
    }//whether the document is private or public
    private Document.Visibility visibility;

    public SemanticDocument(UUID documentId, String title, String url, String description, String addedBy, LocalDateTime addedOn, Document.Visibility visibility) {
        this.documentId = documentId;
        this.title = title;
        this.url = url;
        this.description = description;
        this.addedBy = addedBy;
        this.addedOn = addedOn;
        this.visibility = visibility;
    }

    public UUID getDocumentId() {
        return documentId;
    }

    public void setDocumentId(UUID documentId) {
        this.documentId = documentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public LocalDateTime getAddedOn() {
        return addedOn;
    }

    public void setAddedOn(LocalDateTime addedOn) {
        this.addedOn = addedOn;
    }

    public Document.Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Document.Visibility visibility) {
        this.visibility = visibility;
    }
}
