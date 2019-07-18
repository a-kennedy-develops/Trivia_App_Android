package com.example.alexk.group3_hw02;

// Homework #2
// Group3_HW02
// Alex Kennedy, Will Mayer

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetImageAsync extends AsyncTask<String, Void, Bitmap> {
    IPic iPic;
    Bitmap bitmap = null;

    public GetImageAsync(IPic iPic) {
        this.iPic = iPic;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        int cancelCheck = params.length;
        HttpURLConnection connection = null;

        for (int i = 0; i<cancelCheck; i++) {
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                int length = connection.getContentLength();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    bitmap = BitmapFactory.decodeStream(connection.getInputStream());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }

            if (isCancelled()) {
                break;
            }

        }

        return bitmap;
    }


    @Override
    protected void onPostExecute(Bitmap aBitmap) {
        iPic.handlePic(aBitmap);
    }

    public static interface IPic{
        public void handlePic(Bitmap data); // could add more
    }
}