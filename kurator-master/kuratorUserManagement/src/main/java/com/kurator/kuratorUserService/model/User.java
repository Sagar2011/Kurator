package com.kurator.kuratorUserService.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {
    @Id
    private String userId;

    @Indexed(unique = true)
    private String userName;

    private String email;

    private String firstName;
    private String lastName;
    private String aboutUser;
    private String name;
    private HashSet<UUID> recentViews;
    private HashSet<UUID> favourites;
    private String avatarURL;
    HashSet<String> topicsOfInterest = new HashSet<>();
    ArrayList<MyCurations> myCurations;
    HashSet<String> followingTopics = new HashSet<>();
    HashSet<String> followingPeople = new HashSet<>();
    HashSet<String> follower = new HashSet<>();
    boolean isTerms;


    public enum Role{
        USER,ADMIN
    }

    private Role userRole;

    public ArrayList<String> getAvailable_topics() {
        return available_topics;
    }

    public void setAvailable_topics(ArrayList<String> available_topics) {
        this.available_topics = available_topics;
    }

    private LocalDateTime createdOn;
    private String createdBy;
    private LocalDateTime updatedOn;
    private String updatedBy;
    private ArrayList<String> available_topics;
    private boolean status;
    private ArrayList<Playlist> playlists = new ArrayList<>();


    public User() {
    }

    public User(String userName, String email, String firstName, String lastName, String avatarURL, LocalDateTime createdOn, String createdBy, LocalDateTime updatedOn, String updatedBy) {
        this.userName = userName;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatarURL = avatarURL;
        this.createdOn = createdOn;
        this.createdBy = createdBy;
        this.updatedOn = updatedOn;
        this.updatedBy = updatedBy;
    }

    public User(String userId, String userName, String email, String firstName, String lastName, String aboutUser, String name, HashSet<UUID> recentViews, HashSet<UUID> favourites, String avatarURL, HashSet<String> topicsOfInterest, ArrayList<MyCurations> myCurations, HashSet<String> followingTopics, HashSet<String> followingPeople, Role userRole, LocalDateTime createdOn, String createdBy, LocalDateTime updatedOn, String updatedBy, ArrayList<String> available_topics, boolean status, HashSet<String> follower) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.aboutUser = aboutUser;
        this.name = name;
        this.recentViews = recentViews;
        this.favourites = favourites;
        this.avatarURL = avatarURL;
        this.topicsOfInterest = topicsOfInterest;
        this.myCurations = myCurations;
        this.followingTopics = followingTopics;
        this.followingPeople = followingPeople;
        this.userRole = userRole;
        this.createdOn = createdOn;
        this.createdBy = createdBy;
        this.updatedOn = updatedOn;
        this.updatedBy = updatedBy;
        this.available_topics = available_topics;
        this.status = status;
        this.follower = follower;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public HashSet<UUID> getRecentViews() {
        return recentViews;
    }

    public void setRecentViews(HashSet<UUID> recentViews) {
        this.recentViews = recentViews;
    }

    public HashSet<UUID> getFavourites() {
        return favourites;
    }

    public void setFavourites(HashSet<UUID> favourites) {
        this.favourites = favourites;
    }

    public String getAvatar() {
        return avatarURL;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(LocalDateTime updatedOn) {
        this.updatedOn = updatedOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashSet<String> getTopicsOfInterest() {
        return topicsOfInterest;
    }

    public void setTopicsOfInterest(HashSet<String> topicsOfInterest) {
        this.topicsOfInterest = topicsOfInterest;
    }

    public ArrayList<MyCurations> getMyCurations() {
        return myCurations;
    }

    public void setMyCurations(ArrayList<MyCurations> myCurations) {
        this.myCurations = myCurations;
    }

    public String getAboutUser() {
        return aboutUser;
    }

    public void setAboutUser(String aboutUser) {
        this.aboutUser = aboutUser;
    }

    public Role getUserRole() {
        return userRole;
    }

    public void setUserRole(Role userRole) {
        this.userRole = userRole;
    }

    public HashSet<String> getFollowingTopics() {
        return followingTopics;
    }

    public void setFollowingTopics(HashSet<String> followingTopics) {
        this.followingTopics = followingTopics;
    }

    public HashSet<String> getFollowingPeople() {
        return followingPeople;
    }

    public void setFollowingPeople(HashSet<String> followingPeople) {
        this.followingPeople = followingPeople;
    }
    public ArrayList<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(ArrayList<Playlist> playlists) {
        this.playlists = playlists;
    }

    public HashSet<String> getFollower() {
        return follower;
    }

    public void setFollower(HashSet<String> follower) {
        this.follower = follower;
    }

    public boolean isTerms() {
        return isTerms;
    }

    public void setTerms(boolean terms) {
        isTerms = terms;
    }

    public User(String userId, String userName, String email, String firstName, String lastName, String aboutUser, String name, HashSet<UUID> recentViews, HashSet<UUID> favourites, String avatarURL, HashSet<String> topicsOfInterest, ArrayList<MyCurations> myCurations, HashSet<String> followingTopics, HashSet<String> followingPeople, HashSet<String> follower, boolean isTerms, Role userRole, LocalDateTime createdOn, String createdBy, LocalDateTime updatedOn, String updatedBy, ArrayList<String> available_topics, boolean status, ArrayList<Playlist> playlists) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.aboutUser = aboutUser;
        this.name = name;
        this.recentViews = recentViews;
        this.favourites = favourites;
        this.avatarURL = avatarURL;
        this.topicsOfInterest = topicsOfInterest;
        this.myCurations = myCurations;
        this.followingTopics = followingTopics;
        this.followingPeople = followingPeople;
        this.follower = follower;
        this.isTerms = isTerms;
        this.userRole = userRole;
        this.createdOn = createdOn;
        this.createdBy = createdBy;
        this.updatedOn = updatedOn;
        this.updatedBy = updatedBy;
        this.available_topics = available_topics;
        this.status = status;
        this.playlists = playlists;
    }

    public User(String userName, String email, String firstName, String lastName) {
        // TODO Auto-generated constructor stub

        this.userName = userName;
        this.email = email;

        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", aboutUser='" + aboutUser + '\'' +
                ", name='" + name + '\'' +
                ", recentViews=" + recentViews +
                ", favourites=" + favourites +
                ", avatarURL='" + avatarURL + '\'' +
                ", topicsOfInterest=" + topicsOfInterest +
                ", myCurations=" + myCurations +
                ", followingTopics=" + followingTopics +
                ", followingPeople=" + followingPeople +
                ", follower=" + follower +
                ", isTerms=" + isTerms +
                ", userRole=" + userRole +
                ", createdOn=" + createdOn +
                ", createdBy='" + createdBy + '\'' +
                ", updatedOn=" + updatedOn +
                ", updatedBy='" + updatedBy + '\'' +
                ", available_topics=" + available_topics +
                ", status=" + status +
                ", playlists=" + playlists +
                '}';
    }
}
