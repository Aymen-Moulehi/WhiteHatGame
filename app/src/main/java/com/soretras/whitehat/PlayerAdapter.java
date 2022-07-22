package com.soretras.whitehat;

import android.content.Context;
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

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {

    private ArrayList<Player> players ;
    private PlayerViewModel playerViewModel ;
    private Context context ;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setPlayerViewModel(PlayerViewModel playerViewModel) {
        this.playerViewModel = playerViewModel;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PlayerViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_name_item,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        holder.playerName.setText(players.get(position).getName());
        if(position % 2 == 0){
            holder.itemBackground.setBackground(new ColorDrawable(Color.WHITE));
        }

        holder.deletePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerViewModel.deletePlayer(players.get(position),context);
            }
        });
    }

    @Override
    public int getItemCount() {
        return players.size();
    }

    public class PlayerViewHolder extends RecyclerView.ViewHolder {
        View itemBackground ;
        TextView playerName ;
        ImageView deletePlayer ;
        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);

            itemBackground = itemView.findViewById(R.id.itemBackground);
            playerName = itemView.findViewById(R.id.playerName);
            deletePlayer= itemView.findViewById(R.id.delete);

        }
    }
}
