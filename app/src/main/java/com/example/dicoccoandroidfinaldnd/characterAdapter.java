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

public class characterAdapter extends RecyclerView.Adapter<characterAdapter.MyViewHolder> {


    public String id;
    public List<character> characterList;
    private final ClickListener listener;

    public interface ClickListener {

        void onViewClicked(int position);

        void onEditClicked(int position);

        void onLogClicked(int position);

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewRecyclerName;
        public Button viewBut, editBut, logBut;
        public int position;
        private WeakReference<ClickListener> listenerRef;
        public MyViewHolder(View view, ClickListener listener) {
            super(view);

            listenerRef = new WeakReference<>(listener);
            textViewRecyclerName =  view.findViewById(R.id.logRecyclerDate);
            viewBut = view.findViewById(R.id.buttonRecyclerPDF);
            editBut = view.findViewById(R.id.buttonRecyclerEdit);
            logBut = view.findViewById(R.id.buttonRecyclerLog);

            viewBut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    position = getAdapterPosition();
                    listenerRef.get().onViewClicked(getAdapterPosition());
                }
            });

            editBut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    position = getAdapterPosition();
                    listenerRef.get().onEditClicked(getAdapterPosition());
                }
            });

            logBut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    position = getAdapterPosition();
                    listenerRef.get().onLogClicked(getAdapterPosition());
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

    public characterAdapter(List<character> characterList, ClickListener listener) {
        this.characterList = characterList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout. activity_name_recyclerview_item, parent, false);
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

