package com.kurator.library.model;

import java.time.LocalDateTime;

public class Flag {

    private String flaggedBy;//email id of user who flagged the document
    private LocalDateTime flaggedOn;//when the document was flagged
    private String reasonForFlagging;//reason provided by user why they flagged the document

    public Flag()
    {
        super();
    }

    public Flag(String flaggedBy, LocalDateTime flaggedOn, String reasonForFlagging) {
        super();
        this.flaggedBy = flaggedBy;
        this.flaggedOn = flaggedOn;
        this.reasonForFlagging = reasonForFlagging;
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

    public String getReasonForFlagging() {
        return reasonForFlagging;
    }

    public void setReasonForFlagging(String reasonForFlagging) {
        this.reasonForFlagging = reasonForFlagging;
    }

}
