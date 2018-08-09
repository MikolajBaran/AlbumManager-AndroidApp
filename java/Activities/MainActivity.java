package com.example.a4id1.manageralbumow.Activities;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.a4id1.manageralbumow.R;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private LinearLayout camera;
    private LinearLayout albums;
    private LinearLayout notes;
    private LinearLayout collage;
    private LinearLayout network;
    private File pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pic = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES );

        File dir = new File(pic, "Baran");
        if(!dir.exists()){
            dir.mkdir();

            File dir2 = new File(dir.getPath(), "ludzie");
            dir2.mkdirs();

            File dir3 = new File(dir.getPath(), "XXX");
            dir3.mkdirs();

            File dir4 = new File(dir.getPath(), "miejsca");
            dir4.mkdirs();

            File dir5 = new File(dir.getPath(), "kolaże");
            dir5.mkdirs();
        }

        camera = (LinearLayout) findViewById(R.id.camera);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,CameraActivity.class);
                startActivity(intent);
            }
        });

        albums = (LinearLayout) findViewById(R.id.albums);

        albums.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AlbumsActivity.class);
                intent.putExtra("key", pic);
                startActivity(intent);
            }
        });

        notes = (LinearLayout) findViewById(R.id.notes);

        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,NotesActivity.class);
                startActivity(intent);
            }
        });

        collage = (LinearLayout) findViewById(R.id.collage);

        collage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CollageActivity.class);
                startActivity(intent);
            }
        });

        network = (LinearLayout) findViewById(R.id.network);

        network.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,NetworkActivity.class);
                startActivity(intent);
            }
        });
    }
}
