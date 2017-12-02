package com.example.khalid.thiker;

/**
 * Created by khalid on 11/16/2017.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.khalid.thiker.model.ThikerModel;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<ThikerModel> mQuestionList;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, mark;

        MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.content);
            mark = (TextView) view.findViewById(R.id.mark);
        }
    }

    RecyclerViewAdapter(List<ThikerModel> mQuestionList) {
        this.mQuestionList = mQuestionList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ThikerModel question = mQuestionList.get(position);
        holder.title.setText(question.getText());
        holder.mark.setText(question.getId());

    }

    @Override
    public int getItemCount() {
        return mQuestionList.size();
    }
}