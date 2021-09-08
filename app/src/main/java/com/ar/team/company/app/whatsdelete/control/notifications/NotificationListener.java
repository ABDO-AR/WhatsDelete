package com.ar.team.company.app.whatsdelete.control.notifications;

import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import java.text.DateFormat;
import java.util.Date;

public class NotificationListener extends NotificationListenerService {

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);

        if (sbn.getPackageName().equals("com.whatsapp")) {
            // Initializing:
            String date =   DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(new Date());
            String sender = sbn.getNotification().extras.getString("android.title");
            String msg =    sbn.getNotification().extras.getString("android.text");
            // Developing:
        }

    }

}
