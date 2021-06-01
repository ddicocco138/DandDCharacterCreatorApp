package com.example.dicoccoandroidfinaldnd;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class logAdapter extends RecyclerView.Adapter<logAdapter.MyViewHolder> {

    public List<Log> logList;

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView logDate;
        public TextView logItem;
        public TextView logStatBonus;
        public TextView logComment;

        public MyViewHolder(View view) {
            super(view);
            logDate =  view.findViewById(R.id.logRecyclerDate);
            logItem = view.findViewById(R.id.logRecyclerItem);
            logStatBonus = view.findViewById(R.id.logRecyclerStatBonus);
            logComment = view.findViewById(R.id.logRecyclerComment);

        }
    }

    public logAdapter(List<Log> logList) {
        this.logList = logList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_log_recyclerview_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log log = logList.get(position);
        String logDate = log.getDate();
        String logItem = log.getItems();
        String logStatBonus = log.getStatBonus();
        String logComment = log.getComments();
        holder.logDate.setText(logDate);
        holder.logItem.setText(logItem);
        holder.logStatBonus.setText(logStatBonus);
        holder.logComment.setText(logComment);

    }

    @Override
    public int getItemCount() {
        return logList.size();
    }

    public void setData(List<Log> logList) {
        this.logList = logList;
        notifyDataSetChanged();
    }

}


