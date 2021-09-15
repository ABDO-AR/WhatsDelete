package com.ar.team.company.app.whatsdelete.ui.activity.applications;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ar.team.company.app.whatsdelete.control.adapter.ApplicationsAdapter;
import com.ar.team.company.app.whatsdelete.databinding.ActivityApplicationsBinding;
import com.ar.team.company.app.whatsdelete.databinding.SingleAppItemBinding;
import com.ar.team.company.app.whatsdelete.model.Application;
import com.ar.team.company.app.whatsdelete.ui.activity.home.HomeActivity;
import com.ar.team.company.app.whatsdelete.ar.utils.ARUtils;

import java.util.List;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class ApplicationsActivity extends AppCompatActivity implements ApplicationsAdapter.Apps {

    // This For Control The XML-Main Views:
    private ActivityApplicationsBinding binding;
    private ApplicationsViewModel model;
    // Apps:
    private ApplicationsAdapter adapter;
    private List<Application> applications;
    // TAGS:
    private static final String TAG = "ApplicationsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityApplicationsBinding.inflate(getLayoutInflater()); // INFLATE THE LAYOUT.
        View view = binding.getRoot(); // GET ROOT [BY DEF(CONSTRAINT LAYOUT)].
        setContentView(view); // SET THE VIEW CONTENT TO THE (VIEW).
        // Initializing:
        model = new ViewModelProvider(this).get(ApplicationsViewModel.class);
        // Developing:
        model.getAppsModel().observe(this, this::appsObserver);
        // Developing(Views):
        binding.nextBoardLayout.setOnClickListener(v -> startActivity(ARUtils.runNewTask(this, this, HomeActivity.class)));
    }

    // Observing:
    private void appsObserver(List<Application> applications) {
        // Initializing:
        this.applications = applications;
        this.adapter = new ApplicationsAdapter(this, this.applications, this);
        // Developing:
        binding.appsRecyclerView.setAdapter(adapter);
        binding.appsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override // OnAppClicked:
    public void onAppClicked(List<Application> apps, int pos, SingleAppItemBinding appBinding) {
        // Developing:
        binding.allAppsSwitch.setOnCheckedChangeListener((c, checked) -> appsChanged(apps, checked));
    }

    private void appsChanged(List<Application> apps, boolean checked) {
        // Developing:
        for (Application app : apps) {
            // Setting:
            app.setChecked(checked);
            // Notifying:
            adapter.notifyDataSetChanged();
        }
    }
}