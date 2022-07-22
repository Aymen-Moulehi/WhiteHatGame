package com.soretras.whitehat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.soretras.whitehat.databinding.ActivityPlayerBinding;
import com.soretras.whitehat.databinding.ActivityWordBinding;

import java.util.ArrayList;
import java.util.List;

public class PlayerActivity extends AppCompatActivity {

    private ActivityPlayerBinding binding ;
    private EditText playerName ;
    private Button save ;
    private PlayerViewModel playerViewModel ;
    private PopupWindow popupWindow ;
    private View popupView ;
    private RecyclerView playerRecyclerView ;
    private ArrayList<Player> players = new ArrayList<>() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        binding = DataBindingUtil.setContentView(this , R.layout.activity_player);
        setupAddPlayerPopupWindow();



        playerRecyclerView = binding.playerRecyclerView ;
        PlayerAdapter adapter = new PlayerAdapter() ;
        adapter.setPlayers(players);
        adapter.setContext(getApplicationContext());




        playerViewModel = ViewModelProviders.of(this).get(PlayerViewModel.class);
        adapter.setPlayerViewModel(playerViewModel);
        playerRecyclerView.setAdapter(adapter);

        playerViewModel.getAllPlayers(getApplicationContext());
        playerViewModel.dataMutableLiveData.observe(this, new Observer<List<Player>>() {
            @Override
            public void onChanged(List<Player> players) {
                adapter.setPlayers((ArrayList<Player>) players);
                adapter.notifyDataSetChanged();
            }
        });
        binding.addPlayer.setOnClickListener(view -> showAddPlayerPopupWindow());
        save.setOnClickListener(view -> createPlayer());


    }

    private void createPlayer() {
        String playerName = this.playerName.getText().toString() ;

        if(playerName.length() > 2){
            playerViewModel.createPlayer(
                    new Player(playerName),
                    getApplicationContext()
            );
        }

        popupWindow.dismiss();
    }

    private void showAddPlayerPopupWindow() {
        binding.popupBackground.setVisibility(View.VISIBLE);
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
    }

    private void setupAddPlayerPopupWindow() {
        LayoutInflater inflater = (LayoutInflater)  getSystemService(LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.add_player_popup, null);

        save = popupView.findViewById(R.id.save);
        playerName = popupView.findViewById(R.id.playerName) ;

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        popupWindow = new PopupWindow(popupView, width, height, true);
        popupWindow.setOnDismissListener(() -> binding.popupBackground.setVisibility(View.GONE));
    }
}