package com.soretras.whitehat;

import android.content.Context;
import android.database.Observable;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class WordViewModel extends ViewModel {
    MutableLiveData<List<Word>> dataMutableLiveData = new MutableLiveData<>();
    private WordDatabase wordDatabase ;

    public void getAllWords(Context context){

        Observable<List<Word>> words ;
        wordDatabase = WordDatabase.getInstance(context);
        wordDatabase.wordDao()
                .getAllWords()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Word>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<Word> words) {
                        dataMutableLiveData.setValue(words);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void createWord(Word word , Context context){
        wordDatabase = WordDatabase.getInstance(context);
        wordDatabase.wordDao()
                .createWord(word)
                .subscribeOn(Schedulers.computation())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }


    public void deleteWord(Word word , Context context){
        wordDatabase = WordDatabase.getInstance(context);
        wordDatabase.wordDao()
                .deleteWord(word)
                .subscribeOn(Schedulers.computation())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

    }
}
