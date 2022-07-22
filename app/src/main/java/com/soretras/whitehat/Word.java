package com.soretras.whitehat;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Random;

@Entity(tableName = "word")
public class Word {

    @PrimaryKey(autoGenerate = true)
    private int id ;
    private String firstWord ;
    private String secondWord ;

    // you can use @Ignore if you don't went to use specific attribute


    public Word(String firstWord, String secondWord) {
        this.firstWord = firstWord;
        this.secondWord = secondWord;
    }

    public int getId() {
        return id;
    }

    public String getFirstWord() {
        return firstWord;
    }

    public String getSecondWord() {
        return secondWord;
    }

    // setter use it by room
    public void setId(int id) {
        this.id = id;
    }

    public void randomWordOrderChange(){
        String middle ;
        Random random = new Random();
        if(random.nextBoolean()){
            middle= firstWord ;
            firstWord = secondWord ;
            secondWord = middle ;
        }
    }
}
