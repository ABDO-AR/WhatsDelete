package com.ar.team.company.app.socialdelete.control.foreground;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.ar.team.company.app.socialdelete.R;
import com.ar.team.company.app.socialdelete.ui.activity.home.HomeActivity;
import com.ar.team.company.app.socialdelete.ui.activity.main.MainActivity;

public class ARForegroundService extends Service {

    // Fields:
    public static final String CHANNEL_ID = "SocialDeleteForeground";

    NotificationManager manager;
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Creating Notifications:
        createNotificationChannel();
        // Initializing:
        Bitmap icon = BitmapFactory.decodeResource(getResources(),R.drawable.ic_foreground_icon);

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        @SuppressLint("WrongConstant")
        Notification notification =
                new NotificationCompat.Builder(this, CHANNEL_ID).setContentTitle("SocialDelete")
                .setContentText("SocialDelete")
                .setSubText("SocialDelete service is running")
                        .setBadgeIconType(R.drawable.ic_launcher_foreground).
                        setLargeIcon(icon)
                        .setContentIntent(pendingIntent).build();


        // StartingOurService:
        startForeground(1, notification);

        // Retaining:
        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    // Methods(CreateNotificationsChannels):
    private void createNotificationChannel() {
        // Checking:
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Initializing:
            NotificationChannel serviceChannel = new NotificationChannel(CHANNEL_ID, "SocialDelete Service Channel", NotificationManager.IMPORTANCE_DEFAULT);
             manager = getSystemService(NotificationManager.class);
            assert manager != null;
            manager.createNotificationChannel(serviceChannel);
        }
    }

    public class CallBinder extends Binder {


        public ARForegroundService getService()
        {
            return ARForegroundService.this;
        }
    }
}
