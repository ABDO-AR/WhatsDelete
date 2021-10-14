package com.ar.team.company.app.socialdelete.control.notifications;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.ar.team.company.app.socialdelete.R;
import com.ar.team.company.app.socialdelete.control.icons.ARIconsAccess;
import com.ar.team.company.app.socialdelete.control.preferences.ARPreferencesManager;
import com.ar.team.company.app.socialdelete.model.ARIcon;
import com.ar.team.company.app.socialdelete.model.Chat;
import com.ar.team.company.app.socialdelete.ar.utils.ARUtils;
import com.ar.team.company.app.socialdelete.ui.activity.home.HomeActivity;
import com.ar.team.company.app.socialdelete.ui.activity.show.chat.ShowChatActivity;
import com.ar.team.company.app.socialdelete.ui.interfaces.ChatListener;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

@SuppressWarnings("unused")
public class NotificationListener extends NotificationListenerService {

    // StaticFields:
    public static final String WHATSAPP_PACKAGE_NAME = "com.whatsapp";
    public static final List<Notification.Action> finalActions = new ArrayList<>();
    public static final List<ARIcon> icons = new ArrayList<>();
    // Listeners:
    public static ChatListener listener;
    // Channels:
    public static final String CHANNEL_ID = "AutoRDMDeletedMessage";
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
                    boolean s4 = !sender.equals("Deleting messages...");
                    boolean state = s1 && s2 && s3 && s4;
                    // Fields:
                    ARPreferencesManager manager = new ARPreferencesManager(getApplicationContext());
                    String currentPackages = manager.getStringPreferences(ARPreferencesManager.PACKAGE_APP_NAME);
                    List<Chat.Messages> messages = new ArrayList<>();
                    // Initializing(Data):
                    String msg = sbn.getNotification().extras.getString(Notification.EXTRA_TEXT) + "";
                    String currentSenders = manager.getStringPreferences(ARPreferencesManager.SENDER_NAME);
                    // Checking:
                    boolean deletedMsgState = !msg.equals("This message was deleted") && !msg.equals("\u200Fتم حذف هذه الرسالة");
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
                    if (state && !msg.equals(firstChar + " new messages") && !msg.equals(firstChar + " رسائل جديدة") && !msg.equals("رسالتان ٢ جديدتان") && deletedMsgState) {
                        // Checking(IconState):
                        if (!sender.isEmpty() && sbn.getNotification().getLargeIcon() != null)
                            saveIcon(sender, sbn.getNotification().getLargeIcon());
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
                                            Chat.Messages messages13 = new Chat.Messages(mes.trim(), getCurrentDate(), false);
                                            messages13.setSeenMes(true);
                                            chat.getMessages().add(messages13);
                                            tempChat = chat;
                                        }
                                    }
                                    // SettingChatsAgain:
                                    manager.setStringPreferences(ARPreferencesManager.WHATSAPP_CHATS, ARUtils.fromChatsToJson(savedChats));
                                    // Replaying:
                                    ARUtils.reply(getApplicationContext(), action, mes);
                                    // Debug:
                                    ARUtils.debug(TAG, NP_FIELD, manager.getStringPreferences(ARPreferencesManager.WHATSAPP_CHATS));
                                    // ResponseToTemps:
                                    sentTemp.set(true);
                                }
                            }
                            // Returning:
                            return tempChat;
                        };
                        // AddingData:
                        Chat.Messages messages1 = new Chat.Messages(msg.trim(), date, true);
                        messages1.setSeenMes(false);
                        messages.add(messages1);
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
                                            // Operations:
                                            if (msg.equals("null")) {
                                                chat.setTag(Chat.GROUP_CHAT);
                                                // Clearing:
                                                chat.getMessages().clear();
                                                // Adding:
                                                chat.getMessages().addAll(messages);
                                            } else if (sender.contains(": ")) {
                                                chat.setTag(Chat.GROUP_USER);
                                                for (int index = 0; index < chats.size(); index++) {
                                                    if (chat.getSender().contains(chats.get(index).getSender())) {
                                                        // This is the group for this chat:
                                                        Chat.Messages tempMes = new Chat.Messages(msg.trim(), date, true);
                                                        tempMes.setSeenMes(false);
                                                        chats.get(index).getMessages().add(tempMes);
                                                    }
                                                }
                                            } else {
                                                chat.setTag(Chat.SINGLE_CHAT);
                                                // Adding:
                                                chat.getMessages().addAll(messages);
                                            }
                                            // SettingNewMessage:
                                            chat.setHasNewMessage(true);
                                            chat.setNewMessage(true);
                                            ARPreferencesManager.sender = chat.getSender();
                                        }
                                    } else {
                                        // Operations:
                                        if (msg.equals("null")) {
                                            chat.setTag(Chat.GROUP_CHAT);
                                            // Clearing:
                                            chat.getMessages().clear();
                                            // Adding:
                                            chat.getMessages().addAll(messages);
                                        } else if (sender.contains(": ")) {
                                            chat.setTag(Chat.GROUP_USER);
                                            for (int index = 0; index < chats.size(); index++) {
                                                if (chat.getSender().contains(chats.get(index).getSender())) {
                                                    // This is the group for this chat:
                                                    Chat.Messages tempMes = new Chat.Messages(msg.trim(), date, true);
                                                    tempMes.setSeenMes(false);
                                                    chats.get(index).getMessages().add(tempMes);
                                                }
                                            }
                                        } else {
                                            chat.setTag(Chat.SINGLE_CHAT);
                                            // Adding:
                                            chat.getMessages().addAll(messages);
                                        }
                                        chat.setHasNewMessage(true);
                                    }
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
                                chat.setHasNewMessage(true);
                                // Operations:
                                if (msg.equals("null")) {
                                    chat.setTag(Chat.GROUP_CHAT);
                                    // Clearing:
                                    chat.getMessages().clear();
                                    // AddingTheNewChat:
                                    chats.add(chat);
                                } else if (sender.contains(": ")) {
                                    chat.setTag(Chat.GROUP_USER);
                                    for (int index = 0; index < chats.size(); index++) {
                                        if (chat.getSender().contains(chats.get(index).getSender())) {
                                            // This is the group for this chat:
                                            Chat.Messages tempMes = new Chat.Messages(msg.trim(), date, true);
                                            tempMes.setSeenMes(false);
                                            chats.get(index).getMessages().add(tempMes);
                                        }
                                    }
                                } else {
                                    chat.setTag(Chat.SINGLE_CHAT);
                                    // AddingTheNewChat:
                                    chats.add(chat);
                                }
                            }
                        } else {
                            // Initializing:
                            chats = new ArrayList<>();
                            Chat chat = new Chat(sender, getCurrentDate(), messages);
                            chat.setHasNewMessage(true);
                            // Operations:
                            if (msg.equals("null")) {
                                chat.setTag(Chat.GROUP_CHAT);
                                // Clearing:
                                chat.getMessages().clear();
                                // Developing:
                                chats.add(chat);
                            } else if (sender.contains(": ")) {
                                chat.setTag(Chat.GROUP_USER);
                            } else {
                                chat.setTag(Chat.SINGLE_CHAT);
                                // Developing:
                                chats.add(chat);
                            }
                        }
                        // SettingPreferences:
                        manager.setStringPreferences(ARPreferencesManager.WHATSAPP_CHATS, ARUtils.fromChatsToJson(chats));
                        // Debugging:
                        ARUtils.debug(TAG, NP_FIELD, manager.getStringPreferences(ARPreferencesManager.WHATSAPP_CHATS));
                    } else if (msg.equals("This message was deleted") || msg.equals("\u200Fتم حذف هذه الرسالة")) {
                        // Creating:
                        NotificationManager notificationManager = getSystemService(NotificationManager.class);
                        createNotificationChannel(notificationManager);
                        // Preparing:
                        // Create an explicit intent for an Activity in your app
                        Intent intent = new Intent(this, HomeActivity.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                                .setSmallIcon(R.drawable.ic_notification_small_icon)
                                .setContentTitle("SocialDelete")
                                .setContentText("Message Deleted Was Found")
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setContentIntent(pendingIntent)
                                .setAutoCancel(true);
                        // notificationId is a unique int for each notification that you must define
                        notificationManager.notify(881231, builder.build());
                    }
                    // SettingLis:
                    if (listener != null) listener.onChatUpdate();
                    // Debugging:
                    ARUtils.debug(TAG, NP_FIELD, "Whatsapp Package Was Founded In Preferences");
                    // Debugging:
                    ARUtils.debug(TAG, NP_FIELD, "Start");
                }
            }
        }
    }

    // Method(SaveIcon):
    private void saveIcon(String sender, Icon icon) {
        // Initializing:
        ARIconsAccess access = new ARIconsAccess(getApplication().getApplicationContext(), sender, icon);
        // Developing:
        access.createUserIcon();
    }

    // Method(Notification):
    private void createNotificationChannel(NotificationManager notificationManager) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            notificationManager.createNotificationChannel(channel);
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