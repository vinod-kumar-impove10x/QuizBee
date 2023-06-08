package com.example.quizbee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.quizbee.databinding.ActivityHomeBinding;

public class HomeActivity extends AppCompatActivity {
    public ActivityHomeBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
       // getSupportActionBar().setTitle("Home");
        handleBtn();
    }

    private void handleBtn() {
        binding.startBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, QuestionActivity.class);
            startActivity(intent);
        });
    }
}