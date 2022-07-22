package com.soretras.whitehat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.soretras.whitehat.databinding.ActivityGameOverBinding;
import com.soretras.whitehat.databinding.ActivityGameTableBinding;

import java.util.ArrayList;

public class GameOverActivity extends AppCompatActivity {

    private ArrayList<Player> selectedPlayers ;
    private String word ;
    private ActivityGameOverBinding binding ;
    private GameOverAdapter adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_game_over);


        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        selectedPlayers = (ArrayList<Player>) args.getSerializable("ARRAYLIST_PLAYERS");
        word = intent.getStringExtra("word");


        adapter = new GameOverAdapter();
        adapter.setPlayers(selectedPlayers);
        binding.playerRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}