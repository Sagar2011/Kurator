package com.kurator.semanticService.model;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity("intent")
public class Intent {
    @Id
    @GeneratedValue
    Long id;
    String name;
    long intendId;

    public Intent() {
        super();
        // TODO Auto-generated constructor stub
    }

    public Intent(String name, long intendId) {
        this.name = name;
        this.intendId = intendId;
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

    public long getIntendId() {
        return intendId;
    }

    public void setIntendId(long intendId) {
        this.intendId = intendId;
    }
}
