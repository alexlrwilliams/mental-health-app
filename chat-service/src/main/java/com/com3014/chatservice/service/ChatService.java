package com.com3014.chatservice.service;

import com.com3014.chatservice.exception.ChatNotFoundException;
import com.com3014.chatservice.model.Chat;
import com.com3014.chatservice.model.Message;
import com.com3014.chatservice.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChatService {
    private final ChatRepository chatRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    public Optional<Chat> getById(UUID chatId) {
        return chatRepository.findById(chatId);
    }

    public Chat create(UUID chatId) {
        var chat = new Chat(chatId, List.of());
        return chatRepository.save(chat);
    }

    public Chat addMessage(UUID chatId, Message message) {
        var chat = chatRepository.findById(chatId).orElseThrow(() -> new ChatNotFoundException(
                "Chat %s not found".formatted(chatId)
        ));
        chat.addMessage(message);
        return chatRepository.save(chat);
    }

}
