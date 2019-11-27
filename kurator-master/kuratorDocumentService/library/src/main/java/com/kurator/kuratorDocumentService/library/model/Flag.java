package com.kurator.kuratorDocumentService.library.model;

import java.time.LocalDateTime;

public class Flag {
	
	private String flaggedBy;//email id of user who flagged the document
	private LocalDateTime flaggedOn;//when the document was flagged
	private String comment;//user comment
	private String reason;//why the user flagged the document
	public Flag()
	{
		super();
	}
	
	public Flag(String flaggedBy, LocalDateTime flaggedOn, String comment, String reason) {
		super();
		this.flaggedBy = flaggedBy;
		this.flaggedOn = flaggedOn;
		this.comment = comment;
		this.reason = reason;
	}

	public String getFlaggedBy() {
		return flaggedBy;
	}

	public void setFlaggedBy(String flaggedBy) {
		this.flaggedBy = flaggedBy;
	}

	public LocalDateTime getFlaggedOn() {
		return flaggedOn;
	}

	public void setFlaggedOn(LocalDateTime flaggedOn) {
		this.flaggedOn = flaggedOn;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) { this.reason = reason; }
	
}
