package com.kurator.semanticService.model;


import java.util.ArrayList;

import com.kurator.semanticService.model.relationship.IntentDocumentRelationship;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.beans.factory.annotation.Value;

@NodeEntity("document")
public class Document {
    @Id
    Long id;
    @Property
    @Index(unique = true)
    String documentId;
    @Property
    String title;
    @Property
    String description;
    @Property
    String url;
    @Property
    String addedBy;

    String addedOn;

    public enum Visibility {
        PRIVATE, PUBLIC
    }

    @Property
    Visibility visibility;

    @Value("${docConceptRelation}")
    private static String docConceptRelation;

    @Value("${intentDocRelation}")
    private static String intentDocRelation;

    @Value("${sameDocRelation}")
    private static String sameDocRelation;

    @Relationship(type = "{docConceptRelation}")
    public ArrayList<Concept> concepts = new ArrayList<>();

    @Relationship(type = "{intentDocRelation}")
    public ArrayList<IntentDocumentRelationship> intents = new ArrayList<>();

    @Relationship(type = "{sameDocRelation}")
    public ArrayList<Document> similarDocuments = new ArrayList<>();

    public Document() {
        super();
    }

    public Document(String documentId, String title, String description, String url, String addedBy, String addedOn, Visibility visibility) {
        this.documentId = documentId;
        this.title = title;
        this.description = description;
        this.url = url;
        this.addedBy = addedBy;
        this.addedOn = addedOn;
        this.visibility = visibility;
    }

    public Document(String documentId, String title, String description, String url, String addedBy, String addedOn) {
        this.documentId = documentId;
        this.title = title;
        this.description = description;
        this.url = url;
        this.addedBy = addedBy;
        this.addedOn = addedOn;
    }


    public static String getDocConceptRelation() {
        return docConceptRelation;
    }

    public void setDocConceptRelation(String docConceptRelation) {
        this.docConceptRelation = docConceptRelation;
    }

    public String getIntentDocRelation() {
        return intentDocRelation;
    }

    public void setIntentDocRelation(String intentDocRelation) {
        this.intentDocRelation = intentDocRelation;
    }

    public static String getSameDocRelation() {
        return sameDocRelation;
    }

    public void setSameDocRelation(String sameDocRelation) {
        this.sameDocRelation = sameDocRelation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public ArrayList<IntentDocumentRelationship> getIntents() {
        return intents;
    }

    public void setIntents(ArrayList<IntentDocumentRelationship> intents) {
        this.intents = intents;
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

    public ArrayList<Concept> getConcepts() {
        return concepts;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public void setConcepts(ArrayList<Concept> concepts) {
        this.concepts = concepts;
    }

    public ArrayList<Document> getSimilarDocuments() {
        return similarDocuments;
    }

    public void setSimilarDocuments(ArrayList<Document> similarDocuments) {
        this.similarDocuments = similarDocuments;
    }

    @Override
    public String toString() {
        return "Document{" +
                "id=" + id +
                ", documentId=" + documentId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", addedBy='" + addedBy + '\'' +
                ", addedOn=" + addedOn + '\'' +
                ", visibility=" + visibility + '\'' +
                ", concepts=" + concepts +
                ", intents=" + intents +
                ", similarDocuments=" + similarDocuments +
                '}';
    }
}
