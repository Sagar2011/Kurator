package com.kurator.kuratorDocumentService.library.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

@org.springframework.data.mongodb.core.mapping.Document(collection = "document")
public class Document {

	@Id
	String _id;

	@Indexed(unique=true)
	private UUID documentId;//unique id for each document
	private String title;//title of the document from crawled data
	private String url;//user provided url or generated url
	private String description;//description of the document from crawled data
	private HashSet<String> userRating = new HashSet<String>();//average of the ratings given to the document by users
	private ArrayList<String> numberOfViews;//number of times the document has been viewed
	public enum Type{
		Website,Text,Image,Video
	}//whether the document is a website, a text document, an image or a video
	private Type documentType;//type of the document
	private ArrayList<String> favouriteOf;//email ids of the users who have favourited the document
	private String addedBy;//email id of the user who added the document
	private LocalDateTime addedOn;//when the document was added
	private String updatedBy;//email id of the user who updated the document
	private LocalDateTime updatedOn;//when the document was updated
	private HashMap<String,Integer> intent;//intent of the document discovered from extractor service
	private HashMap<String,Integer> concepts;//term of the document discovered from extractor service
	private ArrayList<String> conceptTags;//term tags for the document provided by the user
	public enum Visibility{
		PRIVATE, PUBLIC
	}//whether the document is private or public
	private Visibility visibility;
	private ArrayList<Flag> documentFlag;
	private Status status;//validity status
	private String documentStatus;//stage the document is in the system
	public enum IndexingStatus{
		CRAWLED,ANALYZED,INDEXED;
	}
	private IndexingStatus indexingStatus;

	public ArrayList<String> getConceptTags() {
		return conceptTags;
	}

	public void setConceptTags(ArrayList<String> conceptTags) {
		this.conceptTags = conceptTags;
	}

	public IndexingStatus getIndexingStatus() {
		return indexingStatus;
	}

	public void setIndexingStatus(IndexingStatus indexingStatus) {
		this.indexingStatus = indexingStatus;
	}

	public Document() {
		super();
	}

	public Document(UUID documentId, String title, String url, String description, HashSet<String> userRating, ArrayList<String> numberOfViews,
			Type documentType, ArrayList<String> favouriteOf, String addedBy, LocalDateTime addedOn, String updatedBy,
			LocalDateTime updatedOn, HashMap<String,Integer> intent, HashMap<String,Integer> concepts,
			ArrayList<String> conceptTags, Visibility visibility, ArrayList<Flag> documentFlag, Status status, String documentStatus) {
		super();
		this.documentId = documentId;
		this.title = title;
		this.url = url;
		this.description = description;
		this.userRating = userRating;
		this.numberOfViews = numberOfViews;
		this.documentType = documentType;
		this.favouriteOf = favouriteOf;
		this.addedBy = addedBy;
		this.addedOn = addedOn;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
		this.intent = intent;
		this.concepts = concepts;
		this.conceptTags = conceptTags;
		this.visibility = visibility;
		this.documentFlag = documentFlag;
		this.status = status;
		this.documentStatus=documentStatus;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public UUID getDocumentId() {
		return documentId;
	}

	public void setDocumentId(UUID documentId) {
		this.documentId = documentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public HashSet<String> getUserRating() {
		return userRating;
	}

	public void setUserRating(HashSet<String> userRating) {
		this.userRating = userRating;
	}

	public ArrayList<String> getNumberOfViews() {
		return numberOfViews;
	}

	public void setNumberOfViews(ArrayList<String> numberOfViews) {
		this.numberOfViews = numberOfViews;
	}

	public Type getDocumentType() {
		return documentType;
	}

	public void setDocumentType(Type documentType) {
		this.documentType = documentType;
	}

	public ArrayList<String> getFavouriteOf() {
		return favouriteOf;
	}

	public void setFavouriteOf(ArrayList<String> favouriteOf) {
		this.favouriteOf = favouriteOf;
	}

	public String getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}

	public LocalDateTime getAddedOn() {
		return addedOn;
	}

	public void setAddedOn(LocalDateTime addedOn) {
		this.addedOn = addedOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

	public HashMap<String,Integer> getIntent() {
		return intent;
	}

	public void setIntent(HashMap<String,Integer> intent) {
		this.intent = intent;
	}

	public Visibility getVisibility() {
		return visibility;
	}

	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
	}

	public ArrayList<Flag> getDocumentFlag() {
		return documentFlag;
	}

	public void setDocumentFlag(ArrayList<Flag> documentFlag) {
		this.documentFlag = documentFlag;
	}

	public Status getStatus() {
		return status;
	}

	public HashMap<String,Integer> getConcepts() {
		return concepts;
	}

	public void setConcepts(HashMap<String,Integer> concepts) {
		this.concepts = concepts;
	}
	
	

	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((documentId == null) ? 0 : documentId.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Document other = (Document) obj;
		if (documentId == null) {
			if (other.documentId != null)
				return false;
		} else if (!documentId.equals(other.documentId))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Document [documentId=" + documentId + ", title=" + title + ", url=" + url + ", description="
				+ description + ", userRating=" + userRating + ", numberOfViews=" + numberOfViews + ", documentType="
				+ documentType + ", favouriteOf=" + favouriteOf + ", addedBy=" + addedBy + ", addedOn=" + addedOn
				+ ", updatedBy=" + updatedBy + ", updatedOn=" + updatedOn + ", intent=" + intent
				+ ", concepts=" + concepts + ", termTags=" + conceptTags + ", visibility="
				+ visibility + ", documentFlag=" + documentFlag + ", status=" + status + ", documentStatus="
				+ documentStatus + "]";
	}



}

