package com.ar.team.company.app.whatsdelete.ui.activity.applications;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ar.team.company.app.whatsdelete.model.Application;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ApplicationsViewModel extends AndroidViewModel {

    // Fields:
    private final MutableLiveData<List<Application>> data = new MutableLiveData<>();
    private final LiveData<List<Application>> applications = data;
    // TAGS:
    private static final String TAG = "ApplicationsViewModel";

    // Def constructor for context:
    public ApplicationsViewModel(@NonNull @NotNull android.app.Application application) {
        super(application);
    }

    // Operations:
    public LiveData<List<Application>> getAppsModel() {
        // Initializing:
        List<Application> apps = new ArrayList<>(); // The returning or setting apps list.
        PackageManager manager = getApplication().getPackageManager(); // Getting the current package manager.
        List<ApplicationInfo> packages = manager.getInstalledApplications(PackageManager.GET_META_DATA); // Get all apps.
        // Developing:
        for (ApplicationInfo packageInfo : packages) { // For loop on list of applications info.
            // Initializing:
            String appName = manager.getApplicationLabel(packageInfo).toString(); // Getting app name.
            Drawable appIcon = manager.getApplicationIcon(packageInfo); // Getting app icon as drawable.
            Drawable appLogo = manager.getApplicationLogo(packageInfo);
            // Developing:
            apps.add(new Application(appName, packageInfo.packageName, appIcon, appLogo)); // add the new app.
        }
        // Setting the apps values:
        data.setValue(apps);
        // Debugging:
        Log.d(TAG, "getAppsModel: Data got Successfully");
        // Returning:
        return applications;
    }

    // Getters:
    public MutableLiveData<List<Application>> getData() {
        return data;
    }

    public LiveData<List<Application>> getApplications() {
        return applications;
    }
}
