package com.example.khalid.thiker.activity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.khalid.thiker.R;
import com.example.khalid.thiker.fragment.AddThiker;
import com.example.khalid.thiker.fragment.AddedAthkar;
import com.example.khalid.thiker.fragment.AthkarMuslim;
import com.example.khalid.thiker.fragment.Home;
import com.example.khalid.thiker.fragment.SavedThiker;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    public static Toolbar toolbar;
    FragmentManager fragmentManager;
    FrameLayout content;

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


        } else if (id == R.id.added_thiker) {
            final FragmentTransaction transaction4 = fragmentManager.beginTransaction().addToBackStack("notification");
            transaction4.replace(R.id.content, AddedAthkar.newInstance()).commit();
            toolbar.setTitle("الأذكار المضافة");
        } else if (id == R.id.saved_thiker) {
            final FragmentTransaction transaction4 = fragmentManager.beginTransaction().addToBackStack("notification");
            transaction4.replace(R.id.content, SavedThiker.newInstance()).commit();
            toolbar.setTitle("الأذكار المحفوظة");

        } else if (id == R.id.settings) {
            final FragmentTransaction transaction4 = fragmentManager.beginTransaction().addToBackStack("notification");
            transaction4.replace(R.id.content, AthkarMuslim.newInstance()).commit();
            toolbar.setTitle("الأعدادات");

        } else if (id == R.id.share) {
            showDialog();

        } else if (id == R.id.rate) {
            rateApp();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(Gravity.RIGHT);
        return true;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    private void rateApp() {

        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
        }
    }

    public void showDialog() {
        LayoutInflater factory = LayoutInflater.from(this);
        final View deleteDialogView = factory.inflate(R.layout.share_dialog, null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();
        deleteDialog.setView(deleteDialogView);

        Button share, twitter, whatsapp;
        twitter = deleteDialogView.findViewById(R.id.twitter);
        whatsapp = deleteDialogView.findViewById(R.id.whatsapp);

        share = deleteDialogView.findViewById(R.id.share);

        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                whatsappShare();
            }
        });
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                twitterShare();
            }
        });

        deleteDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        deleteDialog.show();

    }

    private void whatsappShare() {
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, "The text you wanted to share");
        try {
            startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "عذرا التطبيق غير موجود على الجهاز", Toast.LENGTH_LONG).show();
        }
    }


    private void twitterShare() {
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.twitter.android");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, "The text you wanted to share");
        try {
            startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "عذرا التطبيق غير موجود على الجهاز", Toast.LENGTH_LONG).show();
        }
    }
}
