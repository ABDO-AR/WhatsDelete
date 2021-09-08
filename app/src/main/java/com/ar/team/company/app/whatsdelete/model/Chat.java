package com.ar.team.company.app.whatsdelete.model;

import android.graphics.drawable.Drawable;

import java.util.List;

@SuppressWarnings("unused")
public class Chat {

    // Fields:
    private final String sender, receiver;
    private final String lastMessage, messageDate;
    private final List<String> messages;
    private final Drawable senderPhoto;

    // Constructor:
    public Chat(String sender, String receiver, String lastMessage, String messageDate, List<String> messages, Drawable senderPhoto) {
        this.sender = sender;
        this.receiver = receiver;
        this.lastMessage = lastMessage;
        this.messageDate = messageDate;
        this.messages = messages;
        this.senderPhoto = senderPhoto;
    }

    // Getters:
    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public String getMessageDate() {
        return messageDate;
    }

    public List<String> getMessages() {
        return messages;
    }

    public Drawable getSenderPhoto() {
        return senderPhoto;
    }
}
