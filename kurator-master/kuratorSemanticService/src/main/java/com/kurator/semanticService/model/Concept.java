package com.kurator.semanticService.model;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;

@NodeEntity("concept")
public class Concept {
    @Id
    @GeneratedValue
    Long id;
    String name;
    boolean isReviewed;
    //added by field
    @Relationship(type = "SUB_CONCEPT_OF")
    ArrayList<Concept> subConcepts = new ArrayList<>();

    public Concept() {

    }

    public Concept(Long id, String name, boolean isReviewed, ArrayList<Concept> subConcepts) {
        this.id = id;
        this.name = name;
        this.isReviewed = isReviewed;
        this.subConcepts = subConcepts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isReviewed() {
        return isReviewed;
    }

    public void setReviewed(boolean reviewed) {
        isReviewed = reviewed;
    }

    public ArrayList<Concept> getSubConcepts() {
        return subConcepts;
    }

    public void setSubConcepts(ArrayList<Concept> subConcepts) {
        this.subConcepts = subConcepts;
    }
}
