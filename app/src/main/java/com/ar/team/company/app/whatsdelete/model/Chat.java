package com.ar.team.company.app.whatsdelete.model;

import android.app.Notification;
import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("unused")
public class Chat {

    // Fields:
    private final String sender, receiver;
    private final String messageDate;
    private final Drawable senderPhoto;
    private final List<Notification.Action> actions;
    private final List<Messages> messages;

    // Constructor:
    public Chat(String sender, String receiver, String messageDate, Drawable senderPhoto, List<Messages> messages, List<Notification.Action> actions) {
        this.sender = sender;
        this.receiver = receiver;
        this.messageDate = messageDate;
        this.senderPhoto = senderPhoto;
        this.messages = messages;
        this.actions = actions;
    }

    // InnerClasses:
    public static class Messages {

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
    public List<Notification.Action> getActions() {
        return actions;
    }

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
