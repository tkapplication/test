package com.example.khalid.thiker.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    TextView nothiker;

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
        nothiker = view.findViewById(R.id.nothiker);
        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        arrayList = new ArrayList<>();
        dataBase = new DataBase(getActivity());
        arrayList = (ArrayList<Wall>) dataBase.getSavedAthkar();
        recyclerView.setAdapter(new HomeAdapter(getActivity(), arrayList, "saved"));
        toolbar.setTitle("الأذكار المحفوظة");
        if (arrayList.size() == 0)
            nothiker.setVisibility(View.VISIBLE);

        return view;
    }


}