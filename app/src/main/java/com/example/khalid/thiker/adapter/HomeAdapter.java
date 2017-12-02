package com.example.khalid.thiker.adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.khalid.thiker.R;
import com.example.khalid.thiker.model.Wall;
import com.example.khalid.thiker.utils.DataBase;

import java.util.ArrayList;

import static com.example.khalid.thiker.utils.Utils.copyText;
import static com.example.khalid.thiker.utils.Utils.isNetworkConnected;
import static com.example.khalid.thiker.utils.Utils.shareText;

/**
 * Created by khalid-vibes on 5/29/2017.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private Context context;
    ArrayList<Wall> list;
    DataBase dataBase;
    String location = "";

    public HomeAdapter(Context context, ArrayList<Wall> list, String location) {
        this.list = list;
        this.context = context;
      //  Collections.reverse(list);
        dataBase = new DataBase(context);

        this.location = location;
    }

    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_item, viewGroup, false);
        return new HomeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.textView.setText(list.get(position).getText());
        if (dataBase.checkThiker(list.get(position).getId())) {
            holder.bookmark.setVisibility(View.VISIBLE);
            holder.border.setVisibility(View.GONE);

        } else {
            holder.border.setVisibility(View.VISIBLE);
            holder.bookmark.setVisibility(View.GONE);

        }
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView border, bookmark, copy, share, report;

        public ViewHolder(View view) {

            super(view);
            textView = (TextView) view.findViewById(R.id.text);
            border = (ImageView) view.findViewById(R.id.border_bookmark);
            bookmark = (ImageView) view.findViewById(R.id.bookmark);
            copy = (ImageView) view.findViewById(R.id.copy);
            share = (ImageView) view.findViewById(R.id.share);
            report = (ImageView) view.findViewById(R.id.report);
            copy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    copyText(context, list.get(getAdapterPosition()).getText());
                }
            });
            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shareText(context, list.get(getAdapterPosition()).getText());
                }
            });

            border.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    border.setVisibility(View.GONE);
                    bookmark.setVisibility(View.VISIBLE);
                    dataBase.addThiker(list.get(getAdapterPosition()).getId(), list.get(getAdapterPosition()).getText(),
                            list.get(getAdapterPosition()).getToken()
                    );
                }
            });

            bookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    border.setVisibility(View.VISIBLE);
                    bookmark.setVisibility(View.GONE);
                    dataBase.deleteThiker(list.get(getAdapterPosition()).getId());
                    if (location.equals("saved")) {
                        list.remove(getAdapterPosition());
                        notifyDataSetChanged();
                    }

                }
            });

            report.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isNetworkConnected(context))
                    reportDialog(list.get(getAdapterPosition()).getToken());
                    else
                        Toast.makeText(context,"لا يوجد اتصال بالشبكة",Toast.LENGTH_SHORT).show();
                }
            });
        }


    }

    private void reportDialog(final String token) {

        final Dialog dialog = new Dialog(context); // Context, this, etc.
        dialog.setContentView(R.layout.report_dialog);
        final Button add, cancel;
        add = (Button) dialog.findViewById(R.id.add);
        cancel = (Button) dialog.findViewById(R.id.cancel);
        final EditText editText = (EditText) dialog.findViewById(R.id.addedit);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendReport(token, editText.getText().toString());
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


    public void sendReport(String token, String subject) {
        String url = "http://ro7.xyz/sendreport.php?token=" + token +
                "&subject=" + subject;

        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, "تم ارسال الملاحظة", Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        Volley.newRequestQueue(context).add(stringRequest2).
                setRetryPolicy(new DefaultRetryPolicy(
                        10000,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }


}

