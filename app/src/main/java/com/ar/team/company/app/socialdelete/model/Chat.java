package com.ar.team.company.app.socialdelete.model;

import java.util.List;

@SuppressWarnings("unused")
public class Chat {

    // Fields:
    private final String sender;
    private final String chatDate;
    private final List<Messages> messages;
    // TagsFields:
    private String tag = SINGLE_CHAT;
    // Tags:
    public static final String SINGLE_CHAT = "ar.Chat.Single.Get";
    public static final String GROUP_CHAT = "ar.Chat.Group.Get";
    public static final String GROUP_USER = "ar.User.Group.Get";
    // Checking:
    private boolean isHasNewMessage = false;
    private boolean isNewMessage = false;

    // Constructor:
    public Chat(String sender, String chatDate, List<Messages> messages) {
        this.sender = sender;
        this.chatDate = chatDate;
        this.messages = messages;
    }

    // InnerClasses:
    public static class Messages {

        // Fields:
        private final String message;
        private final String messageDate;
        private final boolean sender;
        private boolean seenMes = false;

        // Constructor:
        public Messages(String message, String messageDate, boolean sender) {
            this.message = message;
            this.messageDate = messageDate;
            this.sender = sender;

        }

        public boolean isSeenMes() {
            return seenMes;
        }

        public void setSeenMes(boolean seenMes) {
            this.seenMes = seenMes;
        }

        // Getters:
        public String getMessage() {
            return message;
        }

        public String getMessageDate() {
            return messageDate;
        }

        public boolean isSender() {
            return sender;
        }
    }

    // Tags(Getter(&Setter)):
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    // Checking(Getter(&Setter)):
    public boolean isNewMessage() {
        return isNewMessage;
    }

    public void setNewMessage(boolean newMessage) {
        isNewMessage = newMessage;
    }

    public boolean isHasNewMessage() {
        return isHasNewMessage;
    }

    public void setHasNewMessage(boolean hasNewMessage) {
        isHasNewMessage = hasNewMessage;
    }

    // Getters:
    public String getSender() {
        return sender;
    }

    public String getChatDate() {
        return chatDate;
    }

    public List<Messages> getMessages() {
        return messages;
    }

}
