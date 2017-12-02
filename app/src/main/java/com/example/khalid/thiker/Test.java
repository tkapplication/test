package com.example.khalid.thiker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.amitshekhar.DebugDB;
import com.example.khalid.thiker.model.ThikerModel;
import com.example.khalid.thiker.utils.DataBase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Test extends AppCompatActivity {
    DataBase dataBase;
    private DatabaseReference mDatabase;
    final List<ThikerModel> questionList = new ArrayList<>();
    private RecyclerViewAdapter mAdapter;
    private int currentPage = 1;
    private RecyclerView recyclerView;
    View progress;

    ArrayList<ThikerModel> thikerModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mAdapter = new RecyclerViewAdapter(questionList);
        LinearLayoutManager mLayoutManager =
                new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        progress = (View) findViewById(R.id.progress);
        move(progress);
        dataBase = new DataBase(this);
        DebugDB.getAddressLog();
        thikerModels = (ArrayList<ThikerModel>) dataBase.getMessage();


        progress.setVisibility(View.VISIBLE);
        move(progress);    }



    public void move(View view) {
        View image = (View) findViewById(R.id.progress);
        Animation animation1 =
                AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);

        image.startAnimation(animation1);
    }


}