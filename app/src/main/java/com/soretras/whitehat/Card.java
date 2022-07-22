package com.soretras.whitehat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Card implements Serializable {

    public static final int WHITE_HAT = 1 ;
    public static final int BLACK_HAT = 2 ;
    public static final int INNOCENT = 3 ;

    private int type;

    public Card(int type){
        this.type = type ;
    }

    public int getType() {
        return type;
    }

    public static ArrayList<Card> generateCards(int whiteHatPlayerNumber,
                                                int blackHatPlayerNumber,
                                                int totalPlayers){

        ArrayList<Card> cards = new ArrayList<>();

        if(totalPlayers < (whiteHatPlayerNumber + blackHatPlayerNumber + 2))
            return cards ;

        int innocentPlayerNumber = totalPlayers - (whiteHatPlayerNumber + blackHatPlayerNumber) ;

        //add innocent players
        for(int i=0;i<innocentPlayerNumber;i++){
            cards.add(new Card(Card.INNOCENT));
        }

        //add white hat cards
        for(int i=0;i<whiteHatPlayerNumber;i++){
            cards.add(new Card(Card.WHITE_HAT));
        }

        // add black hat cards
        for(int i=0;i<blackHatPlayerNumber;i++){
            cards.add(new Card(Card.BLACK_HAT));
        }

        Collections.shuffle(cards,new Random());

        return cards ;
    }

}
