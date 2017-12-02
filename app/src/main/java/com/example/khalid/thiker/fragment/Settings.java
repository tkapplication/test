package com.example.khalid.thiker.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.khalid.thiker.R;

import static com.example.khalid.thiker.activity.MainActivity.toolbar;

/**
 * Created by khalid on 11/10/2017.
 */

public class Settings extends Fragment {


    public Settings() {
        // Required empty public constructor
    }


    public static Settings newInstance() {
        Settings fragment = new Settings();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings, container, false);
        toolbar.setTitle("الأعدادات");

        return view;
    }


}