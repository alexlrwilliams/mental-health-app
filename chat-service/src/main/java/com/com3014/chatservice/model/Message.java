package com.com3014.chatservice.model;

import java.util.UUID;

public record Message (UUID user, String content){}
