package com.kurator.semanticService.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Document(collection = "userStatus")
public class UserStatus {
    @Id
    String id;
    @Indexed(expireAfterSeconds = 604800)  // for the 7 days work
    private Date createdAt;
    private String userEmail;
    private int abusiveHitCounter;

    public UserStatus() {
    }

    public UserStatus(Date createdAt, String userEmail, int abusiveHitCounter) {
        this.createdAt = createdAt;
        this.userEmail = userEmail;
        this.abusiveHitCounter = abusiveHitCounter;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getAbusiveHitCounter() {
        return abusiveHitCounter;
    }

    public void setAbusiveHitCounter(int abusiveHitCounter) {
        this.abusiveHitCounter = abusiveHitCounter;
    }
}
