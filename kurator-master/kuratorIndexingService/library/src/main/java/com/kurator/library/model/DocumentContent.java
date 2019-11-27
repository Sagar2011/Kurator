package com.kurator.library.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@Document(collection = "documentContent")
public class DocumentContent {
    //@Indexed(unique = true)
    @Id
    private UUID documentId;
    private String url;
    private String title;
    private String description;
    private ArrayList<String> headList= new ArrayList<String>();
    //private ArrayList<String> metaList= new ArrayList<String>();
    private ArrayList<String> h1TagList= new ArrayList<String>();
    private ArrayList<String> h2TagList= new ArrayList<String>();
    private ArrayList<String> h3TagList= new ArrayList<String>();
    private ArrayList<String> h4TagList= new ArrayList<String>();
    private ArrayList<String> h5TagList= new ArrayList<String>();
    private ArrayList<String> h6TagList= new ArrayList<String>();
    private ArrayList<String> contentList= new ArrayList<String>();
    private ArrayList<String> linksList= new ArrayList<String>();
    //	private ArrayList<String> termsList= new ArrayList<String>();
    private HashMap<String,Integer> termsWithWeightage= new HashMap<>();

    private ArrayList<String> conceptList= new ArrayList<String>();

    private HashMap<String, Integer> conceptsFromDocument= new HashMap<>();
    //	private Set<String> conceptSet = new LinkedHashSet<>();
    private HashMap<String,Integer> intentList= new HashMap<>();
    public enum IndexingStatus{
        CRAWLED,ANALYZED,INDEXED;
    }

    private IndexingStatus indexingStatus;
    private LocalDateTime indexedOn;


    public DocumentContent() {
        super();
    }
    public DocumentContent(UUID documentId,String url,String title)
    {
        super();
        this.setDocumentId(documentId);
        this.setUrl(url);
        this.setTitle(title);
    }
    public DocumentContent(UUID documentId,String url,ArrayList<String> headList,String title, ArrayList<String> h1TagList,
                           ArrayList<String> h2TagList,ArrayList<String> h3TagList ,ArrayList<String> h4TagList,ArrayList<String> h5TagList,
                           ArrayList<String> h6TagList, ArrayList<String> contentList,ArrayList<String> linksList, HashMap<String,Integer>termsWithWeightage,
                           HashMap<String,Integer> intentList,LocalDateTime indexedOn) {
        super();
        this.setIndexedOn(indexedOn);
        this.setDocumentId(documentId);
        this.setUrl(url);
        this.setTitle(title);
        this.setHeadList(headList);
        this.setH1TagList(h1TagList);
        this.setH2TagList(h2TagList);
        this.setH3TagList(h3TagList);
        this.setH4TagList(h4TagList);
        this.setH5TagList(h5TagList);
        this.setH6TagList(h6TagList);
        this.setContentList(contentList);
        this.setLinksList(linksList);
        this.setTermsWithWeightage(termsWithWeightage);
//		this.setConceptList(topicsList);
        this.setIntentList(intentList);
    }
    public LocalDateTime getIndexedOn() {
        return indexedOn;
    }

    public void setIndexedOn(LocalDateTime indexedOn) {
        this.indexedOn = indexedOn;
    }

    public UUID getDocumentId() {
        return documentId;
    }
    public void setDocumentId(UUID documentId) {
        this.documentId = documentId;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public ArrayList<String> getHeadList() {
        return headList;
    }
    public void setHeadList(ArrayList<String> headList) {
        this.headList = headList;
    }
    public ArrayList<String> getH1TagList() {
        return h1TagList;
    }
    public void setH1TagList(ArrayList<String> h1TagList) {
        this.h1TagList = h1TagList;
    }
    public ArrayList<String> getH2TagList() {
        return h2TagList;
    }
    public void setH2TagList(ArrayList<String> h2TagList) {
        this.h2TagList = h2TagList;
    }
    public ArrayList<String> getH3TagList() {
        return h3TagList;
    }
    public void setH3TagList(ArrayList<String> h3TagList) {
        this.h3TagList = h3TagList;
    }
    public ArrayList<String> getH4TagList() {
        return h4TagList;
    }
    public void setH4TagList(ArrayList<String> h4TagList) {
        this.h4TagList = h4TagList;
    }
    public ArrayList<String> getH5TagList() {
        return h5TagList;
    }
    public void setH5TagList(ArrayList<String> h5TagList) {
        this.h5TagList = h5TagList;
    }
    public ArrayList<String> getH6TagList() {
        return h6TagList;
    }
    public void setH6TagList(ArrayList<String> h6TagList) {
        this.h6TagList = h6TagList;
    }
    public ArrayList<String> getContentList() {
        return contentList;
    }
    public void setContentList(ArrayList<String> contentList) {
        this.contentList = contentList;
    }
    public ArrayList<String> getLinksList() {
        return linksList;
    }
    public void setLinksList(ArrayList<String> linksList) {
        this.linksList = linksList;
    }
    //	public ArrayList<String> getTermsList() {
//		return termsList;
//	}
//	public void setTermsList(ArrayList<String> termsList) {
//		this.termsList = termsList;
//	}
    public HashMap<String, Integer> getTermsWithWeightage() {
        return termsWithWeightage;
    }

    public void setTermsWithWeightage(HashMap<String, Integer> termsWithWeightage) {
        this.termsWithWeightage = termsWithWeightage;
    }
    public ArrayList<String> getConceptList() {
        return conceptList;
    }
    public void setConceptList(ArrayList<String> topicsList) {
        this.conceptList = topicsList;
    }
    public HashMap<String, Integer> getIntentList() {
        return intentList;
    }
    public void setIntentList(HashMap<String, Integer> intentList) {
        this.intentList = intentList;
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

    public HashMap<String, Integer> getConceptsFromDocument() {
        return conceptsFromDocument;
    }

    public void setConceptsFromDocument(HashMap<String, Integer> conceptsFromDocument) {
        this.conceptsFromDocument = conceptsFromDocument;
    }
//	public Set<String> getConceptSet() {
//		return conceptSet;
//	}
//
//	public void setConceptSet(Set<String> conceptSet) {
//		this.conceptSet = conceptSet;
//	}


    public IndexingStatus getIndexingStatus() {
        return indexingStatus;
    }

    public void setIndexingStatus(IndexingStatus indexingStatus) {
        this.indexingStatus = indexingStatus;
    }
}

