package com.example.khalid.thiker.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.example.khalid.thiker.fragment.AddThiker;
import com.example.khalid.thiker.fragment.AthkarMuslim;
import com.example.khalid.thiker.fragment.Home;
import com.example.khalid.thiker.R;
import com.example.khalid.thiker.fragment.SavedThiker;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    FragmentManager fragmentManager;
    FrameLayout content;
    public static Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Cairo.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        setContentView(R.layout.activity_main);
         toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fragmentManager = getSupportFragmentManager();
        getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        content = (FrameLayout) findViewById(R.id.content);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.home);
        final FragmentTransaction transaction4 = fragmentManager.beginTransaction().addToBackStack("home");
        transaction4.replace(R.id.content, Home.newInstance()).commit();
        getSupportActionBar().setTitle("الرئيسية");

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(Gravity.RIGHT)) {
            drawer.closeDrawer(Gravity.RIGHT);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.athkar) {
            final FragmentTransaction transaction4 = fragmentManager.beginTransaction().addToBackStack("notification");
            transaction4.replace(R.id.content, AthkarMuslim.newInstance()).commit();
            toolbar.setTitle(" أذكار المسلم");

        } else if (id == R.id.add_thiker) {
            final FragmentTransaction transaction4 = fragmentManager.beginTransaction().addToBackStack("notification");
            transaction4.replace(R.id.content, AddThiker.newInstance()).commit();
            toolbar.setTitle("إضافة ذكر");

        } else if (id == R.id.home) {
            final FragmentTransaction transaction4 = fragmentManager.beginTransaction().addToBackStack("notification");
            transaction4.replace(R.id.content, Home.newInstance()).commit();
            toolbar.setTitle("الرئيسية");


        } else if (id == R.id.saved_thiker) {
            final FragmentTransaction transaction4 = fragmentManager.beginTransaction().addToBackStack("notification");
            transaction4.replace(R.id.content, SavedThiker.newInstance()).commit();
            toolbar.setTitle("الأذكار المحفوظة");

        } else if (id == R.id.settings) {
            final FragmentTransaction transaction4 = fragmentManager.beginTransaction().addToBackStack("notification");
            transaction4.replace(R.id.content, AthkarMuslim.newInstance()).commit();
            toolbar.setTitle("الأعدادات");

        } else if (id == R.id.share) {
            final FragmentTransaction transaction4 = fragmentManager.beginTransaction().addToBackStack("notification");
            transaction4.replace(R.id.content, AthkarMuslim.newInstance()).commit();

        } else if (id == R.id.rate) {
            final FragmentTransaction transaction4 = fragmentManager.beginTransaction().addToBackStack("notification");
            transaction4.replace(R.id.content, AthkarMuslim.newInstance()).commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(Gravity.RIGHT);
        return true;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
