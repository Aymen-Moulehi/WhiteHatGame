package com.soretras.whitehat;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.soretras.whitehat.databinding.ActivityStartBinding;

public class StartActivity extends AppCompatActivity {

    private ActivityStartBinding binding ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        binding = DataBindingUtil.setContentView(this , R.layout.activity_start);

        binding.exit.setOnClickListener(view -> finish());

        binding.play.setOnClickListener(view ->
            startActivity(new Intent(getApplicationContext(),PrincipalMenuActivity.class)));
    }


}