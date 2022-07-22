package com.soretras.whitehat;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SelectPlayerAdapter extends RecyclerView.Adapter<SelectPlayerAdapter.SelectPlayerViewHolder> {

    private ArrayList<Player> players = new ArrayList<>() ;
    private ArrayList<Player> selectedPlayers = new ArrayList<>() ;


    @NonNull
    @Override
    public SelectPlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SelectPlayerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.select_player_item,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SelectPlayerViewHolder holder, int position) {
        holder.playerName.setText(players.get(position).getName());

        holder.checkBox.setOnClickListener(view -> {
            if(holder.checkBox.isChecked()){
                selectedPlayers.add(players.get(position));
            }else{
                selectedPlayers.remove(players.get(position));
            }
        });

        if(position % 2 == 0){
            holder.itemBackground.setBackground(new ColorDrawable(Color.WHITE));
        }

    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
        notifyDataSetChanged();
    }

    public ArrayList<Player> getSelectedPlayers() {
        return selectedPlayers;
    }

    public class SelectPlayerViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkBox ;
        private TextView playerName ;
        private View itemBackground ;
        public SelectPlayerViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.checkBox);
            playerName = itemView.findViewById(R.id.playerName);
            itemBackground = itemView.findViewById(R.id.itemBackground);
        }
    }



}
