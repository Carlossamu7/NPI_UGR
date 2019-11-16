package com.e.alhambranpi;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static android.os.SystemClock.sleep;

public class War extends AppCompatActivity implements SensorEventListener {

    private TextView text;
    private SensorManager sensorManager;
    private Sensor acelerometro;
    private int disparo = 0;
    private int recarga = 0;
    private int swapping = 0;
    private int precision = 7;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_war);
        getSupportActionBar().setTitle("Recarga y dispara!");
        text = (TextView)findViewById(R.id.textAcelerometro);

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
            recarga++;
            text.setText("");
            text.append("Shoot");
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
            text.setText("");
            text.append("Reload");
        }
    }

    public boolean swap(float y){

        if(y<=precision && swapping == 0){
            swapping++;
        }
        else if (y>precision && swapping == 1){
            soundSwap();
            swapping = 0;
            text.setText("");
            text.append("Swapped");
            return true;
        }
        return false;
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

        /*

        if(z>=-4.5 && z <= 4.5) {
            if (recarga < 2) reload(x);
            else if (recarga == 2) shoot(x);
        }
*/
        if(x >= -2 && x <= 2) swap(y);
        text.setText("");
        text.append("\n" + "X: " + x + "\n" + "Y: " + y + "\n" +"Z: " + z);
    }


    private void soundShot( ){
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.disparo);
        mediaPlayer.start();
    }
    private void soundReload( ){
        MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.recarga);
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
