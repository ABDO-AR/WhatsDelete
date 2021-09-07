package com.ar.team.company.app.whatsdelete.ui.activity.home;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.ar.team.company.app.whatsdelete.R;
import com.ar.team.company.app.whatsdelete.control.adapter.HomeAdapter;
import com.ar.team.company.app.whatsdelete.control.preferences.ARPreferencesManager;
import com.ar.team.company.app.whatsdelete.databinding.ActivityHomeBinding;

import com.ar.team.company.app.whatsdelete.ui.activity.applications.ApplicationsActivity;
import com.ar.team.company.app.whatsdelete.ui.fragment.home.ChatFragment;
import com.ar.team.company.app.whatsdelete.ui.fragment.home.DocumentFragment;
import com.ar.team.company.app.whatsdelete.ui.fragment.home.ImagesFragment;
import com.ar.team.company.app.whatsdelete.ui.fragment.home.StatusFragment;
import com.ar.team.company.app.whatsdelete.ui.fragment.home.VideosFragment;
import com.ar.team.company.app.whatsdelete.ui.fragment.home.VoiceFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;


public class HomeActivity extends AppCompatActivity {

    // This For Control The XML-Main Views:
    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    private ActivityHomeBinding binding;
    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    private HomeViewModel model;
    // TAGS:
    private HomeAdapter adapter;
    private  ARPreferencesManager  manager;
    private ActionBarDrawerToggle drawerToggle ;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private Toolbar toolbar;

    @SuppressWarnings({"unused", "FieldCanBeLocal"})
    private static final String TAG = "HomeActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater()); // INFLATE THE LAYOUT.
        View view = binding.getRoot(); // GET ROOT [BY DEF(CONSTRAINT LAYOUT)].

        setContentView(view); // SET THE VIEW CONTENT TO THE (VIEW).
        // Initializing:

        viewPager =  findViewById(R.id.home_view_pager);
        tabLayout =  findViewById(R.id.home_tab_layout);
        toolbar =  findViewById(R.id.toolbar);

        //setSupportActionBar(toolbar);


        manager = new ARPreferencesManager(this);
        adapter = new HomeAdapter(getSupportFragmentManager());

        drawerToggle = setupDrawerToggle();
        // Setup toggle to display hamburger icon with nice animation
        drawerToggle.setDrawerIndicatorEnabled(true);
        //drawerToggle.set

        drawerToggle.syncState();

        // Tie DrawerLayout events to the ActionBarToggle
        binding.drawerLayout.addDrawerListener(drawerToggle);





        // Setup drawer view
        setupDrawerContent(binding.nvView);

        adapter.AddFragment(new ChatFragment()," Chat ");
        adapter.AddFragment(new StatusFragment()," Status ");
        adapter.AddFragment(new ImagesFragment()," Images ");
        adapter.AddFragment(new VideosFragment()," Videos ");
        adapter.AddFragment(new VoiceFragment()," Voice ");
        adapter.AddFragment(new DocumentFragment()," Document ");

        //set Adapter
        //  binding2.homeViewPager.setAdapter(adapter);
        viewPager.setAdapter(adapter);

        //connect for Tab layout with viewPager
        tabLayout.setupWithViewPager(viewPager);

        model = new ViewModelProvider(this).get(HomeViewModel.class);



    }

    @Override
    protected void onStart()
    {
        super.onStart();
        // DefState:
        boolean state = manager.getBooleanPreferences(ARPreferencesManager.APP_INIT);
        // Developing:
        if (!state)
            startActivity(new Intent(this, ApplicationsActivity.class));

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        // NOTE: Make sure you pass in a valid toolbar reference.  ActionBarDrawToggle() does not require it
        // and will not render the hamburger icon without it.
        return new ActionBarDrawerToggle(this,  binding.drawerLayout,toolbar,R.string.drawer_open,  R.string.drawer_close);
    }
    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }
    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked

        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_chat:
                fragmentClass = ChatFragment.class;

                break;
            case R.id.nav_status:
                fragmentClass = StatusFragment.class;

                break;
            case R.id.nav_images:
                fragmentClass = ImagesFragment.class;

                break;
            case R.id.nav_videos:
                fragmentClass = VideosFragment.class;

                break;
            case R.id.nav_voice:
                fragmentClass = VoiceFragment.class;

                break;
            case R.id.nav_documents:
                fragmentClass = DocumentFragment.class;
                break;
            default:
                fragmentClass = ChatFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        //    getSupportFragmentManager().beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        binding.drawerLayout.closeDrawers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);

        return true;
    }
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // The action bar home/up action should open or close the drawer.

        drawerToggle.onOptionsItemSelected(item);

        switch (item.getItemId()) {


            case android.R.id.home:
                binding.drawerLayout.openDrawer(GravityCompat.START);
                Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_setting:
                Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();
                break;
        }
        if (drawerToggle.onOptionsItemSelected(item))
        {
            Toast.makeText(this, "done", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}