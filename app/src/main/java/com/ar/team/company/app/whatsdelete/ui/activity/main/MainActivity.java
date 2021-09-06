package com.ar.team.company.app.whatsdelete.ui.activity.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;
import android.view.View;

import com.ar.team.company.app.whatsdelete.R;
import com.ar.team.company.app.whatsdelete.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    // This For Control The XML-Main Views:
    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    private ActivityMainBinding binding;
    // TAGS:
    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater()); // INFLATE THE LAYOUT.
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        Objects.requireNonNull(getSupportActionBar()).hide();
        View view = binding.getRoot(); // GET ROOT [BY DEF(CONSTRAINT LAYOUT)].
        setContentView(view); // SET THE VIEW CONTENT TO THE (VIEW).
        // Initializing:
        // Developing:
    }

}