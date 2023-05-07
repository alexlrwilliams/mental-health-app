package com.com3014.chatservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document("chat")
public class Chat {
    @Id
    private UUID chatId;
    private List<Message> chatHistory;


    public Chat(UUID chatId, List<Message> chatHistory) {
        this.chatId = chatId;
        this.chatHistory = chatHistory;
    }

    public void addMessage(Message message) {
        chatHistory.add(message);
    }

    public UUID getChatId() {
        return chatId;
    }

    public List<Message> getChatHistory() {
        return chatHistory;
    }

    public void setChatId(UUID chatId) {
        this.chatId = chatId;
    }

    public void setChatHistory(List<Message> chatHistory) {
        this.chatHistory = chatHistory;
    }
}
