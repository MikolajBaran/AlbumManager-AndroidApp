package com.example.a4id1.manageralbumow.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.a4id1.manageralbumow.Helpers.ImageData;
import com.example.a4id1.manageralbumow.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ChosenCollageActivity extends AppCompatActivity {
private FrameLayout chosenCollageLayout;
    private ImageView tmpIv;
    private ImageView saveCollage;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100){
            if(resultCode == RESULT_OK){
                Log.d("xx", "album");
                Uri imgData = data.getData();
                InputStream stream = null;
                try {
                    stream = getContentResolver().openInputStream(imgData);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Bitmap b = BitmapFactory.decodeStream(stream);
                tmpIv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                tmpIv.setImageBitmap(b);
            }
        }
        else if(requestCode == 200){
            if(resultCode == RESULT_OK){
                Log.d("xx", "aparat");
                Bundle extras = data.getExtras();
                Bitmap b = (Bitmap) extras.get("data");
                tmpIv.setScaleType(ImageView.ScaleType.CENTER_CROP);
                tmpIv.setImageBitmap(b);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chosen_collage);

        ArrayList<ImageData> list = (ArrayList<ImageData>) getIntent().getSerializableExtra("list");

        chosenCollageLayout = (FrameLayout) findViewById(R.id.collageLayout);
        chosenCollageLayout.setDrawingCacheEnabled(true);

        saveCollage  = (ImageView) findViewById(R.id.saveCollage);


        for(int i=0; i<list.size(); i++){
            final ImageView iv = new ImageView(ChosenCollageActivity.this);
            iv.setX(list.get(i).getX());
            iv.setY(list.get(i).getY());
            Log.d("xxx", "x" + list.get(i).getX() + "y" + list.get(i).getY() + "w" +list.get(i).getW() + "h" +  list.get(i).getH());
            iv.setImageResource(R.drawable.ic_add_a_photo_black_48dp);
            iv.setLayoutParams(new LinearLayout.LayoutParams(list.get(i).getW(),list.get(i).getH()));

            iv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    tmpIv = iv;
                    AlertDialog.Builder alert = new AlertDialog.Builder(ChosenCollageActivity.this);
                    alert.setTitle("Wybierz, skąd chcesz dodać zdjęcie");
//nie może mieć setMessage!!!
                    final String[] opcje = {"Galeria", "Aparat"};
                    alert.setItems(opcje, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // wyswietl opcje[which]);
                            switch (opcje[which]) {
                                case "Galeria":
                                    Intent intent = new Intent(Intent.ACTION_PICK);
                                    intent.setType("image/*");
                                    startActivityForResult(intent, 100); // 100 - stała wartośc która posłuży do identyfikacji tej akcji
                                    break;
                                case "Aparat":
                                    Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    //jesli jest dostepny aparat
                                    if (intent2.resolveActivity(getPackageManager()) != null) {
                                        startActivityForResult(intent2, 200); // 200 - jw
                                    }
                                    break;
                            }
                        }
                    });
                    alert.show();
                    return false;
                }
            });

            chosenCollageLayout.addView(iv);
        }

        saveCollage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ChosenCollageActivity.this);
                alert.setTitle("Czy chcesz zapisać kolaż?");
                alert.setMessage("Twój kolaż zostanie zapisany w folderze 'kolaże'");
//ok
                alert.setPositiveButton("Zapisz", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //wyswietl which
                        Bitmap b = chosenCollageLayout.getDrawingCache(true);

                        File pic = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES );
                        File dirMain = new File(pic, "Baran");
                        File dir = new File(dirMain, "kolaże");

                        SimpleDateFormat dFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
                        String d = dFormat.format(new Date());

                        FileOutputStream fs = null;
                        try {
                            fs = new FileOutputStream(dir.getPath()+ "/" + d + ".png");
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        try {
                            b.compress(Bitmap.CompressFormat.PNG, 100, fs); // bmp is your Bitmap instance
                            fs.close();
                            Toast toast = Toast.makeText(ChosenCollageActivity.this, "Zapisano", Toast.LENGTH_SHORT);
                            toast.show();
                        } catch (IOException e) {
                            Toast toast = Toast.makeText(ChosenCollageActivity.this, "Nie udało się zapisać", Toast.LENGTH_SHORT);
                            toast.show();
                            e.printStackTrace();
                        }

                    }

                });

//no
                alert.setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        //wyswietl which
                    }
                });
//
                alert.show();
            }
        });
    }
}
