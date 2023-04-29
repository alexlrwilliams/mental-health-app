package com.com3014.chatservice.repository;

import com.com3014.chatservice.model.Chat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChatRepository extends MongoRepository<Chat, UUID> {
}
