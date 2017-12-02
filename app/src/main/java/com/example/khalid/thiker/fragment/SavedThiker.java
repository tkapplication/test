package com.example.khalid.thiker.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.khalid.thiker.R;
import com.example.khalid.thiker.adapter.HomeAdapter;
import com.example.khalid.thiker.model.Wall;
import com.example.khalid.thiker.utils.DataBase;

import java.util.ArrayList;

import static com.example.khalid.thiker.activity.MainActivity.toolbar;

/**
 * Created by khalid on 11/10/2017.
 */

public class SavedThiker extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Wall> arrayList;
    DataBase dataBase;

    public static SavedThiker newInstance() {
        SavedThiker fragment = new SavedThiker();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.saved_thiker, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        arrayList = new ArrayList<>();
        dataBase = new DataBase(getActivity());
        arrayList= (ArrayList<Wall>) dataBase.getSavedAthkar();
        recyclerView.setAdapter(new HomeAdapter(getActivity(),arrayList,"saved"));
        toolbar.setTitle("الأذكار المحفوظة");

        return view;
    }


}