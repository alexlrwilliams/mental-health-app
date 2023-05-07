package com.com3014.chatservice.controller;

import com.com3014.chatservice.Events;
import com.com3014.chatservice.exception.ChatNotFoundException;
import com.com3014.chatservice.model.Chat;
import com.com3014.chatservice.model.Event;
import com.com3014.chatservice.model.Message;
import com.com3014.chatservice.service.ChatService;
import com.pusher.rest.Pusher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/chat")
public class ChatController {

    private final Pusher pusher;

    private final ChatService chatService;

    @Autowired
    public ChatController(Pusher pusher, ChatService chatService) {
        this.pusher = pusher;
        this.chatService = chatService;
    }

    @GetMapping("/{id}")
    public Chat getChatById(@PathVariable UUID id) {
        return chatService.getById(id).orElseThrow(() -> new ChatNotFoundException(
                "Chat %s not found".formatted(id)
        ));
    }

    @PostMapping("/{id}/join")
    public void joinChat(@PathVariable UUID id,
                         @RequestHeader(value = "email") String userEmail) {
        if (chatService.getById(id).isEmpty()) {
            chatService.create(id);
        }
        pusher.trigger(id.toString(), Events.JOIN.name(), Collections.singletonMap("content", "%s has joined the chat".formatted(userEmail)));
    }

    @PostMapping("/{id}/message")
    public void messageChat(@PathVariable UUID id, @RequestBody Event event) {
        var message = new Message(event.user(), Instant.now(), event.content());
        chatService.addMessage(id, message);
        pusher.trigger(id.toString(), Events.MESSAGE.name(), Map.of("user", event.user(), "content", event.content(), "timestamp", Instant.now().toString()));
    }
}
