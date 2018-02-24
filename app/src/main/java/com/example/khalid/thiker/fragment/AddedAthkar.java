package com.example.khalid.thiker.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.khalid.thiker.R;
import com.example.khalid.thiker.adapter.UserThikerAdapter;
import com.example.khalid.thiker.model.ThikerModel;
import com.example.khalid.thiker.utils.DataBase;

import java.util.ArrayList;

/**
 * Created by khalid on 12/9/2017.
 */

public class AddedAthkar extends Fragment {
    TextView nothiker;
    ArrayList<ThikerModel> thikerModels;
    DataBase dataBase;
    RecyclerView recyclerView;

    public static AddedAthkar newInstance() {
        AddedAthkar fragment = new AddedAthkar();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.added_fragment, container, false);
        dataBase = new DataBase(getActivity());
        thikerModels = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        thikerModels = (ArrayList<ThikerModel>) dataBase.getAllOfflineThiker();
        recyclerView.setAdapter(new UserThikerAdapter(getActivity(), thikerModels));
        nothiker = view.findViewById(R.id.nothiker);
        if (thikerModels.size() == 0)
            nothiker.setVisibility(View.VISIBLE);
        return view;
    }


}
