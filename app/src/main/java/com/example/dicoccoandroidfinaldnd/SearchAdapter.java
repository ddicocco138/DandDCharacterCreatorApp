package com.example.dicoccoandroidfinaldnd;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {


    public String id;
    public List<character> characterList;
    private final TheClickListener listener;

    public interface TheClickListener {

        void onViewClicked(int position);

        void onAddClicked(int position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewRecyclerName;
        public Button viewBut, addBut;
        public int position;
        private WeakReference<TheClickListener> listenerRef;
        public MyViewHolder(View view, TheClickListener listener) {
            super(view);

            listenerRef = new WeakReference<>(listener);
            textViewRecyclerName =  view.findViewById(R.id.logRecyclerDate);
            viewBut = view.findViewById(R.id.buttonRecyclerPDF);
            addBut = view.findViewById(R.id.buttonRecyclerAdd);

            viewBut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    position = getAdapterPosition();
                    listenerRef.get().onViewClicked(getAdapterPosition());
                }
            });

            addBut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    position = getAdapterPosition();
                    listenerRef.get().onAddClicked(getAdapterPosition());
                }
            });

        }
    }

    public void setCharRef(int pos){
        character chars = characterList.get(pos);
        id = chars.getCharacterID();
    }

    public String getCharRef(){
        return id;

    }

    public SearchAdapter(List<character> characterList, TheClickListener listener) {
        this.characterList = characterList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout. activity_name_searchrecyclerview_item, parent, false);
        return new MyViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        character character = characterList.get(position);
        String characterName = character.getName();

        holder.textViewRecyclerName.setText(characterName);

        id = characterList.get(position).getCharacterID();

    }

    @Override
    public int getItemCount() {
        return characterList.size();
    }

    public void setData(List<character> characterList) {
        this.characterList = characterList;
        notifyDataSetChanged();
    }

}
