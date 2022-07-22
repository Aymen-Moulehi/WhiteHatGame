package com.soretras.whitehat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GameTableAdapter extends BaseAdapter {

    private ArrayList<Player> selectedPlayers;
    private LayoutInflater inflater;
    private Context context;

    public GameTableAdapter(ArrayList<Player> selectedPlayers, Context context) {
        this.selectedPlayers = selectedPlayers;
        this.context = context;
    }

    @Override
    public int getCount() {
        return selectedPlayers.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        TextView firstTemper ;
        TextView playerName ;
        TextView playerOrder ;

        if(inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        }

        if(view == null){
            view = inflater.inflate(R.layout.player_item , null);
        }

        firstTemper = view.findViewById(R.id.firstTemper);
        playerName = view.findViewById(R.id.playerName);
        playerOrder = view.findViewById(R.id.playerOrder);

        firstTemper.setText(String.valueOf(selectedPlayers.get(i).getName().charAt(0)));
        playerName.setText(selectedPlayers.get(i).getName());
        playerOrder.setText(String.valueOf(i+1));

        return view;
    }
}
