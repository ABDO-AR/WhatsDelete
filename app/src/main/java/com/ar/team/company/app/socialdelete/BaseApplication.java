package com.ar.team.company.app.socialdelete;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.ar.team.company.app.socialdelete.control.foreground.ARForegroundService;
import com.ar.team.company.app.socialdelete.ui.activity.home.HomeActivity;

public class BaseApplication extends Application {

    // TAGS:
    private static final String TAG = "BaseApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        // StartingForegroundService:
        ContextCompat.startForegroundService(this, new Intent(this, ARForegroundService.class));
    }
}
