package com.kurator.semanticService.repository;

import java.util.ArrayList;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import com.kurator.semanticService.model.Concept;

@Repository
public interface ConceptRepository extends Neo4jRepository<Concept, Long> {
    @Query("match (n:concept) return n.name")
    ArrayList<String> getAllConcepts();

    @Query("merge(n:concept{name:{concept},isReviewed:{isReviewed}})")
    void createConcept(String concept,boolean isReviewed);

    @Query("merge(n:concept{name:{concepts}})")
    void createIndexedConcept(String concepts);

    @Query("Match(c:concept{name:{concept}}) detach delete c")
    void deleteImproperConcept(String concept);

    @Query("Match(c:concept) where c.isReviewed=true return c.name")
    ArrayList<String> getReviewedConcepts();

    @Query("match (n:concept) where n.name={concept} detach delete n")
    void deleteConcepts(String concept);
}
