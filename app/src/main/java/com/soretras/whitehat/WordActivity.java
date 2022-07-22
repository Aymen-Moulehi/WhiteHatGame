package com.soretras.whitehat;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.soretras.whitehat.databinding.ActivityWordBinding;


public class WordActivity extends AppCompatActivity {

    private ActivityWordBinding binding ;
    private EditText firstWord , secondWord ;
    private Button save ;
    private WordViewModel wordViewModel ;
    private PopupWindow popupWindow ;
    private View popupView ;
    private RecyclerView wordRecyclerView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);
        binding = DataBindingUtil.setContentView(this , R.layout.activity_word);
        setupAddWordPopupWindow();



        wordRecyclerView = binding.wordRecyclerView ;
        WordAdapter adapter = new WordAdapter() ;
        adapter.setContext(getApplicationContext());

        wordViewModel = ViewModelProviders.of(this).get(WordViewModel.class);
        adapter.setWordViewModel(wordViewModel);
        wordRecyclerView.setAdapter(adapter);


        wordViewModel.getAllWords(getApplicationContext());
        wordViewModel.dataMutableLiveData.observe(this, words -> {
            adapter.setWords(words);
            adapter.notifyDataSetChanged();
        });

        binding.addWord.setOnClickListener(view -> showAddWordPopupWindow());
        save.setOnClickListener(view -> createWord());

    }

    @SuppressLint("InflateParams")
    public void setupAddWordPopupWindow(){
        LayoutInflater inflater = (LayoutInflater)  getSystemService(LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.add_word_popup, null);

        save = popupView.findViewById(R.id.save);
        firstWord = popupView.findViewById(R.id.firstWord) ;
        secondWord = popupView.findViewById(R.id.secondWord);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        popupWindow = new PopupWindow(popupView, width, height, true);
        popupWindow.setOnDismissListener(() -> binding.popupBackground.setVisibility(View.GONE));

    }

    public void showAddWordPopupWindow(){
        // show the popup window

        binding.popupBackground.setVisibility(View.VISIBLE);
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);
    }

    public void createWord(){

        String firstWordText = firstWord.getText().toString() ;
        String secondWordText = secondWord.getText().toString() ;
        if(firstWordText.length() >= 2 && secondWordText.length() >= 2){
            wordViewModel.createWord(
                    new Word(firstWordText,secondWordText),
                    getApplicationContext()
            );
        }

        popupWindow.dismiss();

    }
}