package com.example.alhambra;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;



public class Storytelling extends AppCompatActivity {

    private static final int PERMISSION_CODE = 1000;
    private static final int IMAGE_CAPTURE_CODE = 1001;

    Button mCaptureBtn;

    Uri image_uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storytelling);

        // Activa la flecha de ir hacia atrás en la jerarquía de activities
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mCaptureBtn = findViewById(R.id.button);

        mCaptureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.CAMERA) ==
                            PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                                    PackageManager.PERMISSION_DENIED){
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission, PERMISSION_CODE);
                    }
                    else{
                        openCamera();

                    }
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
            bitmapBlanco = redimensionar(alto, ancho, bitmapBlanco);
            bitmapNegro = redimensionar(alto, ancho, bitmapNegro);

            if (comparar(bitmapFoto, bitmapNegro)){
                //HACER ALGO SABIENDO QUE LA FOTO ES NEGRA
            } else{
                if (comparar(bitmapFoto, bitmapBlanco)){
                    //HACER ALGO SABIENDO QUE LA FOTO ES BLANCA
                }
            }
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
        return Bitmap.createScaledBitmap(original, ancho,alto,false);
    }

    private boolean comparar(Bitmap b1, Bitmap b2) {
        // FALTA COMPLETAR ESTE METODO
        return true;
    }



}
