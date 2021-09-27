package com.ar.team.company.app.socialdelete.ui.activity.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import com.ar.team.company.app.socialdelete.ar.observer.ARFilesObserver;
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
    private static FileObserver statusObserver;
    private static FileObserver documentsObserver;
    ProgressDialog dialog;
    // TempData:
    private Thread tempThread;
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
        // Initializing(App):
        initApp();
    }

    // Method(Observers):
    private void initObservers() {
        // Dialog:
        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading");
        dialog.setMessage("LoadingObservers");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        // Initializing(ImagesObserver):
        imagesObserver = new ARFilesObserver(ARAccess.WHATSAPP_IMAGES_PATH, model);
        // Initializing(VideosObserver):
        videosObserver = new ARFilesObserver(ARAccess.WHATSAPP_VIDEOS_PATH, model);
        // Initializing(VoicesObserver):
        voicesObserver = new ARFilesObserver(ARAccess.WHATSAPP_VOICES_PATH, model);
        // Initializing(StatusObserver):
        statusObserver = new ARFilesObserver(ARAccess.WHATSAPP_STATUS_PATH, model);
        // Initializing(DocumentsObserver):
        documentsObserver = new ARFilesObserver(ARAccess.WHATSAPP_DOCUMENTS_PATH, model);
        // Debugging:
        Log.d(TAG, "onEventCreate: " + ARAccess.WHATSAPP_IMAGES_PATH);
        Log.d(TAG, "onEventCreate: " + ARAccess.WHATSAPP_VIDEOS_PATH);
        Log.d(TAG, "onEventCreate: " + ARAccess.WHATSAPP_VOICES_PATH);
        Log.d(TAG, "onEventCreate: " + ARAccess.WHATSAPP_STATUS_PATH);
        Log.d(TAG, "onEventCreate: " + ARAccess.WHATSAPP_DOCUMENTS_PATH);
        // StartObservers:
        imagesObserver.startWatching();
        videosObserver.startWatching();
        voicesObserver.startWatching();
        statusObserver.startWatching();
        documentsObserver.startWatching();
        // Preparing:
        tempThread = new Thread(this::preparingObservers);
        // Start:
        tempThread.start();
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
            model.startStatusOperation();
            model.startDocumentOperation();
        }
        // Finishing:
        runOnUiThread(() -> dialog.hide());
        tempThread.interrupt();
    }

    // This method for control observer on ARImagesAccess:
    public static void setImagesObserver(boolean state) {
        // Checking:
        if (imagesObserver != null) {
            // Checking:
            if (state) imagesObserver.startWatching();
            else imagesObserver.stopWatching();
        }
    }

    public static void setStatusObserver(boolean state) {
        // Checking:
        if (statusObserver != null) {
            // Checking:
            if (state) statusObserver.startWatching();
            else statusObserver.stopWatching();
        }
    }

    public static void setVideosObserver(boolean state) {
        // Checking:
        if (videosObserver != null) {
            // Checking:
            if (state) videosObserver.startWatching();
            else videosObserver.stopWatching();
        }
    }

    public static void setVoicesObserver(boolean state) {
        // Checking:
        if (voicesObserver != null) {
            // Checking:
            if (state) voicesObserver.startWatching();
            else voicesObserver.stopWatching();
        }
    }

    public static void setDocumentsObserver(boolean state) {
        // Checking:
        if (documentsObserver != null) {
            // Checking:
            if (state) documentsObserver.startWatching();
            else documentsObserver.stopWatching();
        }
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
        // Dismiss:
        dialog.dismiss();
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
        boolean statusState = model.getStatusThread() != null;
        boolean documentsState = model.getDocumentsThread() != null;
        // Checking(&Interrupting):
        if (imagesState) model.getImagesThread().interrupt();
        if (videosState) model.getVideosThread().interrupt();
        if (voicesState) model.getVoicesThread().interrupt();
        if (statusState) model.getStatusThread().interrupt();
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