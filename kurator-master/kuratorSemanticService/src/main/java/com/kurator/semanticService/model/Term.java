package com.kurator.semanticService.model;

import java.util.ArrayList;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.beans.factory.annotation.Value;

@NodeEntity("term")
public class Term {
    @Id
    @GeneratedValue
    Long id;
    String name;

    @Value("${termIntentRelation}")
    private String termIntentRelation;

    @Value("${termIntentCountRel}")
    private String termIntentCountRel;

    @Relationship(type = "{termIntentRelation}")
    public ArrayList<Intent> intents;

    @Relationship(type = "{termIntentCountRel}")
    public ArrayList<Intent> counterIntents = new ArrayList<>();

    public Term() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Term(String name) {
        this.name = name;
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

    public ArrayList<Intent> getIntents() {
        return intents;
    }

    public void setIntents(ArrayList<Intent> intents) {
        this.intents = intents;
    }

    public ArrayList<Intent> getCounterIntents() {
        return counterIntents;
    }

    public void setCounterIntents(ArrayList<Intent> counterIntents) {
        this.counterIntents = counterIntents;
    }
}
