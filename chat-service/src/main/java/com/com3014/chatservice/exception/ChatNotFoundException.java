package com.com3014.chatservice.exception;

public class ChatNotFoundException extends RuntimeException{

        public ChatNotFoundException(String errorMessage) {
            super(errorMessage);
        }
   }
