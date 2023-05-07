package com.com3014.chatservice.model;

import java.time.Instant;
import java.util.UUID;

public class Message {

    private UUID user;
    private Instant timestamp;
    private String content;

    public Message(UUID user, Instant timestamp, String content) {
        this.user = user;
        this.timestamp = timestamp;
        this.content = content;
    }

    public UUID getUser() {
        return user;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String getContent() {
        return content;
    }

    public String getType() {
        return "MESSAGE";
    }

    public void setUser(UUID user) {
        this.user = user;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
