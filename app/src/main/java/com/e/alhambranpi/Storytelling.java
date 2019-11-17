package com.e.alhambranpi;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Objects;


public class Storytelling extends AppCompatActivity {

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;

    FloatingActionButton mCaptureBtn;
    TextView textV;
    ImageView imageV;
    ConstraintLayout layout;

    Uri image_uri;

    int negro = 0;
    int blanco = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storytelling);

        // Activa la flecha de ir hacia atrás en la jerarquía de activities
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Storytelling!");

        mCaptureBtn = findViewById(R.id.button);
        textV = findViewById(R.id.tvStorytelling);
        imageV = findViewById(R.id.imStorytelling);
        layout = findViewById(R.id.layout);

        mCaptureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission(Manifest.permission.CAMERA) ==
                        PackageManager.PERMISSION_DENIED ||
                        checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                PackageManager.PERMISSION_DENIED) {
                    String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    requestPermissions(permission, PERMISSION_CODE);
                }
                else{
                    openCamera();

                }
            }
        });
    }

    private void openCamera(){
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the camera");
        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openCamera();
                }
                else{
                    Toast.makeText(this, "Permiso denegado...", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmapFoto;
        Bitmap bitmapNegro;
        Bitmap bitmapBlanco;
        if (resultCode == RESULT_OK){
            bitmapFoto = decodeUriToBitmap(this, image_uri);

            bitmapBlanco = BitmapFactory.decodeResource(getResources(), R.drawable.fblanco);
            bitmapNegro = BitmapFactory.decodeResource(getResources(), R.drawable.fnegro);
            int alto = getAlto(bitmapBlanco, bitmapFoto, bitmapNegro);
            int ancho = getAncho(bitmapBlanco, bitmapFoto, bitmapNegro);

            bitmapFoto = redimensionar(alto, ancho, bitmapFoto);

            eleccionMonumento(bitmapFoto);
        }
    }

    private void eleccionMonumento(Bitmap bitmapFoto) {
        if (esNegro(bitmapFoto)) {
            eleccionMonumentoNegro();
            negro++;
        } else{
            if (esBlanco(bitmapFoto)){
                eleccionMonumentoBlanco();
                blanco++;
            } else{
                if (textV.getVisibility() == View.VISIBLE)
                    textV.setVisibility(View.INVISIBLE);
                imageV.setImageResource(R.drawable.musulman);
                layout.setBackground(ContextCompat.getDrawable(this, R.drawable.fondo_fin));
                Objects.requireNonNull(getSupportActionBar()).setTitle("Y ahora es momento de...");
            }
        }
    }

    private void eleccionMonumentoBlanco() {
        switch (blanco) {
            case 0:
                if (textV.getVisibility() == View.INVISIBLE)
                    textV.setVisibility(View.VISIBLE);
                textV.setText("Estamos en la Puerta de las Granadas, se construyó en 1536 sobre la antigua Puerta Bib-Albuxar (Puerta de las Alegres Nuevas). Algunos restos de esta puerta quedan aún en su parte derecha. ");
                imageV.setImageResource(R.drawable.guia_turistica);
                layout.setBackground(ContextCompat.getDrawable(this, R.drawable.puerta_granadas));
                getSupportActionBar().setTitle("Puerta de las Granadas!");
                break;
            case 1:
                textV.setText("Estamos ya dentro de la ciudad amurallada de la Alhambra medieval en la Plaza de los Aljibes. Esta plaza era un lugar de tránsito que separaba la zona militar y los palacios.");
                imageV.setImageResource(R.drawable.soldado);
                layout.setBackground(ContextCompat.getDrawable(this, R.drawable.plaza_aljibes));
                Objects.requireNonNull(getSupportActionBar()).setTitle("Plaza de los aljibes!");
                break;
            default:
                if (textV.getVisibility() == View.VISIBLE)
                    textV.setVisibility(View.INVISIBLE);
                imageV.setImageResource(R.drawable.musulman);
                layout.setBackground(ContextCompat.getDrawable(this, R.drawable.fondo_fin));
                Objects.requireNonNull(getSupportActionBar()).setTitle("Y ahora es momento de...");
                blanco = -1;
                break;
        }
    }

    private void eleccionMonumentoNegro(){
        switch (negro) {
            case 0:
                if (textV.getVisibility() == View.INVISIBLE)
                    textV.setVisibility(View.VISIBLE);
                textV.setText("Estamos en la Puerta de la Justicia, también conocida como Puerta de la Explanada por el amplio espacio que se extendía ante ella. Su majestuosa figura preside todo el ámbito y se ha convertido en uno de los símbolos de la Alhambra.");
                imageV.setImageResource(R.drawable.guia_turistico);
                layout.setBackground(ContextCompat.getDrawable(this, R.drawable.puerta_justicia));
                Objects.requireNonNull(getSupportActionBar()).setTitle("Puerta de la justicia!");
                break;
            case 1:
                textV.setText("Encontraste mi Palacio! Yo soy Carlos V y esta una construcción renacentista situada en la colina de la Alhambra de la ciudad española de Granada, en Andalucía.");
                imageV.setImageResource(R.drawable.carlosv);
                layout.setBackground(ContextCompat.getDrawable(this, R.drawable.palacio_carlosv));
                Objects.requireNonNull(getSupportActionBar()).setTitle("Palacio Carlos V!");
                break;
            default:
                if (textV.getVisibility() == View.VISIBLE)
                    textV.setVisibility(View.INVISIBLE);
                imageV.setImageResource(R.drawable.musulman);
                layout.setBackground(ContextCompat.getDrawable(this, R.drawable.fondo_fin));
                Objects.requireNonNull(getSupportActionBar()).setTitle("Y ahora es momento de...");
                negro = -1;
                break;
        }
    }


    public static Bitmap decodeUriToBitmap(Context context, Uri sendUri) {
        Bitmap getBitmap = null;
        try {
            InputStream image_stream;
            try {
                image_stream = context.getContentResolver().openInputStream(sendUri);
                getBitmap = BitmapFactory.decodeStream(image_stream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return getBitmap;
    }


    private int getAlto(Bitmap b1, Bitmap b2, Bitmap b3){
        int b1alto = b1.getHeight();
        int b2alto = b2.getHeight();
        int b3alto = b3.getHeight();

        if (b1alto < b2alto && b1alto < b3alto){
            return b1alto;
        } else {
            if (b2alto < b1alto && b2alto < b3alto){
                return b2alto;
            } else{
                return b3alto;
            }
        }
    }

    private int getAncho(Bitmap b1, Bitmap b2, Bitmap b3){
        int b1ancho = b1.getWidth();
        int b2ancho = b2.getWidth();
        int b3ancho = b3.getWidth();

        if (b1ancho < b2ancho && b1ancho < b3ancho){
            return b1ancho;
        } else {
            if (b2ancho < b1ancho && b2ancho < b3ancho){
                return b2ancho;
            } else{
                return b3ancho;
            }
        }
    }

    private Bitmap redimensionar(int alto, int ancho, Bitmap original){
        return Bitmap.createScaledBitmap(original, ancho, alto,false);
    }


    private boolean esNegro(Bitmap b1) {
        int color = getAverageColor(b1);
        int rojo = (color >> 16) & 0xff;
        int verde = (color >> 8) & 0xff;
        int azul = (color) & 0xff;

        return !(rojo > 0x30 || verde > 0x30 || azul > 0x30); //Es negro

    }

    private boolean esBlanco(Bitmap b1) {
        int color = getAverageColor(b1);
        int rojo = (color >> 16) & 0xff;
        int verde = (color >> 8) & 0xff;
        int azul = (color) & 0xff;

        return !(rojo < 0x6f || verde < 0x6f || azul < 0x6f); //Es blanco
    }


    int getAverageColor(Bitmap b1) {
        int redBucket = 0;
        int greenBucket = 0;
        int blueBucket = 0;
        int pixelCount = 0;

        for (int y = 0; y < b1.getHeight(); y++) {
            for (int x = 0; x < b1.getWidth(); x++) {
                int c = b1.getPixel(x, y);
                pixelCount++;
                redBucket += Color.red(c);
                greenBucket += Color.green(c);
                blueBucket += Color.blue(c);
            }
        }
        int media_rojo =  redBucket/pixelCount;
        int media_verde =  greenBucket/pixelCount;
        int media_azul =  blueBucket/pixelCount;

        return (media_rojo & 0xff) << 16 | (media_verde & 0xff) << 8 | (media_azul & 0xff);
    }



}
