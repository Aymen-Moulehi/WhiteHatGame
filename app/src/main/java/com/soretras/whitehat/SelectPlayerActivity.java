package com.soretras.whitehat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.soretras.whitehat.databinding.ActivitySelectPlayerBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SelectPlayerActivity extends AppCompatActivity {

    private ActivitySelectPlayerBinding binding;
    private ArrayList<Player> players = new ArrayList<>() ;
    private ArrayList<Word> words ;
    private int whiteHatPlayerNumber = 1;
    private int blackHatPlayerNumber = 1;
    private RecyclerView recyclerView ;
    private SelectPlayerAdapter adapter ;
    PlayerViewModel playerViewModel ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_player);
        binding = DataBindingUtil.setContentView(this , R.layout.activity_select_player);

        //loading data from data base
        DataApp dataApp = new DataApp() ;
        words = dataApp.getWords() ;


        getAllPlayers();

        recyclerView = binding.playerRecyclerView ;
        adapter = new SelectPlayerAdapter();
        recyclerView.setAdapter(adapter);
        adapter.setPlayers(players);
        adapter.notifyDataSetChanged();

        //plus one white hat
        binding.upWhiteHat.setOnClickListener(view -> addOneWhiteHatPlayer());
        //minus one white hat
        binding.downWhiteHat.setOnClickListener(view -> minusOneWhiteHatPlayer());
        //plus one black hat
        binding.upBlackHat.setOnClickListener(view -> addOneBlackHatPlayer());
        //minus one black hat
        binding.downBlackHat.setOnClickListener(view -> minusOneBlackHatPlayer());
        //to the game
        binding.go.setOnClickListener(view -> passSelectedPlayers());

    }

    private void addOneWhiteHatPlayer() {
        whiteHatPlayerNumber += 1 ;
        binding.whiteHatValue.setText(String.valueOf(whiteHatPlayerNumber));
    }

    private void minusOneWhiteHatPlayer() {
        if(whiteHatPlayerNumber > 1)
            whiteHatPlayerNumber -= 1 ;
        binding.whiteHatValue.setText(String.valueOf(whiteHatPlayerNumber));
    }

    private void addOneBlackHatPlayer() {
        blackHatPlayerNumber += 1 ;
        binding.blackHatValue.setText(String.valueOf( blackHatPlayerNumber));
    }

    private void minusOneBlackHatPlayer() {
        if( blackHatPlayerNumber > 1)
            blackHatPlayerNumber -= 1 ;
        binding.blackHatValue.setText(String.valueOf(blackHatPlayerNumber));
    }

    private void passSelectedPlayers(){
        Intent intent = new Intent( getApplicationContext() , SelectCardActivity.class);
        Bundle args = new Bundle();
        args.putSerializable("ARRAYLIST_PLAYERS",(Serializable)adapter.getSelectedPlayers());
        intent.putExtra("BUNDLE",args);
        intent.putExtra("whiteHatPlayerNumber" , whiteHatPlayerNumber );
        intent.putExtra("blackHatPlayerNumber" , blackHatPlayerNumber );
        startActivity(intent);
        finish();
    }


    public void getAllPlayers(){
        playerViewModel = ViewModelProviders.of(this).get(PlayerViewModel.class);
        playerViewModel.getAllPlayers(getApplicationContext());
        playerViewModel.dataMutableLiveData.observe(this, new Observer<List<Player>>() {
            @Override
            public void onChanged(List<Player> players) {
                adapter.setPlayers((ArrayList<Player>) players);
                adapter.notifyDataSetChanged();
            }
        });
    }





}