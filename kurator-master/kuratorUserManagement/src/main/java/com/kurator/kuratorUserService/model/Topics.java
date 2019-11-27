package com.kurator.kuratorUserService.model;

import java.util.UUID;


public class Topics {

    private UUID topicId = UUID.randomUUID();
    private String topicName;

    public UUID getTopicId() {
        return topicId;
    }

    public void setTopicId(UUID topicId) {
        this.topicId = topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    @Override
    public String toString() {
        return "Topics [topicId=" + topicId + ", topicName=" + topicName + "]";
    }


}
