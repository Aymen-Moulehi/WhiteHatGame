package com.soretras.whitehat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.soretras.whitehat.databinding.ActivityPrincipalMenuBinding;

public class PrincipalMenuActivity extends AppCompatActivity{

    private ActivityPrincipalMenuBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_menu);
        binding = DataBindingUtil.setContentView(this , R.layout.activity_principal_menu);

        binding.words.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), WordActivity.class));
            }
        });

        binding.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SelectPlayerActivity.class));
            }
        });

        binding.players.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PlayerActivity.class));
            }
        });


    }


}