package com.example.khalid.thiker.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.khalid.thiker.R;
import com.example.khalid.thiker.adapter.ThikerAdapter;
import com.example.khalid.thiker.model.ThikerModel;
import com.example.khalid.thiker.utils.DataBase;
import com.example.khalid.thiker.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class ThikerPage extends AppCompatActivity {
    String type = "", image = "1", name = "";
    ArrayList<ThikerModel> thikerModels, offline;
    RecyclerView recyclerView;
    TextView textView;
    ProgressBar progressBar;
    DataBase dataBase;
    LinearLayout noConnection;
    ImageView retry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Cairo.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        setContentView(R.layout.activity_thiker);
        retry = (ImageView) findViewById(R.id.retry);
        noConnection = (LinearLayout) findViewById(R.id.noconnect);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        type = getIntent().getExtras().getString("id");
        thikerModels = new ArrayList<>();
        offline = new ArrayList<>();
        dataBase = new DataBase(getApplicationContext());
        thikerModels = (ArrayList<ThikerModel>) dataBase.getThiker(type);

        textView = (TextView) findViewById(R.id.header);
        image = getIntent().getExtras().getString("color");
        name = getIntent().getExtras().getString("name");
        textView.setText(name);
        offline = (ArrayList<ThikerModel>) dataBase.getOfflineThiker(type);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textView.setBackgroundColor(Color.parseColor(image));

        if (!Utils.isNetworkConnected(getApplicationContext()) && thikerModels.size() == 0) {
            noConnection.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);

        } else if (Utils.isNetworkConnected(getApplicationContext()))
            getThiker(type);

        else {
            progressBar.setVisibility(View.GONE);
            thikerModels = (ArrayList<ThikerModel>) dataBase.getThiker(type);
            thikerModels.addAll(offline);

            recyclerView.setAdapter(new ThikerAdapter(getApplicationContext(), thikerModels));

        }
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utils.isNetworkConnected(getApplicationContext())) {
                    getThiker(type);
                    noConnection.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);

                }
            }
        });
    }

    public void getThiker(String type) {
        thikerModels = new ArrayList<>();
        type = type.replaceAll("\\s", "%20");
        String url = "http://ro7.xyz/getthiker.php?type=" + type;

        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String id, text, count, type, fadel;
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {

                                id = jsonArray.getJSONObject(i).getString("id");
                                text = jsonArray.getJSONObject(i).getString("text");
                                count = jsonArray.getJSONObject(i).getString("count");
                                type = jsonArray.getJSONObject(i).getString("type");
                                fadel = jsonArray.getJSONObject(i).getString("fadel");

                                thikerModels.add(new ThikerModel(id, text, count, type, fadel));
                                dataBase.addThikerPage(id, text, count, type, fadel);
                            }
                            thikerModels.addAll(offline);

                            recyclerView.setAdapter(new ThikerAdapter(getApplicationContext(), thikerModels));
                            progressBar.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        Volley.newRequestQueue(getApplicationContext()).add(stringRequest2).
                setRetryPolicy(new DefaultRetryPolicy(
                        10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
