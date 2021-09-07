package com.ar.team.company.app.whatsdelete.utils;

import android.content.Context;
import android.content.Intent;

import com.ar.team.company.app.whatsdelete.control.preferences.ARPreferencesManager;
import com.ar.team.company.app.whatsdelete.ui.activity.applications.ApplicationsActivity;
import com.ar.team.company.app.whatsdelete.ui.activity.home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the default class for any utils or helping things
 * like static methods or fields
 * you can put here all your utilities.
 */
@SuppressWarnings("unused")
public class ARUtils {

    // Fields:

    // Constructors:

    // Methods:
    public static List<String> getAppsPackages(ARPreferencesManager manager) {
        // Initializing:
        // Developing:
        return new ArrayList<>();
    }

    // For running new task intent:
    public static Intent runNewTask(Context context, Class<?> cls) {
        // Initializing:
        Intent newTask = new Intent(context,cls);
        // AddingFlags:
        newTask.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        newTask.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // Returning:
        return newTask;
    }

    // Getters(&Setters):

}
