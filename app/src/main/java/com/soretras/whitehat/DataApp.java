package com.soretras.whitehat;

import java.util.ArrayList;


public class DataApp {
    private ArrayList<Player> players ;
    private ArrayList<Word> words ;


    public DataApp(){
        players = new ArrayList<>() ;
        words = new ArrayList<>() ;

        Player p1 = new Player("Aymen") ;
        p1.setId(1);

        Player p2 = new Player("Ali") ;
        p2.setId(2);

        Player p3 = new Player("Walid") ;
        p3.setId(3);

        Player p4 = new Player("Sohail") ;
        p4.setId(4);

        Player p5 = new Player("jack") ;
        p5.setId(5);

        Player p6 = new Player("Ahmed") ;
        p6.setId(6) ;

        players.add(p1);
        players.add(p2);
        players.add(p3);
        players.add(p4);
        players.add(p5);
        players.add(p6);

        Word w1 = new Word("bus","car") ;
        w1.setId(1);

        Word w2 = new Word("fish","jellyfish") ;
        w2.setId(2);

        Word w3 = new Word("water","ice") ;
        w3.setId(3);

        words.add(w1) ;
        words.add(w2) ;
        words.add(w3) ;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public ArrayList<Word> getWords() {
        return words;
    }
}
