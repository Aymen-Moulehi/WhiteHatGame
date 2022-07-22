package com.soretras.whitehat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.soretras.whitehat.databinding.ActivityGameTableBinding;
import com.soretras.whitehat.databinding.ActivitySelectPlayerBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GameTableActivity extends AppCompatActivity {

    private ArrayList<Player> selectedPlayers ;
    private ArrayList<Player> selectedPlayersCopy ;
    private int whiteHatPlayerNumber ;
    private int blackHatPlayerNumber ;
    private int totalPlayersNumber ;
    private int innocentPlayerNumber ;
    private boolean isFirstTimeChangeOrder = false ;
    private ActivityGameTableBinding binding;
    private String word ;
    private GameTableAdapter adapter;
    private GridView gridView;
    private View popupKillPlayerView ;
    private PopupWindow popupKillPlayerWindow ;
    private Button yesButton ;
    private Button noButton ;
    private TextView playerToKillName ;
    private int playerToKillId = 0 ;
    private View popupWhiteHatCheckWordView ;
    private PopupWindow popupWhiteHatCheckWordWindow ;
    private Button guessButton ;
    private EditText guessWord ;
    private boolean GuessButtonClicked = false ;
    private boolean whiteHatWin = false ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_table);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_game_table);
        setupKillPlayerPopup();
        setupWhiteHatCheckWordPlayerPopup();


        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        selectedPlayers = (ArrayList<Player>) args.getSerializable("ARRAYLIST_PLAYERS");
        whiteHatPlayerNumber = intent.getIntExtra("whiteHatPlayerNumber" , 1);
        blackHatPlayerNumber = intent.getIntExtra("blackHatPlayerNumber", 1);
        word = intent.getStringExtra("word");
        totalPlayersNumber = selectedPlayers.size() ;
        innocentPlayerNumber = totalPlayersNumber - (whiteHatPlayerNumber + blackHatPlayerNumber);
        selectedPlayersCopy = new ArrayList<>();
        selectedPlayersCopy.addAll(selectedPlayers);


        changeOrderPlayers();

        gridView = binding.gridViewPlayers ;
        adapter = new GameTableAdapter(selectedPlayers, getApplicationContext());
        gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                playerToKillId = i ;
                playerToKillName.setText(selectedPlayers.get(playerToKillId).getName());
                showKillPlayerWindow();
            }
        });

    }

    private Boolean isGameOver(){
        if(innocentPlayerNumber <= 1 || whiteHatPlayerNumber + blackHatPlayerNumber == 0 || whiteHatWin)
            return true ;
        return  false ;
    }

    private void changeOrderPlayers(){
        Random random = new Random();
        int jump = random.nextInt(5 - 1 + 1) + 1;
        Collections.rotate(selectedPlayers, jump);

        if(!isFirstTimeChangeOrder){
            while(selectedPlayers.get(0).getCard().getType() == Card.WHITE_HAT){
                jump = random.nextInt(5 - 1 + 1) + 1;
                Collections.rotate(selectedPlayers, jump);
            }
            isFirstTimeChangeOrder = true ;
        }

    }

    @SuppressLint("InflateParams")
    public void setupKillPlayerPopup(){
        LayoutInflater inflater = (LayoutInflater)  getSystemService(LAYOUT_INFLATER_SERVICE);
        popupKillPlayerView = inflater.inflate(R.layout.kill_player_popup, null);

        yesButton = popupKillPlayerView.findViewById(R.id.yes);
        noButton = popupKillPlayerView.findViewById(R.id.no);
        playerToKillName = popupKillPlayerView.findViewById(R.id.playerName);


        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        popupKillPlayerWindow = new PopupWindow(popupKillPlayerView, width, height, true);
        popupKillPlayerWindow.setOnDismissListener(() -> {
            binding.popupBackground.setVisibility(View.GONE);


        });

        yesButton.setOnClickListener(view -> {
            killPlayer();
            if(isGameOver()){
                gameOver();
            }
        });

        noButton.setOnClickListener(view -> popupKillPlayerWindow.dismiss());

    }

    public void showKillPlayerWindow(){
        // show the popup window
        binding.popupBackground.setVisibility(View.VISIBLE);
        popupKillPlayerWindow.showAtLocation(popupKillPlayerView, Gravity.CENTER, 0, 0);
    }





    @SuppressLint("InflateParams")
    public void setupWhiteHatCheckWordPlayerPopup(){
        LayoutInflater inflater = (LayoutInflater)  getSystemService(LAYOUT_INFLATER_SERVICE);
        popupWhiteHatCheckWordView = inflater.inflate(R.layout.check_white_hat, null);


        guessButton = popupWhiteHatCheckWordView.findViewById(R.id.guess) ;
        guessWord = popupWhiteHatCheckWordView.findViewById(R.id.guessWord) ;


        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        popupWhiteHatCheckWordWindow = new PopupWindow(popupWhiteHatCheckWordView, width, height, true);
        popupWhiteHatCheckWordWindow.setOnDismissListener(() -> {
            if(GuessButtonClicked){
                binding.popupBackground.setVisibility(View.GONE);
            }else{
                showWhiteHatCheckWordPlayerWindow();
            }
        });

        guessButton.setOnClickListener(view -> {
            checkWhiteHatWord();
            if(isGameOver()){
                gameOver();
            }
        });
    }

    public void showWhiteHatCheckWordPlayerWindow(){
        // show the popup window
        GuessButtonClicked = false ;
        binding.popupBackground.setVisibility(View.VISIBLE);
        popupWhiteHatCheckWordWindow.showAtLocation(
                popupWhiteHatCheckWordView,
                Gravity.CENTER, 0, 0
        );
    }

    public void killPlayer(){
        popupKillPlayerWindow.dismiss();

        if(selectedPlayers.get(playerToKillId).getCard().getType() == Card.INNOCENT){
            innocentPlayerNumber -= 1;
            Toast.makeText(this, "you kill a innocent player", Toast.LENGTH_SHORT).show();
            selectedPlayers.remove(playerToKillId);
            changeOrderPlayers();
            adapter.notifyDataSetChanged();
        }else if(selectedPlayers.get(playerToKillId).getCard().getType() == Card.WHITE_HAT){
            showWhiteHatCheckWordPlayerWindow();
        }else{
            blackHatPlayerNumber -= 1;
            Toast.makeText(this, "you kill a black hat player", Toast.LENGTH_SHORT).show();
            selectedPlayers.remove(playerToKillId);
            changeOrderPlayers();
            adapter.notifyDataSetChanged();
        }



    }

    public void checkWhiteHatWord(){
        GuessButtonClicked = true ;
        if(!guessWord.getText().toString().equals(word)){
            selectedPlayers.remove(playerToKillId);
            whiteHatPlayerNumber -= 1 ;
            Toast.makeText(this, "white hat out", Toast.LENGTH_SHORT).show();
            changeOrderPlayers();
            adapter.notifyDataSetChanged();
        }else{
            whiteHatWin = true ;
        }
        popupWhiteHatCheckWordWindow.dismiss();
    }

    public void gameOver(){
        Toast.makeText(this, "Game Over", Toast.LENGTH_SHORT).show();
        passSelectedPlayers();
        finish();
    }

    private void passSelectedPlayers(){
        Intent intent = new Intent( getApplicationContext() , GameOverActivity.class);
        Bundle args = new Bundle();
        args.putSerializable("ARRAYLIST_PLAYERS",selectedPlayersCopy);
        intent.putExtra("BUNDLE",args);
        intent.putExtra("word",word);
        startActivity(intent);
    }
}