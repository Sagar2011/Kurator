package com.kurator.semanticService.repository;

import java.util.ArrayList;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import com.kurator.semanticService.model.Intent;

@Repository
public interface IntentRepository extends Neo4jRepository<Intent, Long> {

    String relIntentTerm = "INDICATOR_OF";
    String relIntentCounterTerm = "COUNTER_INDICATOR_OF";

    @Query("match (n:intent) return n.name")
    ArrayList<String> getAllIntents();

    //  return intent from terms
    @Query("MATCH(ter:term{name:{terms}})-[:INDICATOR_OF]-(intent) return intent.name")
    ArrayList<String> getIntentFromTerm(String terms);

    @Query("MATCH(t:term)-[:INDICATOR_OF]-(i:intent{name:{intents}}) return t.name")
    ArrayList<String> getTermFromIntent(String intents);

    @Query("MATCH(t:term)-[:COUNTER_INDICATOR_OF]-(i:intent{name:{intents}}) return t.name")
    ArrayList<String> getCounterTermFromIntent(String intents);
//    return terms from intent

    @Query("merge(n:intent{name:{intent}}) merge(p:term{name:{term}}) merge (p)-[r:" + relIntentTerm + "]->(n)")
    void mapIntentWithTerms(String intent, String term);


    @Query("merge(n:intent{name:{intent}}) merge(p:term{name:{term}}) merge(p)-[r:" + relIntentCounterTerm + "]->(n)")
    void mapIntentWithCounterTerms(String intent, String term);
}
