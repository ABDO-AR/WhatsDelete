package com.ar.team.company.app.whatsdelete.control.preferences;

import android.content.Context;
import android.content.SharedPreferences;

@SuppressWarnings("unused")
public class ARPreferencesManager {

    // Fields:
    private final SharedPreferences preferences;
    private final SharedPreferences.Editor editor;
    // PreferencesName:
    public static final String PREFERENCES_NAME = "ar.Shared.Preferences.Name";
    // PreferencesKeys:
    public static final String APP_INIT = "ar.Shared.Preferences.Init.App.Created";

    // Constructor:
    public ARPreferencesManager(Context context) {
        // Initializing:
        this.preferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        this.editor = preferences.edit();
        // Developing:
        editor.apply();
    }

    // Setter(Preferences):
    public void setStringPreferences(String key, String value) {
        // Developing:
        editor.putString(key, value);
        editor.apply();
    }

    public void setIntegerPreferences(String key, Integer value) {
        // Developing:
        editor.putInt(key, value);
        editor.apply();
    }

    public void setBooleanPreferences(String key, Boolean value) {
        // Developing:
        editor.putBoolean(key, value);
        editor.apply();
    }

    // Getter(Preferences):
    public String getStringPreferences(String key) {
        // Developing:
        return preferences.getString(key, "Empty");
    }

    public Integer getIntegerPreferences(String key) {
        // Developing:
        return preferences.getInt(key, 0);
    }

    public Boolean getBooleanPreferences(String key) {
        // Developing:
        return preferences.getBoolean(key, false);
    }

    // Getters:
    public SharedPreferences getPreferences() {
        return preferences;
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }
}
