package com.ar.team.company.app.whatsdelete.ui.activity.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ar.team.company.app.whatsdelete.databinding.ActivityHomeBinding;
import com.ar.team.company.app.whatsdelete.model.Application;
import com.ar.team.company.app.whatsdelete.ui.activity.applications.ApplicationsActivity;

public class HomeActivity extends AppCompatActivity {

    // This For Control The XML-Main Views:
    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    private ActivityHomeBinding binding;
    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    private HomeViewModel model;
    // TAGS:
    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater()); // INFLATE THE LAYOUT.
        View view = binding.getRoot(); // GET ROOT [BY DEF(CONSTRAINT LAYOUT)].
        setContentView(view); // SET THE VIEW CONTENT TO THE (VIEW).
        // Initializing:
        model = new ViewModelProvider(this).get(HomeViewModel.class);
        // Developing:
        startActivity(new Intent(this, ApplicationsActivity.class));
    }
}