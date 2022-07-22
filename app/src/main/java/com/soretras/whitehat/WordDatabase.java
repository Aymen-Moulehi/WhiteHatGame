package com.soretras.whitehat;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

@androidx.room.Database(entities ={Word.class , Player.class},version = 1,exportSchema = false)
public abstract class WordDatabase  extends RoomDatabase {

    private static  WordDatabase instance ;
    public abstract WordDao wordDao();
    public  abstract  PlayerDao playerDao();

    public static synchronized WordDatabase getInstance(Context context) {
        if(instance == null){
            instance = Room.databaseBuilder(context , WordDatabase.class , "database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
