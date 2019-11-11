package com.example.alhambra;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class War extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor acelerometro;
    private int disparo = 0;
    private int recarga = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_war);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        assert sensorManager != null;
        acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        assert acelerometro != null;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Do something here if sensor accuracy changes.
    }
    @Override
    public void onSensorChanged(SensorEvent event) {
        float x,y,z;
        x = event.values[0];
        y = event.values[1];
        z = event.values[2];

        if(x<=7 && disparo == 0 && recarga == 3){
            disparo++;
            getWindow().getDecorView().setBackgroundColor(Color.BLUE);
        }
        else if (x>7 && disparo == 1 && recarga == 3){
            disparo++;
            getWindow().getDecorView().setBackgroundColor(Color.RED);

        }
        if(disparo == 2) {
            disparo = 0;
            recarga = 0;
            soundShot();
        }

        if(x<=-7 && recarga ==0){
            recarga++;
            getWindow().getDecorView().setBackgroundColor(Color.BLUE);
        }
        else if (x>-7 && recarga == 1){
            recarga++;
        }
        if(recarga == 2) {
            recarga++;
            soundReload();
        }
    }


    private void soundShot( ){
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.disparo);
        mediaPlayer.start();
    }
    private void soundReload( ){
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.recarga);
        mediaPlayer.start();
    }


    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, acelerometro, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }
}
