package com.example.alhambra;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;

public class ImageAdapter extends BaseAdapter{
    private Context mContext;
    private AssetManager am;
    private String[] files;

    ImageAdapter(Context c) {
        mContext = c;
        am = mContext.getAssets();
        try { files  = am.list("img"); }
        catch (IOException e) { e.printStackTrace(); }
    }

    public int getCount() {
        return files.length;
    }

    public Object getItem(int position) { return null; }

    public long getItemId(int position) { return 0; }

    // Creamos un nuevo ImageView para cada elemento al que hace referencia el adaptador
    @SuppressLint("InflateParams")
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            convertView = layoutInflater.inflate(R.layout.grid_element, null);
        }

        final ImageView imageView = convertView.findViewById(R.id.gridImageview);
        imageView.setImageBitmap(null);
        // Ejecutamos el código relacionado con la imagen después de presentar la vista
        imageView.post(new Runnable() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void run() {
                new AsyncTask<Void, Void, Void>() {
                    private Bitmap bitmap;
                    @Override
                    protected Void doInBackground(Void... voids) {
                        bitmap = getPicFromAsset(imageView, files[position]);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        super.onPostExecute(aVoid);
                        imageView.setImageBitmap(bitmap);
                    }
                }.execute();
            }
        });

        return convertView;
    }

    private Bitmap getPicFromAsset(ImageView imageView, String assetName) {
        // Dimensiones de la vista
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();
        if(targetW == 0 || targetH == 0) { return null; }

        try {
            InputStream is = am.open("img/" + assetName);
            // Dimensiones del Bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(is, new Rect(-1, -1, -1, -1), bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            is.reset();

            // Decodificamos el archivo de imagen en un Bitmap de tamaño para llenar la vista
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = Math.min(photoW/targetW, photoH/targetH);  // factor escalado

            return BitmapFactory.decodeStream(is, new Rect(-1, -1, -1, -1), bmOptions);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
