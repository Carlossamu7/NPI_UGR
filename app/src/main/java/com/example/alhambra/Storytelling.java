package com.example.alhambra;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

public class Storytelling extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storytelling);

        // Activa la flecha de ir hacia atrás en la jerarquía de activities
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
