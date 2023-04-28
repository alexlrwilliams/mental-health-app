package com.com3014.chatservice.controller;

import com.com3014.chatservice.Events;
import com.com3014.chatservice.model.Message;
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

    @Autowired
    public ChatController(Pusher pusher) {
        this.pusher = pusher;
    }

    @PostMapping("/{id}/join")
    public void joinChat(@PathVariable UUID id,
                         @RequestHeader(value = "email") String userEmail) {
        pusher.trigger(id.toString(), Events.JOIN.name(), Collections.singletonMap("content", "%s has joined the chat".formatted(userEmail)));
    }

    @PostMapping("/{id}/message")
    public void messageChat(@PathVariable UUID id, @RequestBody Message message) {
        pusher.trigger(id.toString(), Events.MESSAGE.name(), Map.of("user", message.user(), "content", message.content(), "timestamp", Instant.now().toString()));
    }
}
