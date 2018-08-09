package com.example.a4id1.manageralbumow.Helpers;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by 4id1 on 2017-12-07.
 */
public class UploadPhoto extends AsyncTask<String, Void, String> {
    private byte[] photo;
    private Context context;
    public ProgressDialog pDialog;
    private String result;

    public UploadPhoto(byte[] photo, Context context) {
        this.photo = photo;
        Log.d("xxx", "" + photo.length);
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        Log.d("s","sss" +params);

        HttpPost httpPost = new HttpPost("http://4ia1.spec.pl.hostingasp.pl/test_uploadu/SaveCollage.aspx"); // URL_SERWERA proponuję zapisać w osobnej klasie np Settings w postaci stałej
        httpPost.setEntity(new ByteArrayEntity(photo)); // bytes - nasze zdjęcie przekonwertowane na byte[]
        DefaultHttpClient httpClient = new DefaultHttpClient(); // klient http
        HttpResponse httpResponse = null; // obiekt odpowiedzi z serwera
        try {
            httpResponse = httpClient.execute(httpPost); // wykonanie wysłania
            Log.d("response", "R: " + httpResponse);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            result = EntityUtils.toString(httpResponse.getEntity(), HTTP.UTF_8); // odebranie odpowiedzi z serwera, którą potem wyświetlimy w onPostExecute
            Log.d("result", "s" + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog  = new ProgressDialog(context);
        pDialog.setMessage("Trwa wysyłanie pliku...");
        pDialog.setCancelable(false); // nie da się zamknąć klikając w ekran
        pDialog.show();
        Log.d("s","sss");
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pDialog.dismiss();
        Log.d("s","FINISH");
        Toast.makeText(context, result, Toast.LENGTH_SHORT).show();

    }
}
