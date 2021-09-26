package com.ar.team.company.app.socialdelete.control.notifications;

import android.app.Notification;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.ar.team.company.app.socialdelete.control.preferences.ARPreferencesManager;
import com.ar.team.company.app.socialdelete.model.ARIcon;
import com.ar.team.company.app.socialdelete.model.Chat;
import com.ar.team.company.app.socialdelete.ar.utils.ARUtils;
import com.ar.team.company.app.socialdelete.ui.activity.show.chat.ShowChatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@SuppressWarnings("unused")
public class NotificationListener extends NotificationListenerService {

    // StaticFields:
    public static final String WHATSAPP_PACKAGE_NAME = "com.whatsapp";
    public static final List<Notification.Action> finalActions = new ArrayList<>();
    public static final List<ARIcon> icons = new ArrayList<>();
    // TAGS:
    private static final String TAG = "NotificationListener";
    private static final String NP_FIELD = "onNotificationPosted: ";

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        // Checking:
        if (sbn.getPackageName() != null) {
            // Checking(WhatsAppPackageName):
            if (sbn.getPackageName().equals(WHATSAPP_PACKAGE_NAME)) {
                // Initializing(Date):
                String date = getCurrentDate();
                // Creating(Chats):
                List<Chat> chats;
                // Initializing(Data):
                String sender = sbn.getNotification().extras.getString(Notification.EXTRA_TITLE);
                // Checking:
                if (sender != null) {
                    // CheckingFields:
                    boolean s1 = sbn.getPackageName().equals(WHATSAPP_PACKAGE_NAME);
                    boolean s2 = !sender.equals("WhatsApp") && !sender.equals("WhatsApp Web") && !sender.equals("WhatsApp Desktop");
                    boolean s3 = !sender.equals("Me") && !sender.equals("You");
                    boolean state = s1 && s2 && s3;
                    // Fields:
                    ARPreferencesManager manager = new ARPreferencesManager(getApplicationContext());
                    String currentPackages = manager.getStringPreferences(ARPreferencesManager.PACKAGE_APP_NAME);
                    List<Chat.Messages> messages = new ArrayList<>();
                    // Initializing(Data):
                    String msg = sbn.getNotification().extras.getString(Notification.EXTRA_TEXT);
                    String currentSenders = manager.getStringPreferences(ARPreferencesManager.SENDER_NAME);
                    // Design:
                    String firstChar;
                    // Trying:
                    try {
                        // Initializing:
                        firstChar = msg.split(" ")[0];
                    } catch (Exception e) {
                        // Trying:
                        try {
                            // Initializing:
                            firstChar = msg.substring(0, 1);
                        } catch (NullPointerException nullPointerException) {
                            // Initializing:
                            firstChar = "A";
                            // Debug:
                            Log.d(TAG, "onNotificationPosted: " + nullPointerException.toString());
                        }
                    }
                    // CheckingStatusBarNotification:
                    if (state && !msg.equals(firstChar + " new messages")) {
                        // Initializing(Replay):
                        Notification.WearableExtender extender = new Notification.WearableExtender(sbn.getNotification());
                        List<Notification.Action> actions = new ArrayList<>(extender.getActions());
                        // AddingAll:
                        finalActions.addAll(actions);
                        // Filtering(FinalActions):
                        List<Notification.Action> filteringAction = new ArrayList<>(new HashSet<>(finalActions));
                        // Clearing(FinalAction):
                        finalActions.clear();
                        // AddingAll(FilteringActions):
                        finalActions.addAll(filteringAction);
                        // Icons:
                        icons.add(new ARIcon(sender, sbn.getNotification().getLargeIcon()));
                        // LoopingValues:
                        ShowChatActivity.clicked = (senderName, mes) -> {
                            // Temps:
                            AtomicBoolean sentTemp = new AtomicBoolean(false);
                            Chat tempChat = null;
                            // Looping:
                            for (Notification.Action action : finalActions) {
                                // Debugging:
                                Log.d(TAG, "ARActions.Debug: " + action.title);
                                // Checking:
                                if (action.title.toString().contains(senderName) && !sentTemp.get()) {
                                    // AddingMeMessages:
                                    List<Chat> savedChats = ARUtils.fromJsonToChats(manager.getStringPreferences(ARPreferencesManager.WHATSAPP_CHATS));
                                    // Looping:
                                    for (Chat chat : savedChats) {
                                        // Checking:
                                        if (chat.getSender().equals(senderName)) {
                                            // Setting:
                                            tempChat = chat;
                                            chat.getMessages().add(new Chat.Messages(mes.trim(), getCurrentDate(), false));
                                        }
                                    }
                                    // SettingChatsAgain:
                                    manager.setStringPreferences(ARPreferencesManager.WHATSAPP_CHATS, ARUtils.fromChatsToJson(savedChats));
                                    // Replaying:
                                    ARUtils.reply(getApplicationContext(), action, mes);
                                    // ResponseToTemps:
                                    sentTemp.set(true);
                                }
                            }
                            // Returning:
                            return tempChat;
                        };
                        // AddingData:
                        messages.add(new Chat.Messages(msg.trim(), date, true));
                        // Developing:
                        if (manager.getPreferences().contains(ARPreferencesManager.WHATSAPP_CHATS)) {
                            // Initializing:
                            chats = ARUtils.fromJsonToChats(manager.getStringPreferences(ARPreferencesManager.WHATSAPP_CHATS));
                            // Developing:
                            for (Chat chat : chats) {
                                if (chat.getSender().equals(sender)) {
                                    // AddingTheNewMessage:
                                    if (!chat.getMessages().isEmpty()) {
                                        // Checking(MessagesSizeForRemovingDuplicates):
                                        if (!chat.getMessages().get(chat.getMessages().size() - 1).getMessage().equals(messages.get(0).getMessage())) {
                                            // Adding:
                                            chat.getMessages().addAll(messages);
                                        }
                                    } else chat.getMessages().addAll(messages); // Adding.
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
                                Chat chat = new Chat(sender, getCurrentDate(), messages);
                                // AddingTheNewChat:
                                chats.add(chat);
                            }
                        } else {
                            // Initializing:
                            chats = new ArrayList<>();
                            // Developing:
                            chats.add(new Chat(sender, getCurrentDate(), messages));
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
        }
    }

    // Method(Date):
    private String getCurrentDate() {
        // Initializing(Calendar):
        Calendar calendar = Calendar.getInstance();
        // Preparing(Calendar):
        int hour24hrs = calendar.get(Calendar.HOUR);
        int minutes = calendar.get(Calendar.MINUTE);
        String dayState = (calendar.get(Calendar.AM_PM) == Calendar.AM) ? " AM" : " PM";
        // Setting(Date):
        return hour24hrs + ":" + minutes + dayState;
    }
}