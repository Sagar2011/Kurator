package com.kurator.kuratorDocumentService.library.model;

public class Status {
	
	public enum Validity{
		Valid,Invalid
	}//whether the document is a valid or invalid
	private Validity validity;//validity of the document
	
	public Status() {}
	
	public Status(Validity validity, Reason reason) {
		super();
		this.validity = validity;
		this.reason = reason;
	}
	public enum Reason{
		NotDuplicate,Duplicate,NotFound,Blacklisted,Blocked,Timedout,AuthenticationRequired,GDPRPermissionRequired,JunkDocument,Abusive
	}//why the document is a valid or invalid
	private Reason reason;//why the document is valid or invalid
	public Validity getValidity() {
		return validity;
	}
	public void setValidity(Validity validity) {
		this.validity = validity;
	}
	public Reason getReason() {
		return reason;
	}
	public void setReason(Reason reason) {
		this.reason = reason;
	}

}
