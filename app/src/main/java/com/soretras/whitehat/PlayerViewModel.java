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

public class PlayerViewModel extends ViewModel {
    MutableLiveData<List<Player>> dataMutableLiveData = new MutableLiveData<>();
    private WordDatabase wordDatabase ;



    public void getAllPlayers(Context context){

        Observable<List<Player>> players ;
        wordDatabase = WordDatabase.getInstance(context);
        wordDatabase.playerDao()
                .getAllPlayers()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Player>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull List<Player> players) {
                        dataMutableLiveData.setValue(players);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }

    public void createPlayer(Player player , Context context){
        wordDatabase = WordDatabase.getInstance(context);
        wordDatabase.playerDao()
                .createPlayer(player)
                .subscribeOn(Schedulers.computation())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                       // Toast.makeText(context, "Create new player with success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }


    public void deletePlayer(Player player , Context context){
        wordDatabase = WordDatabase.getInstance(context);
        wordDatabase.playerDao()
                .deletePlayer(player)
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
