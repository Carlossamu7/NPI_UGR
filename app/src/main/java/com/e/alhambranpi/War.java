package com.e.alhambranpi;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

import static android.os.SystemClock.sleep;

public class War extends AppCompatActivity implements SensorEventListener {

    private TextView texto_arriba;
    private TextView texto_abajo;
    private SensorManager sensorManager;
    private Sensor acelerometro;
    private int disparo = 0;
    private int recarga = 0;
    private int swapping = 0;
    private int precision = 7;
    private double giro = 4.5;
    private boolean arma_seleccionada = false;
    private int arma = 0; //arco = 0, pistola = 1, cañon = 2


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_war);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Alhabatalla");
        texto_arriba = (TextView)findViewById(R.id.textAcelerometro);
        texto_abajo = (TextView)findViewById(R.id.textSwap);


        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        assert sensorManager != null;
        acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        assert acelerometro != null;
    }

    public void reload(float x){

        if (x >= -precision && recarga == 0) {
            recarga++;
        } else if (x < -precision && recarga == 1) {
            soundReload();
            arma_seleccionada = true;
            recarga++;
            texto_abajo.setText("");
            texto_abajo.append("¡Dispara!");
        }
    }

    public void shoot(float x){

        if(x<=precision && disparo == 0){
            disparo++;
        }
        else if (x>precision && disparo == 1){
            disparo = 0;
            recarga = 0;
            soundShot();
            arma_seleccionada = false;
            texto_abajo.setText("");
            texto_abajo.append("Recarga o elige tu arma");
        }
    }

    public void swap(float y, float z){

        if(( z>= precision || y<=precision) && swapping == 0){
            swapping++;
        }
        else if ((z < precision-2 || y >precision+1) && swapping == 1){
            soundSwap();
            arma = (++arma)%3;
            swapping = 0;
        }
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

        if(z>= -giro && z <= giro) {
            if (recarga < 2) reload(x);
            else if (recarga == 2) shoot(x);
        }
        else if(x >= -giro && x <= giro && !arma_seleccionada) swap(y, z);

        if(arma == 0) {
            getWindow().getDecorView().setBackgroundResource(R.drawable.arco);
            texto_arriba.setText("");
            texto_arriba.append("Arco");
        }
        else if (arma == 1) {
            getWindow().getDecorView().setBackgroundResource(R.drawable.esco);
            texto_arriba.setText("");
            texto_arriba.append("Escopeta");
        }
        else {
            getWindow().getDecorView().setBackgroundResource(R.drawable.cannon);
            texto_arriba.setText("");
            texto_arriba.append("Cañón");
        }

    }


    private void soundShot(){
        MediaPlayer mediaPlayer;
        if(arma == 0) mediaPlayer = MediaPlayer.create(this, R.raw.disparar_arco);
        else if (arma == 1) mediaPlayer = MediaPlayer.create(this, R.raw.disparo);
        else  mediaPlayer = MediaPlayer.create(this, R.raw.disparar_canion);

        mediaPlayer.start();
    }
    private void soundReload(){
        MediaPlayer mediaPlayer;
        if(arma == 0) mediaPlayer = MediaPlayer.create(this, R.raw.tensar_arco);
        else if (arma == 1) mediaPlayer = MediaPlayer.create(this, R.raw.recarga);
        else  mediaPlayer = MediaPlayer.create(this, R.raw.encender_canion);

        mediaPlayer.start();

    }
    private void soundSwap( ){
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.swap);
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
