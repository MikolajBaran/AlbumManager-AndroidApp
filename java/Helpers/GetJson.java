package com.example.a4id1.manageralbumow.Helpers;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by 4id1 on 2017-12-14.
 */
public class GetJson extends AsyncTask<String, Void, String> {
    private JSONArray allImagesJson = null; //obiekt JSONArray

    @Override
    protected String doInBackground(String... params) {
        HttpPost httpPost = new HttpPost("adres strony zwracającej JSON-a");
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String jsonString = null;
        try {
            jsonString = EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

//jesli jsonString nie jest pusty wtedy parsujemy go na obiekt JSON
        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }

//a potem rozbijamy na tablicę obiektów
        try {
            allImagesJson = jsonObj.getJSONArray("ImagesList");
        } catch (JSONException e) {
            e.printStackTrace();
        }

//teraz mogę pobierać dane for-em z elementów tej tablicy

        for (int i = 0; i < allImagesJson.length(); i++) {

            // obiekty po kolei
            try {
                JSONObject object = allImagesJson.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            // poszczególne pola
          //  String imageName = image.getString("imageName");
          //  String imageSaveTime = image.getString("imageSaveTime");

            //tutaj dodaj do ArrayList-y obiekt klasy ImageData
          //  lista.add(new ImageData(imageName, imageSaveTime))


        }
        return null;
    }
}
