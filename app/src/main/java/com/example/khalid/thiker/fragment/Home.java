package com.example.khalid.thiker.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.amitshekhar.DebugDB;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.khalid.thiker.EndlessRecyclerOnScrollListener;
import com.example.khalid.thiker.R;
import com.example.khalid.thiker.adapter.HomeAdapter;
import com.example.khalid.thiker.model.Wall;
import com.example.khalid.thiker.utils.DataBase;
import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.UUID;

import static com.example.khalid.thiker.activity.MainActivity.toolbar;
import static com.example.khalid.thiker.utils.Utils.get;
import static com.example.khalid.thiker.utils.Utils.getbool;
import static com.example.khalid.thiker.utils.Utils.isNetworkConnected;
import static com.example.khalid.thiker.utils.Utils.save;
import static com.example.khalid.thiker.utils.Utils.savebool;

/**
 * Created by khalid on 11/11/2017.
 */

public class Home extends Fragment {

    ArrayList<Wall> arrayList;
    FloatingActionButton add;
    DataBase dataBase;
    private int currentPage = 1;
    private RecyclerView recyclerView;
    HomeAdapter homeAdapter;
    DilatingDotsProgressBar dilatingDotsProgressBar;
    ProgressBar progressBar;

    public Home() {
    }


    public static Home newInstance() {
        Home fragment = new Home();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.home, container, false);
        String uniqueID = UUID.randomUUID().toString();
        if (!getbool(getActivity(), "gettoken")) {

            savebool(getActivity(), "gettoken", true);
            save(getActivity(), "token", uniqueID);
        }
        DebugDB.getAddressLog();
        toolbar.setTitle("الرئيسية");
        dataBase = new DataBase(getActivity());
        add = (FloatingActionButton) view.findViewById(R.id.add);
        arrayList = new ArrayList<>();
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        LinearLayoutManager mLayoutManager =
                new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        if (!isNetworkConnected(getActivity())) {
            progressBar.setVisibility(View.GONE);
            arrayList = (ArrayList<Wall>) dataBase.getWall();
            recyclerView.setAdapter(new HomeAdapter(getActivity(), arrayList, "home"));

        } else {
            homeAdapter = new HomeAdapter(getActivity(), arrayList, "");
            recyclerView.setAdapter(homeAdapter);
            dilatingDotsProgressBar = (DilatingDotsProgressBar) view.findViewById(R.id.progress);

            recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(mLayoutManager) {
                @Override
                public void onLoadMore(int current) {
                    currentPage++;
                    dilatingDotsProgressBar.show();
                    getResult(currentPage);
                }

            });
            getResult(currentPage);

        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkConnected(getActivity()))
                    addDialog();
                else
                    Toast.makeText(getActivity(), "لا يوجد اتصال بالشبكة", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public void getResult(int page) {
        String url = "http://ro7.xyz/getwall.php?page=" + page;

        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String id, text, token;
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {

                                id = jsonArray.getJSONObject(i).getString("id");
                                text = jsonArray.getJSONObject(i).getString("text");
                                token = jsonArray.getJSONObject(i).getString("token");
                                arrayList.add(new Wall(id, text, token));
                                dataBase.addWall(id, text, token);
                            }

                            dilatingDotsProgressBar.hide();
                            homeAdapter.notifyDataSetChanged();
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
        Volley.newRequestQueue(getActivity()).add(stringRequest2).
                setRetryPolicy(new DefaultRetryPolicy(
                        10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    private void addDialog() {

        final Dialog dialog = new Dialog(getActivity()); // Context, this, etc.
        dialog.setContentView(R.layout.add_dialog);
        final Button add, cancel;
        add = (Button) dialog.findViewById(R.id.add);
        cancel = (Button) dialog.findViewById(R.id.cancel);
        final EditText editText = (EditText) dialog.findViewById(R.id.addedit);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addWall(editText.getText().toString(), get(getActivity(), "token"));
                dialog.cancel();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }


    public void addWall(String text, String token) {
        String url = "http://ro7.xyz/addwall.php?text=" + text.replaceAll("\\s+", "%20") +

                "&token=" + token;

        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getActivity(), "تمت الإضافة", Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        Volley.newRequestQueue(getActivity()).add(stringRequest2).
                setRetryPolicy(new DefaultRetryPolicy(
                        10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

}