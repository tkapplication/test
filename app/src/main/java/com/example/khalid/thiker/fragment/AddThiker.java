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

public class AddThiker extends Fragment {


    public AddThiker() {
        // Required empty public constructor
    }


    public static AddThiker newInstance() {
        AddThiker fragment = new AddThiker();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_thiker, container, false);
        toolbar.setTitle("إضافة ذكر");

        return view;
    }


}