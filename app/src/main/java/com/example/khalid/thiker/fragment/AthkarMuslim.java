package com.example.khalid.thiker.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.khalid.thiker.R;
import com.example.khalid.thiker.adapter.TypesAdapter;
import com.example.khalid.thiker.model.Types;
import com.example.khalid.thiker.utils.DataBase;
import com.example.khalid.thiker.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import static com.example.khalid.thiker.activity.MainActivity.toolbar;


public class AthkarMuslim extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Types> types;
    ProgressBar progressBar;
    DataBase dataBase;

    public static AthkarMuslim newInstance() {
        AthkarMuslim fragment = new AthkarMuslim();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_athkar, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        dataBase = new DataBase(getActivity());
        toolbar.setTitle(" أذكار المسلم");
        types = new ArrayList<>();
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        if (Utils.isNetworkConnected(getActivity())) {
            getTypes();
        } else {
            progressBar.setVisibility(View.GONE);
            types = (ArrayList<Types>) dataBase.getTypes();
            recyclerView.setAdapter(new TypesAdapter(getActivity(), types));
        }
        return view;
    }

    public void getTypes() {
        String url = "http://ro7.xyz/gettypes.php";

        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        String id, name, image;
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {

                                id = jsonArray.getJSONObject(i).getString("id");
                                name = jsonArray.getJSONObject(i).getString("name");
                                image = jsonArray.getJSONObject(i).getString("image");
                                types.add(new Types(id, name, image));
                                dataBase.addType(id, name, image);
                            }
                            recyclerView.setAdapter(new TypesAdapter(getActivity(), types));
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

}
