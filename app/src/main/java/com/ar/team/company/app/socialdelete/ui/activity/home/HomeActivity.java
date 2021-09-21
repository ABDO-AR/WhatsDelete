package com.ar.team.company.app.socialdelete.ui.activity.home;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.FileObserver;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ar.team.company.app.socialdelete.R;
import com.ar.team.company.app.socialdelete.ar.access.ARAccess;
import com.ar.team.company.app.socialdelete.control.adapter.HomeItemsAdapter;
import com.ar.team.company.app.socialdelete.control.adapter.PagerAdapter;
import com.ar.team.company.app.socialdelete.control.foreground.ARForegroundService;
import com.ar.team.company.app.socialdelete.control.preferences.ARPreferencesManager;

import com.ar.team.company.app.socialdelete.databinding.ActivityHomeBinding;
import com.ar.team.company.app.socialdelete.ui.activity.applications.ApplicationsActivity;
import com.ar.team.company.app.socialdelete.ui.activity.settings.SettingsActivity;
import com.ar.team.company.app.socialdelete.ui.interfaces.HomeItemClickListener;
import com.ar.team.company.app.socialdelete.ar.utils.ARUtils;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;

import java.util.Objects;

@SuppressWarnings({"FieldCanBeLocal", "unused"})
public class HomeActivity extends AppCompatActivity implements HomeItemClickListener, SharedPreferences.OnSharedPreferenceChangeListener {

    // This For Control The XML-Main Views:
    private ActivityHomeBinding binding;
    private HomeViewModel model;
    // Drawer(&TabLayout):
    private PagerAdapter adapter;
    private ARPreferencesManager manager;
    // TabMediator:
    private TabLayoutMediator mediator;
    // Observers:
    private static FileObserver imagesObserver;
    // TAGS:
    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater()); // INFLATE THE LAYOUT.
        View view = binding.getRoot(); // GET ROOT [BY DEF(CONSTRAINT LAYOUT)].
        setContentView(view); // SET THE VIEW CONTENT TO THE (VIEW).
        // Initializing(MAIN-FIELDS):
        model = new ViewModelProvider(this).get(HomeViewModel.class);
        initObservers();
        // StartOurForegroundService:
        ContextCompat.startForegroundService(this, new Intent(this, ARForegroundService.class));
        // Initializing(App):
        initApp();
    }

    // Method(Observers):
    private void initObservers() {
        // Initializing(Observers):
        imagesObserver = new FileObserver(ARAccess.WHATSAPP_IMAGES_PATH) {
            @Override
            public void onEvent(int i, @Nullable String s) {
                // Debugging:
                Log.d(TAG, "onEvent: " + s);
                // Checking:
                if (i == FileObserver.CREATE || i == FileObserver.ACCESS){
                    // Debugging:
                    Log.d(TAG, "onEventCreate: " + s);
                    // StartOperations:
                    model.startImageOperation();
                }
            }
        };
        // StartObservers:
        imagesObserver.startWatching();
    }

    // This method for control observer on ARImagesAccess:
    public static void setImagesObserver(boolean state){
        // Checking:
        if (state) imagesObserver.startWatching();
        else imagesObserver.stopWatching();
    }

    // InitApp:
    private void initApp() {
        // Initializing(UI):
        initUI();
        // Initializing(MEDIATOR):
        mediator = new TabLayoutMediator(binding.mainContentLayout.homeTabLayout, binding.mainContentLayout.homeViewPager, (tab, position) -> tab.setText(adapter.getHeaders(position)));
        // Initializing(FIELDS):
        manager = new ARPreferencesManager(this);
        adapter = new PagerAdapter(getSupportFragmentManager(), getLifecycle());
        // AttachMediator:
        binding.mainContentLayout.homeViewPager.setAdapter(adapter);
        mediator.attach();
        // OpeningDrawer:
        binding.mainContentLayout.btnDrawer.setOnClickListener(view1 -> binding.drawerLayout.openMenu(true));
        // SettingMenuSize:
        settingSize();
        // ManagerListener:
        manager.getPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    // SharedPreferencesChangeListener:
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        // Initializing(APP):
        initApp();
    }

    @Override
    protected void onResume() {
        // Initializing(APP):
        initApp();
        // Super:
        super.onResume();
    }

    @Override
    protected void onStop() {
        // UnRegistering:
        manager.getPreferences().unregisterOnSharedPreferenceChangeListener(this);
        // Super:
        super.onStop();
    }

    // SettingDrawerSize:
    private void settingSize() {
        // Initializing:
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        // GettingWidth:
        int width = displayMetrics.widthPixels;
        // Developing:
        binding.drawerLayout.setMenuSize(width);
    }

    // Initializing(UserInterface):
    private void initUI() {
        // Setting The New ActionBar:
        setSupportActionBar(binding.mainContentLayout.toolbar);
        // Initializing:
        binding.drawerLayout.setTouchMode(ElasticDrawer.TOUCH_MODE_BEZEL);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        binding.menuRecyclerView.setLayoutManager(gridLayoutManager);
        // Developing Nav Drawer:
        HomeItemsAdapter homeItemsAdapter = new HomeItemsAdapter(this);
        binding.menuRecyclerView.setAdapter(homeItemsAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // DefState:
        boolean state = manager.getBooleanPreferences(ARPreferencesManager.APP_INIT);
        // Developing:
        if (!state) startActivity(new Intent(this, ApplicationsActivity.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // InflatingHomeMenu:
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        // Returning:
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Developing:
        switch (item.getItemId()) {
            case R.id.menu_setting:
                ARUtils.runActivity(this, SettingsActivity.class);
                break;
            case R.id.menu_store:
                Toast.makeText(this, "Store", Toast.LENGTH_SHORT).show();
                break;
        }
        // Returning:
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // ClosingDrawer:
        if (ElasticDrawer.STATE_CLOSED != binding.drawerLayout.getDrawerState()) {
            binding.drawerLayout.closeMenu(false);
        } else super.onBackPressed();
    }

    @Override
    public void openFragment(int pos) {
        // Initializing:
        TabLayout.Tab tab = binding.mainContentLayout.homeTabLayout.getTabAt(pos);
        // Developing:
        Objects.requireNonNull(tab).select();
        // ClosingDrawer:
        binding.drawerLayout.closeMenu(false);
    }
}