package com.ar.team.company.app.whatsdelete.ui.activity.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ar.team.company.app.whatsdelete.R;
import com.ar.team.company.app.whatsdelete.control.adapter.HomeItemsAdapter;
import com.ar.team.company.app.whatsdelete.control.adapter.PagerAdapter;
import com.ar.team.company.app.whatsdelete.control.preferences.ARPreferencesManager;

import com.ar.team.company.app.whatsdelete.databinding.ActivityHomeBinding;
import com.ar.team.company.app.whatsdelete.ui.activity.applications.ApplicationsActivity;
import com.ar.team.company.app.whatsdelete.ui.activity.settings.SettingsActivity;
import com.ar.team.company.app.whatsdelete.ui.interfaces.HomeItemClickListener;
import com.ar.team.company.app.whatsdelete.ar.utils.ARUtils;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;

import java.util.Objects;

@SuppressWarnings("FieldCanBeLocal")
public class HomeActivity extends AppCompatActivity implements HomeItemClickListener {

    // This For Control The XML-Main Views:
    private ActivityHomeBinding binding;
    private HomeViewModel model;
    // Drawer(&TabLayout):
    private PagerAdapter adapter;
    private ARPreferencesManager manager;
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
        // OpeningDrawer:
        binding.mainContentLayout.btnDrawer.setOnClickListener(view1 -> binding.drawerLayout.openMenu(true));
        // SettingMenuSize:
        settingSize();
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