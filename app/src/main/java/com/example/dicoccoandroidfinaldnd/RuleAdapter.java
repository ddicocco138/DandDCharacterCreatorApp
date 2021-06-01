package com.example.dicoccoandroidfinaldnd;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RuleAdapter extends RecyclerView.Adapter<RuleAdapter.RuleViewHolder> {
    private ArrayList<RuleOutline> rList;
    @NonNull
    @Override
    public RuleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_row, parent, false);
        RuleViewHolder rvh = new RuleViewHolder(v);
        return rvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RuleViewHolder holder, int position) {
        RuleOutline currentItem = rList.get(position);

        holder.rText.setText(currentItem.getrText());

    }

    @Override
    public int getItemCount() {
        return rList.size();
    }

    public static class  RuleViewHolder extends RecyclerView.ViewHolder {
        public TextView rText;

        public RuleViewHolder(@NonNull View itemView) {
            super(itemView);
            rText = itemView.findViewById(R.id.tavernRule);
        }
    }

    public RuleAdapter(ArrayList<RuleOutline> rules){
        rList = rules;
    }

}
