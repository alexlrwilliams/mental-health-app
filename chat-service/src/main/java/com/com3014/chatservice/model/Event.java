package com.com3014.chatservice.model;

import java.util.UUID;

public record Event(UUID user, String content){}
