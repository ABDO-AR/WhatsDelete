package com.ar.team.company.app.whatsdelete.ui.activity.applications;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.ar.team.company.app.whatsdelete.control.adapter.ApplicationsAdapter;
import com.ar.team.company.app.whatsdelete.databinding.ActivityApplicationsBinding;
import com.ar.team.company.app.whatsdelete.model.Application;

import java.util.List;
import java.util.Objects;

public class ApplicationsActivity extends AppCompatActivity {

    // This For Control The XML-Main Views:
    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    private ActivityApplicationsBinding binding;
    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    private ApplicationsViewModel model;
    // Apps:
    private ApplicationsAdapter adapter;
    private List<Application> applications;
    // TAGS:
    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    private static final String TAG = "ApplicationsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityApplicationsBinding.inflate(getLayoutInflater()); // INFLATE THE LAYOUT.
        Objects.requireNonNull(getSupportActionBar()).hide();
        View view = binding.getRoot(); // GET ROOT [BY DEF(CONSTRAINT LAYOUT)].
        setContentView(view); // SET THE VIEW CONTENT TO THE (VIEW).
        // Initializing:
        model = new ViewModelProvider(this).get(ApplicationsViewModel.class);
        // Developing:
        model.getAppsModel().observe(this, this::appsObserver);
    }

    // Observing:
    private void appsObserver(List<Application> applications) {
        // Initializing:
        this.applications = applications;
        this.adapter = new ApplicationsAdapter(this, this.applications);
        // Developing:
        binding.appsRecyclerView.setAdapter(adapter);
        binding.appsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}