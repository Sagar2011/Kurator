package com.kurator.kuratorUserService.model;

import java.time.LocalDateTime;
import java.util.UUID;



public class MyCurations {
	
	private UUID documentId;
	 
	private LocalDateTime curatedOn;
	
	public enum Visibility{
		PRIVATE, PUBLIC
	}
	
	private Visibility visibility;

	public UUID getDocumentId() {
		return documentId;
	}

	public void setDocumentId(UUID documentId) {
		this.documentId = documentId;
	}

	public LocalDateTime getCuratedOn() {
		return curatedOn;
	}

	public void setCuratedOn(LocalDateTime curatedOn) {
		this.curatedOn = curatedOn;
	}

	public Visibility getVisibility() {
		return visibility;
	}

	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
	}

	@Override
	public String toString() {
		return "MyCurations [documentId=" + documentId + ", curatedOn=" + curatedOn + ", visibility=" + visibility
				+ "]";
	};
	
	

}
