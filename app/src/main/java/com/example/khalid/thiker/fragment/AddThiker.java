package com.example.khalid.thiker.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.khalid.thiker.R;
import com.example.khalid.thiker.model.Types;
import com.example.khalid.thiker.utils.DataBase;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.UUID;

import static com.example.khalid.thiker.activity.MainActivity.toolbar;

/**
 * Created by khalid on 11/10/2017.
 */

public class AddThiker extends Fragment {

    Spinner spinner;
    DataBase dataBase;
    ArrayList<Types> types;
    ArrayList<String> strings;
    Button save;
    EditText counter, thiker;
    String type;

    public static AddThiker newInstance() {
        AddThiker fragment = new AddThiker();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_thiker, container, false);
        toolbar.setTitle("إضافة ذكر");
        spinner = view.findViewById(R.id.spinner);
        dataBase = new DataBase(getActivity());
        types = new ArrayList<>();
        strings = new ArrayList<>();
        counter = view.findViewById(R.id.count);
        thiker = view.findViewById(R.id.edit);
        save = view.findViewById(R.id.save);
        if (dataBase.checkTypes()) {
            types = (ArrayList<Types>) dataBase.getTypes();
            for (Types type : types) {
                strings.add(type.getName());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, strings);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);

        } else {
            getTypes();
        }
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uniqueID = UUID.randomUUID().toString().substring(0, 5);
                if (thiker.getText().toString().equals(""))

                    Toast.makeText(getActivity(), "يرجى ملئ حقل الذكر ", Toast.LENGTH_SHORT).show();

                else if (counter.getText().toString().equals(""))
                    Toast.makeText(getActivity(), "يرجى ملئ حقل التكرار ", Toast.LENGTH_SHORT).show();

                else {
                    dataBase.addUserThiker(uniqueID, thiker.getText().toString(), counter.getText().toString(), type, "");
                    Toast.makeText(getActivity(), "تم اضافة الذكر بنجاح ", Toast.LENGTH_SHORT).show();

                }
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = types.get(position).getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
                                strings.add(name);
                                dataBase.addType(id, name, image);
                            }
                            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, strings);
                            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinner.setAdapter(dataAdapter);
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