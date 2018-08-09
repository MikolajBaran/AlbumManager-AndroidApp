package com.example.a4id1.manageralbumow.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.a4id1.manageralbumow.Helpers.DatabaseManager;
import com.example.a4id1.manageralbumow.R;

import java.io.File;
import java.lang.reflect.Array;

public class ChosenAlbumActivity extends AppCompatActivity {

private ImageView delete;
private LinearLayout images;
private File dir;
    private String albumName;
    private File dirMain;
    private LinearLayout.LayoutParams lparams;
    private LinearLayout.LayoutParams lparams2;
    private LinearLayout.LayoutParams llparams;
    private LinearLayout.LayoutParams lparamsColor;
    private LinearLayout tmpLinearLayout;
    private int count;
    private LinearLayout noteInputs;
    private EditText noteTitle;
    private EditText noteText;
    private DatabaseManager db;
    private View view;
    private int noteColor;
    private int colorTab[] = { Color.rgb(255,205,255), Color.rgb(255,0,0), Color.rgb(13,54,99), Color.rgb(155,8,78)};
    private LinearLayout noteColorsLayout;
    private LinearLayout tmpColorLayout;

    private Bitmap betterImageDecode(String filePath) {

        Bitmap myBitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();    //opcje przekształcania bitmapy
        options.inSampleSize = 4; // zmniejszenie jakości bitmapy 4x
        //
        myBitmap = BitmapFactory.decodeFile(filePath, options);
        return myBitmap;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chosen_album);

        Bundle bundle = getIntent().getExtras();

        albumName = bundle.getString("albumname").toString();



                db = new DatabaseManager(
                ChosenAlbumActivity.this, // activity z galerią zdjęć
                "NotatkiBaranMikolaj.db", // nazwa bazy
                null,
                3 //wersja bazy, po zmianie schematu bazy należy ją zwiększyć
        );

        File pic = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES );
        dirMain = new File(pic, "Baran");
        dir = new File(dirMain, albumName);

        delete = (ImageView) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d("lalala", dir.getPath());
                AlertDialog.Builder alert = new AlertDialog.Builder(ChosenAlbumActivity.this);
                alert.setTitle("Uwaga!");
                alert.setMessage("Czy chcesz usunąć album " + albumName + " wraz z całą jego zawartością?");
//ok
                alert.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //wyswietl which
                        Log.d("TAG", "CHCE");
                        for (File file : dir.listFiles()){
                            //usuwaj pliki
                            file.delete();
                        }
                        dir.delete();
                        Intent intent = new Intent(ChosenAlbumActivity.this,AlbumsActivity.class);
                        startActivity(intent);
                    }

                });

//no
                alert.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        //wyswietl which
                        Log.d("TAG", "NIE CHCE");
                    }
                });
//
                alert.show();
            }
        });

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        File[] files = dir.listFiles();

        images = (LinearLayout) findViewById(R.id.chosenAlbumImages);

        lparams = new LinearLayout.LayoutParams((size.x/3)*2, size.y/3);
        lparams2 = new LinearLayout.LayoutParams(size.x/3, size.y/3);
        llparams = new LinearLayout.LayoutParams(size.x, size.y/3);
        lparamsColor = new LinearLayout.LayoutParams(100, 100);

        count = 0;

        view = View.inflate(ChosenAlbumActivity.this, R.layout.note_inputs, null);


        for(int i=0; i<files.length; i++){
            if(files[i].isFile()){
                ImageView tmp = new ImageView(ChosenAlbumActivity.this);
                final String imagepath = files[i].getPath(); // pobierz scieżkę z obiektu File
                Bitmap bmp = betterImageDecode(imagepath); // funkcja decodeImage opisana jest poniżej
                tmp.setImageBitmap(bmp); // wstawienie bitmapy do ImageView
                tmp.setScaleType(ImageView.ScaleType.CENTER_CROP);

                if(i==files.length-1 && files.length%2==1){
                        tmpLinearLayout = new LinearLayout(ChosenAlbumActivity.this);
                        tmpLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        tmpLinearLayout.setLayoutParams(llparams);
                        if(count%2==0){
                            tmp.setLayoutParams(lparams);
                        }else{
                            tmp.setLayoutParams(lparams2);
                        }
                        //count++;
                        images.addView(tmpLinearLayout);
                }
                else{
                    if(i%2==0){
                        if(count%2==0){
                            tmp.setLayoutParams(lparams);
                        }else{
                            tmp.setLayoutParams(lparams2);
                        }
                        tmpLinearLayout = new LinearLayout(ChosenAlbumActivity.this);
                        tmpLinearLayout.setOrientation(LinearLayout.HORIZONTAL);
                        tmpLinearLayout.setLayoutParams(llparams);
                    }
                    else{
                        if(count%2==0){
                            tmp.setLayoutParams(lparams2);
                        }else{
                            tmp.setLayoutParams(lparams);
                        }
                        count++;
                        images.addView(tmpLinearLayout);
                    }
                }



                tmpLinearLayout.addView(tmp);

                tmp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ChosenAlbumActivity.this,ChosenImageActivity.class);
                        intent.putExtra("sciezka", imagepath);
                        startActivity(intent);
                    }
                });

                tmp.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        noteColorsLayout = (LinearLayout) view.findViewById(R.id.noteColors);
                        for(int i=0; i<colorTab.length; i++){
                            tmpColorLayout = new LinearLayout(ChosenAlbumActivity.this);
                            tmpColorLayout.setBackgroundColor(colorTab[i]);
                            tmpColorLayout.setLayoutParams(lparamsColor);
                            noteColorsLayout.addView(tmpColorLayout);


                            final int finalI = i;
                            tmpColorLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    noteColor = colorTab[finalI];
                                }
                            });
                        }

                        AlertDialog.Builder alert = new AlertDialog.Builder(ChosenAlbumActivity.this);
                        alert.setTitle("Notatka");
                        //alert.setMessage("komunikat");
//ok
                        alert.setPositiveButton("Zapisz", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                noteText = (EditText) view.findViewById(R.id.noteText);
                                noteTitle = (EditText) view.findViewById(R.id.noteTitle);
                                db.insert(noteTitle.getText().toString(), noteText.getText().toString(), noteColor, imagepath );
                                //db.insert("aaa", "bbb");
                            }

                        });

//no
                        alert.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        alert.setView(view);
//
                        alert.show();

                        return false;
                    }
                });
            }
        }
    }
}
