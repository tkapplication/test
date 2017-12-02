package com.example.khalid.thiker.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.khalid.thiker.R;
import com.example.khalid.thiker.activity.ThikerPage;
import com.example.khalid.thiker.model.Types;
import com.example.khalid.thiker.utils.DataBase;

import java.util.ArrayList;

/**
 * Created by khalid on 11/30/2017.
 */

public class TypesAdapter extends RecyclerView.Adapter<TypesAdapter.ViewHolder> {
    private Context context;
    ArrayList<Types> list;
    DataBase dataBase;

    public TypesAdapter(Context context, ArrayList<Types> list) {
        this.list = list;
        this.context = context;
       // Collections.reverse(list);
        dataBase = new DataBase(context);

    }

    @Override
    public TypesAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.types_item, viewGroup, false);
        return new TypesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.linearLayout.setBackgroundColor(Color.parseColor(list.get(position).getImage()));
        holder.textView.setText(list.get(position).getName());

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        LinearLayout linearLayout;

        public ViewHolder(View view) {

            super(view);
            textView = (TextView) view.findViewById(R.id.name);
            linearLayout = (LinearLayout) view.findViewById(R.id.linear);
            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ThikerPage.class);
                    intent.putExtra("type", list.get(getAdapterPosition()).getName());
                    intent.putExtra("color", list.get(getAdapterPosition()).getImage());

                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });
        }
    }
}
