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
    // WhatsAppDirsObservers:
    private static FileObserver imagesObserver;
    private static FileObserver videosObserver;
    private static FileObserver voicesObserver;
    private static FileObserver documentsObserver;
    // TempData:
    private static int tempVoices = 0;
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
        manager = new ARPreferencesManager(this);
        // Observers:
        initObservers();
        // StartOurForegroundService:
        ContextCompat.startForegroundService(this, new Intent(this, ARForegroundService.class));
        // Initializing(App):
        initApp();
    }

    // Method(Observers):
    private void initObservers() {
        // Initializing(ImagesObserver):
        imagesObserver = new FileObserver(ARAccess.WHATSAPP_IMAGES_PATH) {
            @Override
            public void onEvent(int i, @Nullable String s) {
                // Debugging:
                Log.d(TAG, "onEvent: " + s);
                // Checking:
                if (i == FileObserver.CREATE || i == FileObserver.ACCESS) {
                    // Debugging:
                    Log.d(TAG, "onEventCreate: " + s);
                    // StartOperations:
                    model.startImageOperation();
                }
            }
        };
        // Initializing(VideosObserver):
        videosObserver = new FileObserver(ARAccess.WHATSAPP_VIDEOS_PATH) {
            @Override
            public void onEvent(int i, @Nullable String s) {
                // Debugging:
                Log.d(TAG, "onEvent: " + s);
                // Checking:
                if (i == FileObserver.CREATE || i == FileObserver.ACCESS) {
                    // Debugging:
                    Log.d(TAG, "onEventCreate: " + s);
                    // StartOperations:
                    model.startVideoOperation();
                }
            }
        };
        // Initializing(VoicesObserver):
        voicesObserver = new FileObserver(ARAccess.WHATSAPP_VOICES_PATH) {
            @Override
            public void onEvent(int i, @Nullable String s) {
                // Debugging:
                Log.d(TAG, "onEvent: " + s);
                // Checking:
                if (tempVoices == 0) {
                    // Debugging:
                    Log.d(TAG, "onEventCreate: " + s);
                    // StartOperations:
                    model.startVoiceOperation();
                    // Increment:
                    tempVoices++;
                }
            }
        };
        // Initializing(DocumentsObserver):
        documentsObserver = new FileObserver(ARAccess.WHATSAPP_DOCUMENTS_PATH) {
            @Override
            public void onEvent(int i, @Nullable String s) {
                // Debugging:
                Log.d(TAG, "onEvent: " + s);
                // Checking:
                if (i == FileObserver.CREATE || i == FileObserver.ACCESS) {
                    // Debugging:
                    Log.d(TAG, "onEventCreate: " + s);
                    // StartOperations:
                    model.startDocumentOperation();
                }
            }
        };
        // Debugging:
        Log.d(TAG, "onEventCreate: " + ARAccess.WHATSAPP_IMAGES_PATH);
        Log.d(TAG, "onEventCreate: " + ARAccess.WHATSAPP_VIDEOS_PATH);
        Log.d(TAG, "onEventCreate: " + ARAccess.WHATSAPP_VOICES_PATH);
        Log.d(TAG, "onEventCreate: " + ARAccess.WHATSAPP_DOCUMENTS_PATH);
        // StartObservers:
        imagesObserver.startWatching();
        videosObserver.startWatching();
        voicesObserver.startWatching();
        documentsObserver.startWatching();
        // Preparing:
        preparingObservers();
    }

    // Method(Preparing):
    private void preparingObservers() {
        // Initializing:
        boolean tempDirsState = manager.getBooleanPreferences(ARPreferencesManager.INIT_TEMP_DIR);
        // Checking:
        if (!tempDirsState) {
            // Setting:
            manager.setBooleanPreferences(ARPreferencesManager.INIT_TEMP_DIR, true);
            // StartInitializing:
            model.startImageOperation();
            model.startVideoOperation();
            model.startVoiceOperation();
            model.startDocumentOperation();
        }
    }

    // This method for control observer on ARImagesAccess:
    public static void setImagesObserver(boolean state) {
        // Checking:
        if (state) imagesObserver.startWatching();
        else imagesObserver.stopWatching();
    }

    public static void setVideosObserver(boolean state) {
        // Checking:
        if (state) videosObserver.startWatching();
        else videosObserver.stopWatching();
    }

    public static void setVoicesObserver(boolean state) {
        // Checking:
        if (state) voicesObserver.startWatching();
        else voicesObserver.stopWatching();
    }

    public static void setDocumentsObserver(boolean state) {
        // Checking:
        if (state) documentsObserver.startWatching();
        else documentsObserver.stopWatching();
    }

    // Methods(Reset):
    public static void resetTempVoices() {
        // Resting:
        tempVoices = 0;
    }

    // InitApp:
    private void initApp() {
        // Initializing(UI):
        initUI();
        // Initializing(MEDIATOR):
        mediator = new TabLayoutMediator(binding.mainContentLayout.homeTabLayout, binding.mainContentLayout.homeViewPager, (tab, position) -> tab.setText(adapter.getHeaders(position)));
        // Initializing(FIELDS):
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

    @Override
    protected void onDestroy() {
        // Initializing:
        boolean imagesState = model.getImagesThread() != null;
        boolean videosState = model.getVideosThread() != null;
        boolean voicesState = model.getVoicesThread() != null;
        boolean documentsState = model.getDocumentsThread() != null;
        // Checking(&Interrupting):
        if (imagesState) model.getImagesThread().interrupt();
        if (videosState) model.getVideosThread().interrupt();
        if (voicesState) model.getVoicesThread().interrupt();
        if (documentsState) model.getDocumentsThread().interrupt();
        // Super:
        super.onDestroy();
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