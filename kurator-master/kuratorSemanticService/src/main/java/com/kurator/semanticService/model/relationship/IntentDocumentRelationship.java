package com.kurator.semanticService.model.relationship;

import com.kurator.semanticService.model.Document;
import com.kurator.semanticService.model.Intent;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.Property;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type="INTENT_OF")
public class IntentDocumentRelationship {

    @Property
    private float confidenceRating;

    @StartNode
    Document document;

    @EndNode
    Intent intent;

    public IntentDocumentRelationship() {
    }

    public float getConfidenceRating() {
        return confidenceRating;
    }

    public void setConfidenceRating(float confidenceRating) {
        this.confidenceRating = confidenceRating;
    }


}
