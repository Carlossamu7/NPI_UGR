package com.example.alhambra;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class War extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_war);

        // Activa la flecha de ir hacia atrás en la jerarquía de activities
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
