package com.example.a4id1.manageralbumow.Activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.a4id1.manageralbumow.Adapters.PhotoArrayAdapter;
import com.example.a4id1.manageralbumow.Helpers.Networking;
import com.example.a4id1.manageralbumow.Helpers.PreviewText;
import com.example.a4id1.manageralbumow.Helpers.UploadPhoto;
import com.example.a4id1.manageralbumow.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChosenImageActivity extends AppCompatActivity {

    private ImageView chosenImage;
    private ImageView delete;
    private File file;
    private String[] list = new String[]{"Fonts", "Upload", "Sharing"};
    private ListView listviewPhotoMenu;
    private DrawerLayout drawerLayout;
    private Boolean checkConn;
    private byte[] byteArray;
    private File dir;
    private File dirMain;
    private PreviewText previewText;

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
        setContentView(R.layout.activity_chosen_image);

        Bundle bundle = getIntent().getExtras();
        String imagepath = bundle.getString("sciezka");

        file = new File(imagepath);

        chosenImage = (ImageView) findViewById(R.id.chosenImage);

        delete = (ImageView) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d("lalala", dir.getPath());
                AlertDialog.Builder alert = new AlertDialog.Builder(ChosenImageActivity.this);
                alert.setTitle("Uwaga!");
                alert.setMessage("Czy chcesz usunąć to zdjęcie?");
//ok
                alert.setPositiveButton("Tak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //wyswietl which
                        Log.d("TAG", "CHCE");
                        file.delete();
                        Intent intent = new Intent(ChosenImageActivity.this,AlbumsActivity.class);
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

        listviewPhotoMenu = (ListView) findViewById(R.id.listviewPhotoMenu);
        Log.d("xxx", "P: " + list[1]);
        PhotoArrayAdapter adapter = new PhotoArrayAdapter(
                ChosenImageActivity.this,
                R.layout.photo_layout,
                list
        );
        listviewPhotoMenu.setAdapter(adapter);

        final Bitmap bmp = betterImageDecode(imagepath); // funkcja decodeImage opisana jest poniżej
        chosenImage.setImageBitmap(bmp); // wstawienie bitmapy do ImageView
        //chosenImage.setScaleType(ImageView.ScaleType.CENTER_CROP);

        //  width = bmp.getWidth();
        //   height = bmp.getHeight();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byteArray = stream.toByteArray();

        listviewPhotoMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Networking networking = new Networking(ChosenImageActivity.this);
                checkConn = networking.checkConnection();
                if(i == 0){
                    //fonts
                    Intent intent = new Intent(ChosenImageActivity.this,FontsActivity.class);
                    startActivityForResult(intent, 1);
                }
                else if(i == 1){
                    //upload
                    if(checkConn == false){
                        AlertDialog.Builder alert = new AlertDialog.Builder(ChosenImageActivity.this);
                        alert.setTitle("Uwaga!");
                        alert.setCancelable(false);                    //nie zamyka sie po kliknieciu poza
                        alert.setMessage("Brak połączenia z internetem");
                        alert.setNeutralButton("OK", null).show();     // null to pusty click
                    }
                    else
                    {
                        UploadPhoto uploadPhoto = new UploadPhoto(byteArray, ChosenImageActivity.this);
                        uploadPhoto.execute();
                    }

                }
                else if(i == 2){
                    //sharing
                    if(checkConn == false){
                        AlertDialog.Builder alert = new AlertDialog.Builder(ChosenImageActivity.this);
                        alert.setTitle("Uwaga!");
                        alert.setCancelable(false);                    //nie zamyka sie po kliknieciu poza
                        alert.setMessage("Brak połączenia z internetem");
                        alert.setNeutralButton("OK", null).show();     // null to pusty click
                    }
                    else{
                        Intent share = new Intent(Intent.ACTION_SEND);
                        share.setType("image/jpeg"); //typ danych który chcemy współdzielić
                        SimpleDateFormat dFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
                        String d = dFormat.format(new Date());
                        String tempFileName = d + ".jpg"; // dodaj bieżąca datę do nazwy pliku

                        File dirMain = new File(Environment.getExternalStorageDirectory().getPath() + "/Pictures");

                        File tmpFile = new File(dirMain, tempFileName);
                        if (tmpFile.exists ()) tmpFile.delete ();

                        try {
                            FileOutputStream fs = new FileOutputStream(tmpFile);
                            bmp.compress(Bitmap.CompressFormat.JPEG, 90, fs);
                            fs.flush();
                            fs.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


//teraz utwórz tymczasowy plik (obiekt File), który potem będzie współdzielony
//zapisz tymczasowy plik bezpośrednio na karcie SD w znanej sobie lokalizacji

                        share.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + Environment.getExternalStorageDirectory().getPath() + "/Pictures/" + tempFileName)); //pobierz plik i podziel się nim:
                        startActivity(Intent.createChooser(share, "Podziel się plikiem!")); //pokazanie okna share
                    }
                }
            }
        });
       // drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
     //   drawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                String text = data.getStringExtra("text");
                String fontname = data.getStringExtra("font");

                Typeface tf = Typeface.createFromAsset(getAssets(), "Fonts/" + fontname);
                previewText = new PreviewText(ChosenImageActivity.this, -8000000, tf, text);
            }
        }
    }
}
