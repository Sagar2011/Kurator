package com.kurator.kuratorUserService.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.UUID;

public class Playlist {

    private String playlistName;
    private String description;
    private LinkedHashSet<UUID> documentID = new LinkedHashSet<>();
    private LocalDateTime createdOn;

    public Playlist() {
    }

    public Playlist(String playlistName, String description, LinkedHashSet<UUID> documentID, LocalDateTime createdOn) {
        this.playlistName = playlistName;
        this.description = description;
        this.documentID = documentID;
        this.createdOn = createdOn;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public LinkedHashSet<UUID> getDocumentID() {
        return documentID;
    }

    public void setDocumentID(LinkedHashSet<UUID> documentID) {
        this.documentID = documentID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "playlistName='" + playlistName + '\'' +
                ", description='" + description + '\'' +
                ", documentID=" + documentID +
                ", createdOn=" + createdOn +
                '}';
    }

}
