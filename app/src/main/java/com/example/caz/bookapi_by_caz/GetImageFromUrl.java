package com.example.caz.bookapi_by_caz;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class GetImageFromUrl extends AsyncTask<String, Void, Bitmap> {

    private ImageView imageViewBookImage;
    public GetImageFromUrl(ImageView ivBookImageView){
        this.imageViewBookImage = ivBookImageView;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {

        String urlDisplay = strings[0];
        Bitmap bitmap = null;
        try {

            InputStream inputStream = new URL(urlDisplay).openStream();
            bitmap = BitmapFactory.decodeStream(inputStream);

        } catch (IOException e) {

            e.printStackTrace();
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        imageViewBookImage.setImageBitmap(bitmap);
    }
}