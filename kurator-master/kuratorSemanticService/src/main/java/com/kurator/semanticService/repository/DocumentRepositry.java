package com.kurator.semanticService.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import com.kurator.semanticService.model.Document;

@Repository
public interface DocumentRepositry extends Neo4jRepository<Document, Long> {

    //    String vardocConceptRelation = SpringProperties.getProperty("docConceptRelation").toString();
    String vardocConceptRelation = "PRIMARY_TOPIC_OF";
    String intentDocRelation = "INTENT_OF";
    String sameDocRelation = "SIMILAR_TO";
    String relIntentTerm = "INDICATOR_OF";

    @Query("WITH {term} as termlist match(t:term)-[:INDICATOR_OF]-(int) where t.name IN termlist with t,collect(int) as intentlist unwind intentlist as intl match(intl)-[:INTENT_OF]-(docs) with collect(docs) as intDocs unwind intDocs as doc with {concept} as conceptlist match(doc)-[:PRIMARY_TOPIC_OF]-(concepts) where concepts.name IN conceptlist with collect(doc) as final return final")
    ArrayList<Document> getDocumentByTermAndConcept(ArrayList<String> term, ArrayList<String> concept);

    @Query("MATCH(ter:term{name:{term}})-[:" + sameDocRelation + "]-(t) with collect(t) AS t UNWIND t as termList  MATCH(terms)-[:" + relIntentTerm + "]-(i) where terms.name=termList.name with collect(i) as intlist UNWIND intlist as intl MATCH (intterm)-[:" + intentDocRelation + "]-(doc) where intterm.name=intl.name with collect(doc) as list UNWIND list as d match (docs)-[:" + vardocConceptRelation + "]-(t1:concept{name:{concept}}) where docs.title=d.title with collect(d) as final return final")
    ArrayList<Document> getDocumentBySimilarTermAndConcept(String term, String concept);

    @Query("WITH {conceptlists} as topiclist match(t:concept)-[:" + vardocConceptRelation + "]-(d) where t.name IN topiclist with t, collect(d) as doclist with collect(doclist) as alldoc WITH reduce(comdoc = head(alldoc), do in tail(alldoc) | apoc.coll.intersection(comdoc, do)) as comdoc RETURN comdoc")
    ArrayList<Document> getDocumentByIntentAndConcept(String intent, HashSet<String> conceptlists);


    @Query("match(d:document)-[re:" + intentDocRelation + "]-(int) WHERE d.documentId={id} with max(re.confidenceRating) as r match(doc:document)-[rel:" + intentDocRelation + "{confidenceRating:r}]-(intent) WHERE doc.documentId={id} return distinct intent.name as intent, rel.confidenceRating as rating limit 1")
    ArrayList<HashMap<?, ?>> getConfidenceRating(String id);

    @Query("match (docs:document)-[:" + vardocConceptRelation + "]-(con:concept) where con.name={concept} return docs")
    ArrayList<Document> getDocumentByConcept(String concept);

    @Query("match(doc:document) where doc.documentId={documentId} return doc")
    Document getDocumentById(String documentId);

    @Query("match(doc:document)-[r:" + vardocConceptRelation + "]-(concept) where doc.documentId={documentId} with concept, collect(r.confidencerating) as list return concept.name order by list DESC limit 4")
    HashSet<String> getConceptWithDocument(String documentId);

    @Query("merge(d:document{documentId:{documentId},title:{title},description:{description},url:{url},addedBy:{addedBy},addedOn:{addedOnDate},visibility:{visibility}})")
    void createNewDocument(String documentId, String title, String description, String url, String addedBy, String addedOnDate, Document.Visibility visibility);

   @Query("MATCH(d:document),(i:intent) where d.documentId={documentId} and i.name={name} merge (d)-[r:" + intentDocRelation + "]->(i) set r.confidenceRating={confidencerating}")
    void updateConfidenceRating(String documentId,String name,float confidencerating);

   @Query("MATCH(d:document),(c:concept) where d.documentId={documentId} and c.name={name} merge (d)-[r:" + vardocConceptRelation + "]->(c) set r.confidencerating={confidencerating}")
   void mappingConcept(String documentId,String name,int confidencerating);

}

