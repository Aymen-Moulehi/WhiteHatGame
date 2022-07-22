package com.soretras.whitehat;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Observable;

@Dao
public interface WordDao {

    @Insert
    Completable createWord(Word word);

    @Delete
    Completable deleteWord(Word word);

    // void updateWord(Word word);

    @Query("select * from word")
    Observable<List<Word>> getAllWords();

}
