package com.ar.team.company.app.whatsdelete.control.notifications;

import android.app.Notification;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import com.ar.team.company.app.whatsdelete.control.preferences.ARPreferencesManager;
import com.ar.team.company.app.whatsdelete.model.Chat;
import com.ar.team.company.app.whatsdelete.utils.ARUtils;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotificationListener extends NotificationListenerService {

    // StaticFields:
    public static final String WHATSAPP_PACKAGE_NAME = "com.whatsapp";
    // TAGS:
    private static final String TAG = "NotificationListener";
    private static final String NP_FIELD = "onNotificationPosted: ";

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        // Fields:
        ARPreferencesManager manager = new ARPreferencesManager(getApplicationContext());
        String currentPackages = manager.getStringPreferences(ARPreferencesManager.PACKAGE_APP_NAME);
        List<Chat.Messages> messages = new ArrayList<>();
        // CheckingStatusBarNotification:
        if (sbn.getPackageName().equals(WHATSAPP_PACKAGE_NAME)) {
            // Initializing(DateTime):
            String date = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(new Date());
            List<Chat> chats;
            // Initializing(Data):
            String sender = sbn.getNotification().extras.getString(Notification.EXTRA_TITLE);
            String msg = sbn.getNotification().extras.getString(Notification.EXTRA_TEXT);
            String currentSenders = manager.getStringPreferences(ARPreferencesManager.SENDER_NAME);
            // AddingData:
            messages.add(new Chat.Messages(msg));
            // Developing:
            if (manager.getPreferences().contains(ARPreferencesManager.WHATSAPP_CHATS)) {
                // Initializing:
                chats = ARUtils.fromJsonToChats(manager.getStringPreferences(ARPreferencesManager.WHATSAPP_CHATS));
                // Developing:
                for (Chat chat : chats) {
                    if (chat.getSender().equals(sender)) {
                        // AddingTheNewMessage:
                        chat.getMessages().addAll(messages);
                        // AddingSender($Preferences):
                        if (!currentSenders.contains(sender)) {
                            // Initializing:
                            manager.setStringPreferences(ARPreferencesManager.SENDER_NAME, sender + ",");
                            // Refreshing:
                            currentSenders = manager.getStringPreferences(ARPreferencesManager.SENDER_NAME);
                        }
                    }
                }
                // CheckingSenders:
                if (!currentSenders.contains(sender)) {
                    // Initializing:
                    Chat chat = new Chat(sender, "", date, null, messages);
                    // AddingTheNewChat:
                    chats.add(chat);
                }
            } else {
                // Initializing:
                chats = new ArrayList<>();
                // Developing:
                chats.add(new Chat(sender, "", date, null, messages));
            }
            // SettingPreferences:
            manager.setStringPreferences(ARPreferencesManager.WHATSAPP_CHATS, ARUtils.fromChatsToJson(chats));
            // Debugging:
            ARUtils.debug(TAG, NP_FIELD, manager.getStringPreferences(ARPreferencesManager.WHATSAPP_CHATS));
        }
        // Debugging:
        ARUtils.debug(TAG, NP_FIELD, "Whatsapp Package Was Founded In Preferences");
        // Debugging:
        ARUtils.debug(TAG, NP_FIELD, "Start");
    }
}
