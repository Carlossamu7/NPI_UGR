package com.e.proyectoalhambra

import android.media.MediaPlayer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View
import android.widget.Button as Button1

class War : AppCompatActivity() {

    private lateinit var mediaPlayer: MediaPlayer;
    private lateinit var buttonWar: Button1;

    fun pum(view: View){
        buttonWar = findViewById(R.id.buttonWar)
        mediaPlayer = MediaPlayer.create(this, R.raw.disparo_1)
        buttonWar.setOnClickListener(View.OnClickListener {  })
        mediaPlayer.start()
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_war)






    }
}
