package com.example.a4id1.manageralbumow.Activities;

import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.OrientationEventListener;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.a4id1.manageralbumow.Helpers.CameraPreview;
import com.example.a4id1.manageralbumow.Helpers.Kolo;
import com.example.a4id1.manageralbumow.Helpers.Miniatura;
import com.example.a4id1.manageralbumow.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CameraActivity extends AppCompatActivity {

    private Camera camera;
    private int cameraId = -1;
    private CameraPreview _cameraPreview;
    private FrameLayout _frameLayout;

    private Camera.Parameters camParams;

    private ImageView takePhoto;
    private ImageView savePhoto;

    private ImageView photoExposure;
    private ImageView photoBrightness;
    private ImageView photoColors;
    private ImageView photoSize;

    private Kolo kolo;
    private double angle;
    private ArrayList<Miniatura> photosArrayList = new ArrayList<>();
    private int countPhotoPreviews = 0;

    private OrientationEventListener orientationEventListener;
    private boolean turnMenu = true;
    private boolean turnBackMenu = false;

    private boolean hideMenu = true;
    private LinearLayout photooptionsLayout;
    private LinearLayout takesavephotoLayout;


    private byte[] fdata = null;

    private void initCamera() {
        boolean cam = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);

        if (!cam) {
            // uwaga - brak kamery

        } else {

            // wykorzystanie danych zwróconych przez kolejną funkcję getCameraId()

            cameraId = getCameraId();
            // jest jakaś kamera!
            if (cameraId < 0) {
                // brak kamery z przodu!
            } else {
                camera = Camera.open(cameraId);
            }
        }
    }

    private int getCameraId(){
        int cid = 0;
        int camerasCount = Camera.getNumberOfCameras(); // gdy więcej niż jedna kamera

        for (int i = 0; i < camerasCount; i++) {
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(i, cameraInfo);

            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                cid = i;
            }
             /*
            if (cameraInfo.facing == CameraInfo.CAMERA_FACING_FRONT) {
               cid = i;
            }
            */
        }

        return cid;
    }


    private void initPreview(){
        _cameraPreview = new CameraPreview(CameraActivity.this, camera);
        _frameLayout = (FrameLayout) findViewById(R.id.cameraLayout);
        _frameLayout.addView(_cameraPreview);

        kolo = new Kolo(CameraActivity.this);
        _frameLayout.addView(kolo);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        takePhoto = (ImageView) findViewById(R.id.takePhoto);
        savePhoto = (ImageView) findViewById(R.id.savePhoto);

        photoExposure = (ImageView) findViewById(R.id.photoExposure);
        photoBrightness = (ImageView) findViewById(R.id.photoBrightness);
        photoColors = (ImageView) findViewById(R.id.photoColors);
        photoSize = (ImageView) findViewById(R.id.photoSize);

        takesavephotoLayout = (LinearLayout) findViewById(R.id.takesavephotoLayout);
        photooptionsLayout = (LinearLayout) findViewById(R.id.photooptionsLayout);


        //Camera
        initCamera();
        initPreview();
        //

        camParams = camera.getParameters();

        if (camParams != null){

            //

            photoExposure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final List<String> balance = camParams.getSupportedWhiteBalance();
                    final String[] items = new String[balance.size()];

                    for (int i=0; i<balance.size(); i++){
                        items[i] = balance.get(i);
                    }

                    AlertDialog.Builder alert = new AlertDialog.Builder(CameraActivity.this);
                    alert.setTitle("Wybierz balans bieli");
                    alert.setItems(items, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            camParams.setWhiteBalance(items[which]);
                            camera.setParameters(camParams);

                        }
                    });
                    alert.show();
                }
            });
            //

            photoBrightness.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int tmpMin = camParams.getMinExposureCompensation();
                    int tmpMax = camParams.getMaxExposureCompensation();
                    int tmp;

                    if (tmpMin<0){
                        tmpMin = Math.abs(tmpMin);
                        tmp = tmpMax + tmpMin;
                    }else {
                        tmp = tmpMax + tmpMin;
                    }

                    final String[] items = new String[tmp+1];
                    for (int i=0; i<=tmp; i++){
                        items[i] = String.valueOf(camParams.getMaxExposureCompensation() - i);
                    }

                    AlertDialog.Builder alert = new AlertDialog.Builder(CameraActivity.this);
                    alert.setTitle("Wybierz naświetlenie");
                    alert.setItems(items, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            camParams.setExposureCompensation(Integer.parseInt(items[which]));
                            camera.setParameters(camParams);

                        }
                    });
                    alert.show();
                }
            });

            //
            photoColors.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final List<String> effects = camParams.getSupportedColorEffects();
                    final String[] items = new String[effects.size()];

                    for (int i=0; i<effects.size(); i++){
                        items[i] = effects.get(i);
                    }

                    AlertDialog.Builder alert = new AlertDialog.Builder(CameraActivity.this);
                    alert.setTitle("Wybierz efekt kolorystyczny");
                    alert.setItems(items, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            camParams.setColorEffect(items[which]);
                            camera.setParameters(camParams);

                        }
                    });
                    alert.show();

                }
            });

            //
            photoSize.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final List<Camera.Size> sizes = camParams.getSupportedPictureSizes();
                    final String[] items = new String[sizes.size()];

                    Camera.Size result = null;
                    for (int i=0;i<sizes.size();i++){
                        result = (Camera.Size) sizes.get(i);
                        String tmp = result.width + " x " + result.height;
                        items[i] = tmp;
                    }

                    AlertDialog.Builder alert = new AlertDialog.Builder(CameraActivity.this);
                    alert.setTitle("Wybierz rozmiar zdjęcia");
                    alert.setItems(items, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            Camera.Size result = null;
                            result = (Camera.Size) sizes.get(which);
                            camParams.setPictureSize(result.width , result.height);
                            camera.setParameters(camParams);

                        }
                    });
                    alert.show();

                }
            });
            //
        }

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                camera.takePicture(null, null, camPictureCallback);

            }
        });

        savePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AlertDialog.Builder alert = new AlertDialog.Builder(CameraActivity.this);
                alert.setTitle("Wybierz lokalizację zdjęcia");
                //nie może mieć setMessage!!!

                File pic = Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES );
                File dir = new File(pic, "Baran");
                final File[] files = dir.listFiles();

                String[] array = new String[files.length];
                for(int q=0; q<files.length; q++){
                    array[q] = files[q].getName();
                }

                alert.setItems(array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Log.d("OPT", files[which].getPath());
                        if (fdata != null){
                            SimpleDateFormat dFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
                            String d = dFormat.format(new Date());

                            FileOutputStream fs = null;
                            try {
                                fs = new FileOutputStream(files[which].getPath() + "/" + d);
                            } catch (FileNotFoundException e) {

                            }
                            try {
                                fs.write(fdata);
                                fs.close();
                                Toast toast = Toast.makeText(CameraActivity.this, "Zapisano", Toast.LENGTH_SHORT);
                                toast.show();
                            } catch (IOException e) {
                                Toast toast = Toast.makeText(CameraActivity.this, "Nie udało się zapisać", Toast.LENGTH_SHORT);
                                toast.show();
                            }

                        }
                    }
                });
                alert.show();

            }
        });



        orientationEventListener = new OrientationEventListener(CameraActivity.this) {
            @Override
            public void onOrientationChanged(int i) {

                Log.d("xxx", i+ "");
                // i zwraca kąt 0 - 360 stopni podczas obracania ekranem w osi Z
                // tutaj wykonaj animacje butonów i miniatur zdjęć
                    if(i < 315 && i > 180){
                        if(turnMenu == true) {
                            ObjectAnimator.ofFloat(takePhoto, View.ROTATION, 0, 90)
                                    .setDuration(300)
                                    .start();
                            ObjectAnimator.ofFloat(savePhoto, View.ROTATION, 0, 90)
                                    .setDuration(300)
                                    .start();
                            ObjectAnimator.ofFloat(photoBrightness, View.ROTATION, 0, 90)
                                    .setDuration(300)
                                    .start();
                            ObjectAnimator.ofFloat(photoColors, View.ROTATION, 0, 90)
                                    .setDuration(300)
                                    .start();
                            ObjectAnimator.ofFloat(photoExposure, View.ROTATION, 0, 90)
                                    .setDuration(300)
                                    .start();
                            ObjectAnimator.ofFloat(photoSize, View.ROTATION, 0, 90)
                                    .setDuration(300)
                                    .start();

                            turnBackMenu = true;
                            turnMenu = false;

                            if(photosArrayList.size()!= 0){
                                for(int j=2; j < (photosArrayList.size()+2); j++) {
                                    ObjectAnimator.ofFloat(_frameLayout.getChildAt(j), View.ROTATION, 0, 90)
                                            .setDuration(300)
                                            .start();
                                }
                            }
                        }
                    }
                    else{
                        if(turnBackMenu == true){
                            ObjectAnimator.ofFloat(takePhoto, View.ROTATION, 90, 0)
                                    .setDuration(300)
                                    .start();
                            ObjectAnimator.ofFloat(savePhoto, View.ROTATION, 90, 0)
                                    .setDuration(300)
                                    .start();
                            ObjectAnimator.ofFloat(photoBrightness, View.ROTATION, 90, 0)
                                    .setDuration(300)
                                    .start();
                            ObjectAnimator.ofFloat(photoColors, View.ROTATION, 90, 0)
                                    .setDuration(300)
                                    .start();
                            ObjectAnimator.ofFloat(photoExposure, View.ROTATION, 90, 0)
                                    .setDuration(300)
                                    .start();
                            ObjectAnimator.ofFloat(photoSize, View.ROTATION, 90, 0)
                                    .setDuration(300)
                                    .start();

                            turnMenu = true;
                            turnBackMenu = false;

                            if(photosArrayList.size()!= 0){
                                for(int j=2; j < (photosArrayList.size()+2); j++){
                                    ObjectAnimator.ofFloat(_frameLayout.getChildAt(j), View.ROTATION, 90, 0)
                                            .setDuration(300)
                                            .start();
                                }
                            }
                        }
                    }
            }
        };


        _frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(hideMenu == true){
                    ObjectAnimator.ofFloat(takesavephotoLayout, View.TRANSLATION_Y, 100)
                            .setDuration(300) //ms
                            .start();
                    ObjectAnimator.ofFloat(photooptionsLayout, View.TRANSLATION_Y, -100)
                            .setDuration(300) //ms
                            .start();
                }
                else if(hideMenu == false){
                    ObjectAnimator.ofFloat(takesavephotoLayout, View.TRANSLATION_Y, 0)
                            .setDuration(300) //ms
                            .start();
                    ObjectAnimator.ofFloat(photooptionsLayout, View.TRANSLATION_Y, 0)
                            .setDuration(300) //ms
                            .start();
                }

                hideMenu = !hideMenu;
            }
        });

    }

    private Camera.PictureCallback camPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            fdata = data;
            camera.startPreview();

            if(countPhotoPreviews>0){
                for(int i=0;i<photosArrayList.size();i++){
                    _frameLayout.removeViewAt(2);
                }
            }

            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            Bitmap smallBmp = Bitmap.createScaledBitmap(bitmap , 100, 100, false);

            Miniatura miniatura = new Miniatura(CameraActivity.this,smallBmp,100,100);
            miniatura.setImageBitmap(smallBmp);

            photosArrayList.add(miniatura);
            refreshPreview();
            countPhotoPreviews++;
        }
    };

    public void refreshPreview(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int x=width/2;
        int height = size.y;
        int y=height/2;

        for(int i=0;i<photosArrayList.size();i++) {
            double rad = 0;
            angle = 360 / photosArrayList.size();

            double last = ((3 * Math.PI) / 2);
            rad = last + (((angle * Math.PI) / 180) * i);


            int sin = (int) Math.round(200 * Math.sin(rad));
            int cos = (int) Math.round(200 * Math.cos(rad));

            int xx = x + sin - 50;
            int yy = y + cos - 120;

            photosArrayList.get(i).setX(xx);
            photosArrayList.get(i).setY(yy);


            _frameLayout.addView(photosArrayList.get(i));

        }
    }

    @Override
    public void onPause() {
        super.onPause();

        // jeśli nie zwolnimy (release) kamery, inna aplikacje nie może jej używać

        if (camera != null) {
            camera.stopPreview();
            //linijka nieudokumentowana w API, bez niej jest crash przy wznawiamiu kamery
            _cameraPreview.getHolder().removeCallback(_cameraPreview);
            camera.release();
            camera = null;
        }

        orientationEventListener.disable();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (camera == null) {
            initCamera();
            initPreview();
        }

        if (orientationEventListener.canDetectOrientation()) {
            // Log - listener działa
            orientationEventListener.enable();
        } else {
            // Log - listener nie działa
        }
    }
}
