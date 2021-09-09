package com.ar.team.company.app.whatsdelete.ui.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.ar.team.company.app.whatsdelete.control.preferences.ARPreferencesManager;
import com.ar.team.company.app.whatsdelete.databinding.ActivitySettingsBinding;

@SuppressWarnings("FieldCanBeLocal")
public class SettingsActivity extends AppCompatActivity {

    // This For Control The XML-Main Views:
    private ActivitySettingsBinding binding;
    private ARPreferencesManager manager;
    // Themes:
    private String[] themes;
    // TAGS:
    private static final String TAG = "SettingsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySettingsBinding.inflate(getLayoutInflater()); // INFLATE THE LAYOUT.
        View view = binding.getRoot(); // GET ROOT [BY DEF(CONSTRAINT LAYOUT)].
        setContentView(view); // SET THE VIEW CONTENT TO THE (VIEW).
        // Initializing:
        manager = new ARPreferencesManager(this);
        themes = new String[]{"Light", "Dark"};
        // Developing:
        binding.backButton.setOnClickListener(v -> finish());
        binding.themeCardView.setOnClickListener(this::switchThemes);
    }

    // OnThemeClick:
    private void switchThemes(View view) {
        // Initializing:
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        boolean state = manager.getBooleanPreferences(ARPreferencesManager.LIGHT_THEME);
        // Preparing:
        builder.setTitle("Choose Theme");
        builder.setSingleChoiceItems(themes, state ? 0 : 1, this::onThemeSelected);
        // Developing:
        builder.show();
    }

    private void onThemeSelected(DialogInterface dialogInterface, int i) {
        // Initializing:
        String chosenTheme = themes[i];
        boolean state = chosenTheme.equals("Light");
        // Developing:
        if (state) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        // SettingPreferences:
        manager.setBooleanPreferences(ARPreferencesManager.LIGHT_THEME, state);
    }
}