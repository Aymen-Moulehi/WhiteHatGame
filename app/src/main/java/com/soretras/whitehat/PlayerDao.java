package com.soretras.whitehat;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

@Dao
public interface PlayerDao {


    @Insert
    Completable createPlayer(Player player);

    @Delete
    Completable deletePlayer(Player player);

    // void updateWord(Word word);

    @Query("select * from player")
    Observable<List<Player>> getAllPlayers();



}
