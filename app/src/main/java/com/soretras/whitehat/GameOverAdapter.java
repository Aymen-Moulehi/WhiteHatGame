package com.soretras.whitehat;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class GameOverAdapter extends RecyclerView.Adapter<GameOverAdapter.GameOverHolder> {

    ArrayList<Player> players ;

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GameOverHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new  GameOverAdapter.GameOverHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_end_item,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull GameOverHolder holder, int position) {
        holder.playerName.setText(players.get(position).getName());

        if(players.get(position).getCard().getType() == Card.WHITE_HAT){
            holder.whiteHat.setVisibility(View.VISIBLE);
        }

        if(players.get(position).getCard().getType() == Card.BLACK_HAT){
            holder.blackHat.setVisibility(View.VISIBLE);
        }

        if(position % 2 == 0){
            holder.itemBackground.setBackground(new ColorDrawable(Color.WHITE));
        }
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public class GameOverHolder extends RecyclerView.ViewHolder {
        TextView playerName ;
        ImageView whiteHat ;
        ImageView blackHat ;
        View itemBackground ;
        public GameOverHolder(@NonNull View itemView) {
            super(itemView);
            playerName = itemView.findViewById(R.id.playerName);
            whiteHat = itemView.findViewById(R.id.whiteHat) ;
            blackHat = itemView.findViewById(R.id.blackHat);
            itemBackground = itemView.findViewById(R.id.itemBackground);
        }
    }
}
