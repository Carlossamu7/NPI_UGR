package com.example.alhambra;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class PuzzleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        // Activa la flecha de ir hacia atrás en la jerarquía de activities
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
