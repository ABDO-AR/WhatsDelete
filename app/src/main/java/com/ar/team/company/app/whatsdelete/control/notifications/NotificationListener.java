package com.ar.team.company.app.whatsdelete.control.notifications;

import android.app.Notification;
import android.app.RemoteInput;
import android.content.Intent;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.widget.Toast;

import com.ar.team.company.app.whatsdelete.control.preferences.ARPreferencesManager;
import com.ar.team.company.app.whatsdelete.model.Chat;
import com.ar.team.company.app.whatsdelete.ar.utils.ARUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
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
            // Initializing(Replay):
            Notification.WearableExtender extender = new Notification.WearableExtender(sbn.getNotification());
            List<Notification.Action> actions = new ArrayList<>(extender.getActions());
            // Initializing(Icon):
            // PreparingIcon:
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
                    Chat chat = new Chat(sender, "", date, null, messages, null);
                    // AddingTheNewChat:
                    chats.add(chat);
                }
            } else {
                // Initializing:
                chats = new ArrayList<>();
                // Developing:
                chats.add(new Chat(sender, "", date, null, messages, null));
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
