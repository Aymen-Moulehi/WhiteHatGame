package com.soretras.whitehat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class SelectCardAdapter extends BaseAdapter {

    private ArrayList<Card> cards ;
    private Context context ;
    private ArrayList<Player> selectedPlayers ;
    private LayoutInflater inflater ;

    public SelectCardAdapter(ArrayList<Card> cards, ArrayList<Player> selectedPlayers, Context context) {
        this.cards = cards;
        this.context = context;
        this.selectedPlayers = selectedPlayers;
    }

    @Override
    public int getCount() {
        return cards.size();
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

        if(inflater == null) {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;
        }

        if(view == null){
            view = inflater.inflate(R.layout.card_item , null);
        }

        return view;
    }


}
