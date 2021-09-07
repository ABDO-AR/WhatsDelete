package com.ar.team.company.app.whatsdelete.ui.activity.home;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ar.team.company.app.whatsdelete.R;
import com.ar.team.company.app.whatsdelete.control.adapter.PagerAdapter;
import com.ar.team.company.app.whatsdelete.control.preferences.ARPreferencesManager;
import com.ar.team.company.app.whatsdelete.databinding.ActivityHomeBinding;

import com.ar.team.company.app.whatsdelete.ui.activity.applications.ApplicationsActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

@SuppressWarnings("FieldCanBeLocal")
public class HomeActivity extends AppCompatActivity {

    // This For Control The XML-Main Views:
    private ActivityHomeBinding binding;
    private HomeViewModel model;
    // Drawer(&TabLayout):
    private PagerAdapter adapter;
    private ARPreferencesManager manager;
    private ActionBarDrawerToggle drawerToggle;
    // TabMediator:
    private TabLayoutMediator mediator;
    // TAGS:
    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater()); // INFLATE THE LAYOUT.
        View view = binding.getRoot(); // GET ROOT [BY DEF(CONSTRAINT LAYOUT)].
        setContentView(view); // SET THE VIEW CONTENT TO THE (VIEW).
        // Initializing(UI):
        initUI();
        // Initializing(MAIN-FIELDS):
        model = new ViewModelProvider(this).get(HomeViewModel.class);
        // Initializing(MEDIATOR):
        mediator = new TabLayoutMediator(binding.mainContentLayout.homeTabLayout, binding.mainContentLayout.homeViewPager, (tab, position) -> tab.setText(adapter.getHeaders(position)));
        // Initializing(FIELDS):
        manager = new ARPreferencesManager(this);
        adapter = new PagerAdapter(getSupportFragmentManager(), getLifecycle());
        // AttachMediator:
        binding.mainContentLayout.homeViewPager.setAdapter(adapter);
        mediator.attach();
    }

    // Initializing(UserInterface):
    private void initUI() {
        // Setting The New ActionBar:
        setSupportActionBar(binding.mainContentLayout.toolbar);
        // Initializing:
        drawerToggle = setupDrawerToggle();
        // DrawerToggle(Properties):
        drawerToggle.setDrawerIndicatorEnabled(true); // Enabling Drawer Indicator For Animations(InButton).
        drawerToggle.syncState(); // Sync Drawer Toggle State.
        // Developing:
        binding.drawerLayout.addDrawerListener(drawerToggle);
        // Setup DrawerView:
        setupDrawerContent(binding.nvView);
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
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync The Toggle State After OnRestoreInstanceState Has Occurred:
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NotNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass Any Configuration Change To The Drawer Toggles:
        drawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * NOTE: Make Sure You Pass In A Valid Toolbar Reference. ActionBarDrawToggle() Does Not Require It.
     * And Will Not Render The Hamburger Icon Without It.
     */
    private ActionBarDrawerToggle setupDrawerToggle() {
        // Initializing:
        int s1 = R.string.drawer_open;
        int s2 = R.string.drawer_close;
        // Returning:
        return new ActionBarDrawerToggle(this, binding.drawerLayout, binding.mainContentLayout.toolbar, s1, s2);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        // Setup Listeners:
        navigationView.setNavigationItemSelectedListener(this::selectDrawerItem);
    }

    public boolean selectDrawerItem(MenuItem menuItem) {
        // Initializing:
        TabLayout.Tab tab = binding.mainContentLayout.homeTabLayout.getTabAt(0);
        // GetCorrectFragment(ID):
        int itemId = menuItem.getItemId();
        // Checking:
        if (itemId == R.id.nav_chat) {
            tab = binding.mainContentLayout.homeTabLayout.getTabAt(0);
            binding.nvView.setCheckedItem(R.id.nav_chat);
        } else if (itemId == R.id.nav_status) {
            tab = binding.mainContentLayout.homeTabLayout.getTabAt(1);
            binding.nvView.setCheckedItem(R.id.nav_status);
        } else if (itemId == R.id.nav_images) {
            tab = binding.mainContentLayout.homeTabLayout.getTabAt(2);
            binding.nvView.setCheckedItem(R.id.nav_images);
        } else if (itemId == R.id.nav_videos) {
            tab = binding.mainContentLayout.homeTabLayout.getTabAt(3);
            binding.nvView.setCheckedItem(R.id.nav_videos);
        } else if (itemId == R.id.nav_voice) {
            tab = binding.mainContentLayout.homeTabLayout.getTabAt(4);
            binding.nvView.setCheckedItem(R.id.nav_voice);
        } else if (itemId == R.id.nav_documents) {
            tab = binding.mainContentLayout.homeTabLayout.getTabAt(5);
            binding.nvView.setCheckedItem(R.id.nav_documents);
        }
        // Developing:
        Objects.requireNonNull(tab).select();
        // Set ActionBar Title:
        setTitle(menuItem.getTitle());
        // Close The NavigationDrawer:
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        // Returning:
        return true;
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
                Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_store:
                Toast.makeText(this, "Store", Toast.LENGTH_SHORT).show();
                break;
        }
        // Returning:
        return super.onOptionsItemSelected(item);
    }
}