package com.ar.team.company.app.whatsdelete.model;

import android.graphics.drawable.Drawable;

import java.util.List;

@SuppressWarnings("unused")
public class Chat {

    // Fields:
    private final String sender, receiver;
    private final String messageDate;
    private final Drawable senderPhoto;
    private final List<Messages> messages;

    // Constructor:
    public Chat(String sender, String receiver, String messageDate, Drawable senderPhoto, List<Messages> messages) {
        this.sender = sender;
        this.receiver = receiver;
        this.messageDate = messageDate;
        this.senderPhoto = senderPhoto;
        this.messages = messages;
    }

    // InnerClasses:
    public static class Messages{

        // Fields:
        private final String message;

        // Constructor:
        public Messages(String message) {
            this.message = message;
        }

        // Getters:
        public String getMessage() {
            return message;
        }
    }

    // Getters:
    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public String getMessageDate() {
        return messageDate;
    }

    public Drawable getSenderPhoto() {
        return senderPhoto;
    }

    public List<Messages> getMessages() {
        return messages;
    }
}
