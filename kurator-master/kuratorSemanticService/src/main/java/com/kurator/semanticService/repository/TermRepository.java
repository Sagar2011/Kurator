package com.kurator.semanticService.repository;

import java.util.ArrayList;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import com.kurator.semanticService.model.Term;

@Repository
public interface TermRepository extends Neo4jRepository<Term, Long> {
	@Query("match (n:term) return n.name")
	ArrayList<String> getTerms();

	@Query("Match(t:term{name:{term}}) detach delete t")
	void deleteImproperTerm(String term);

}
