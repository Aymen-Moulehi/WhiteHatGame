package com.soretras.whitehat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.soretras.whitehat.databinding.ActivitySelectCardBinding;

import java.util.ArrayList;
import java.util.Random;

public class SelectCardActivity extends AppCompatActivity {

    private int whiteHatPlayerNumber ;
    private int blackHatPlayerNumber ;
    private ArrayList<Player> selectedPlayers ;
    private ArrayList<Card> cards ;
    private ActivitySelectCardBinding binding;
    private GridView cardGridView ;
    private SelectCardAdapter adapter;
    private PopupWindow popupWindow ;
    private View popupView ;
    private PopupWindow popupWindowWhiteHat ;
    private View popupViewWhiteHat ;
    private TextView word ;
    private Button okButton ;
    private Button okButtonWhiteHat ;
    private int playerNameIndex = 0 ;
    private Word wordForGame ;
    private ArrayList<Word> words = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_card);
        binding = DataBindingUtil.setContentView(this , R.layout.activity_select_card);
        setupWordPopupWindow();
        setupWhiteHatPopupWindow();
        getAllWord();

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        selectedPlayers = (ArrayList<Player>) args.getSerializable("ARRAYLIST_PLAYERS");
        whiteHatPlayerNumber = intent.getIntExtra("whiteHatPlayerNumber" , 1);
        blackHatPlayerNumber = intent.getIntExtra("blackHatPlayerNumber", 1);

        binding.playerName.setText(selectedPlayers.get(playerNameIndex).getName());

        //Generate cards for players
        cards = Card.generateCards(
                whiteHatPlayerNumber,
                blackHatPlayerNumber,
                selectedPlayers.size()
        );

        cardGridView = binding.cardGridView ;
        adapter = new SelectCardAdapter(cards, selectedPlayers, getApplicationContext());
        cardGridView.setAdapter(adapter);

        //wordForGame = new Word("Bus","Car");


        cardGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(cards.get(i).getType() == Card.BLACK_HAT) {
                    word.setText(wordForGame.getSecondWord());
                    showWordPopupWindow();
                }
                if(cards.get(i).getType() == Card.WHITE_HAT){
                    showWhiteHatPopupWindow();
                }
                if (cards.get(i).getType() == Card.INNOCENT){
                    word.setText(wordForGame.getFirstWord());
                    showWordPopupWindow();
                }
                selectedPlayers.get(playerNameIndex).setCard(cards.get(i));
                playerNameIndex += 1 ;
                if(playerNameIndex < selectedPlayers.size()){
                    binding.playerName.setText(selectedPlayers.get(playerNameIndex).getName());
                }
                cards.remove(i);
                adapter.notifyDataSetChanged();
            }
        });

    }

    @SuppressLint("InflateParams")
    public void setupWordPopupWindow(){
        LayoutInflater inflater = (LayoutInflater)  getSystemService(LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.show_word_popup, null);

        word = popupView.findViewById(R.id.word);
        okButton = popupView.findViewById(R.id.ok);


        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        popupWindow = new PopupWindow(popupView, width, height, true);
        popupWindow.setOnDismissListener(() -> {
            binding.popupBackground.setVisibility(View.GONE);
            if (cards.size() == 0) {
                passSelectedPlayers();
            }
        });
        okButton.setOnClickListener(view -> popupWindow.dismiss());

    }
    public void showWordPopupWindow(){
        // show the popup window
        binding.popupBackground.setVisibility(View.VISIBLE);
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
    }


    @SuppressLint("InflateParams")
    public void setupWhiteHatPopupWindow(){
        LayoutInflater inflater = (LayoutInflater)  getSystemService(LAYOUT_INFLATER_SERVICE);
        popupViewWhiteHat = inflater.inflate(R.layout.show_white_hat_popup, null);

        okButtonWhiteHat = popupViewWhiteHat.findViewById(R.id.ok);


        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        popupWindowWhiteHat = new PopupWindow(popupViewWhiteHat, width, height, true);
        popupWindowWhiteHat.setOnDismissListener(() -> {
            binding.popupBackground.setVisibility(View.GONE);
            if (cards.size() == 0){
                passSelectedPlayers();
            }
        });
        okButtonWhiteHat.setOnClickListener(view -> popupWindowWhiteHat.dismiss());

    }

    public void showWhiteHatPopupWindow(){
        // show the popup window
        binding.popupBackground.setVisibility(View.VISIBLE);
        popupWindowWhiteHat.showAtLocation(popupView, Gravity.CENTER, 0, 0);
    }


    private void passSelectedPlayers(){
        Intent intent = new Intent( getApplicationContext() , GameTableActivity.class);
        Bundle args = new Bundle();
        args.putSerializable("ARRAYLIST_PLAYERS",selectedPlayers);
        intent.putExtra("BUNDLE",args);
        intent.putExtra("whiteHatPlayerNumber" , whiteHatPlayerNumber );
        intent.putExtra("blackHatPlayerNumber" , blackHatPlayerNumber );
        intent.putExtra("word",wordForGame.getFirstWord());
        startActivity(intent);
        finish();
    }

    public void getAllWord(){
        WordViewModel wordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        wordViewModel.getAllWords(getApplicationContext());
        wordViewModel.dataMutableLiveData.observe(this, words -> {
            this.words = (ArrayList<Word>) words ;
            Random rand = new Random();
            wordForGame = words.get(rand.nextInt(words.size()));
            wordForGame.randomWordOrderChange();
        });
    }

}