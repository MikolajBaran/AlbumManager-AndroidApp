package com.example.a4id1.manageralbumow.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.a4id1.manageralbumow.R;

import java.io.File;
import java.util.Arrays;

public class AlbumsActivity extends AppCompatActivity {

    private ListView albumsListview;
    private String[] listviewTextArray;
    private ImageView addAlbum;
    private EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_albums);

        File pic = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES );
        final File dir = new File(pic, "Baran");

        File[] files = dir.listFiles(); // tablica plików
        Arrays.sort(files); // sortowanie plików wg nazwy

        listviewTextArray = new String[files.length];
        for (int i=0; i<files.length; i++){
            listviewTextArray[i] = files[i].getName();
        }

        albumsListview = (ListView) findViewById(R.id.albumsListview);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                AlbumsActivity.this,     // Context
                R.layout.listview_layout,     // nazwa pliku xml naszego wiersza
                R.id.listviewText,         // id pola txt w wierszu
                listviewTextArray );         // tablica przechowująca dane

        albumsListview.setAdapter(adapter);

        albumsListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //test
                Log.d("TAG","index = " + i);
                Intent intent = new Intent(AlbumsActivity.this,ChosenAlbumActivity.class);
                intent.putExtra("albumname", listviewTextArray[i]);
                startActivity(intent);
            }
        });

        addAlbum = (ImageView) findViewById(R.id.addAlbum);
        addAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(AlbumsActivity.this);
                alert.setTitle("Dodanie albumu");
                alert.setMessage("Podaj nazwę nowego albumu:");
//tutaj input
                input = new EditText(AlbumsActivity.this);
                input.setText("nowy album");
                alert.setView(input);
//teraz butony jak poprzednio i
                alert.setPositiveButton("Dodaj", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //wyswietl which
                        Log.d("TAG", "CHCE");

                        File dirNew = new File(dir, input.getText().toString());
                        dirNew.mkdir();
                    }
                });

//no
                alert.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        //wyswietl which
                        Log.d("TAG", "NIE CHCE");
                    }
                });
                alert.show();
            }
        });
    }
}
