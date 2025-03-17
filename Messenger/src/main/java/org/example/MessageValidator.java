package com.example.messaging.utils;

public class MessageValidator {
    private static final MessageValidator instance = new MessageValidator();

    private MessageValidator() {}

    public static MessageValidator getInstance() {
        return instance;
    }

    public boolean isValid(String message) {
        return message != null && !message.contains("\n");
    }
}
