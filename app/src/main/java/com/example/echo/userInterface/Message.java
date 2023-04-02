package com.example.echo.userInterface;

public class Message {
    private String message;
    private String senderId;

    public Message(String message, String senderId) {
        this.message = message;
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage() {
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId() {
        this.senderId = senderId;
    }

}