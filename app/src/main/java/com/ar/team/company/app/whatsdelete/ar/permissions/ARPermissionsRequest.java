package com.ar.team.company.app.whatsdelete.ar.permissions;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;

import androidx.core.app.NotificationManagerCompat;

@SuppressWarnings("unused")
public class ARPermissionsRequest {

    // Fields:
    private final Context context;
    // Fields(Static):
    public static final String NOTIFICATIONS_ACCESS_PAGE = Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS;

    // Constructor:
    public ARPermissionsRequest(Context context) {
        this.context = context;
    }

    // Methods:
    public boolean checkNotificationAccess() {
        // Retuning(NotificationAccess):
        return NotificationManagerCompat.getEnabledListenerPackages(context).contains(context.getPackageName());
    }

    public void runNotificationAccess() {
        // Initializing:
        boolean state = checkNotificationAccess();
        // Developing:
        if (!state) showNotificationAccessDialog((dialog, i) -> onAccessButtonClicked(dialog));
    }

    // Method(ReRunAccess):
    public void reRunNotificationAccess() {
        // Initializing:
        boolean state = checkNotificationAccess();
        // Developing:
        onAccessButtonClicked();
    }

    // Methods(Listener):
    private void onAccessButtonClicked(DialogInterface dialog) {
        // Initializing:
        boolean state = checkNotificationAccess();
        // StartActivity:
        context.startActivity(new Intent(NOTIFICATIONS_ACCESS_PAGE));
        // Developing:
        if (state) dialog.dismiss();
    }
    private void onAccessButtonClicked() {
        // Initializing:
        boolean state = checkNotificationAccess();
        // StartActivity:
        context.startActivity(new Intent(NOTIFICATIONS_ACCESS_PAGE));
    }
    // Methods(UI):
    public void showNotificationAccessDialog(DialogInterface.OnClickListener listener) {
        // Initializing:
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        AlertDialog dialog;
        // Preparing:
        builder.setCancelable(false);
        builder.setTitle("Request Notification Access Permission");
        builder.setMessage("We cannot start this application without notification access click access to enable it.");
        builder.setPositiveButton("Access", listener);
        // Refreshing:
        dialog = builder.create();
        // Developing:
        dialog.show();
    }

    // Getters:
    public Context getContext() {
        return context;
    }
}
