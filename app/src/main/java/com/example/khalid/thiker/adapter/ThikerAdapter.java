package com.example.khalid.thiker.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.khalid.thiker.R;
import com.example.khalid.thiker.model.ThikerModel;
import com.example.khalid.thiker.utils.DataBase;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by khalid on 12/1/2017.
 */

public class ThikerAdapter extends RecyclerView.Adapter<ThikerAdapter.ViewHolder> {
    ArrayList<ThikerModel> list;
    DataBase dataBase;
    int c;
    private Context context;

    public ThikerAdapter(Context context, ArrayList<ThikerModel> list) {
        this.list = list;
        this.context = context;
        Collections.reverse(list);
        dataBase = new DataBase(context);
    }

    @Override
    public ThikerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.thiker_item, viewGroup, false);
        return new ThikerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        if (!list.get(position).getFadel().equals(null))
            holder.fadel.setText(list.get(position).getFadel());

        holder.name.setText(list.get(position).getText());
        holder.count.setText(list.get(position).getCount());

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, count, fadel;
        CardView cardView;

        public ViewHolder(View view) {
            super(view);

            name = view.findViewById(R.id.text);
            count = view.findViewById(R.id.count);
            fadel = view.findViewById(R.id.fadel);
            cardView = view.findViewById(R.id.card_view);


            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    c = Integer.parseInt(count.getText().toString());
                    c--;
                    if (c == 0) {
                        list.remove(getAdapterPosition());
                        notifyDataSetChanged();
                    } else {
                        list.get(getAdapterPosition()).setCount(c + "");
                        count.setText(c + "");
                    }

                }

        });
    }
}
}

