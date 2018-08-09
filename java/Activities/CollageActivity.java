package com.example.a4id1.manageralbumow.Activities;

import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;

import com.example.a4id1.manageralbumow.Helpers.ImageData;
import com.example.a4id1.manageralbumow.R;

import java.util.ArrayList;

public class CollageActivity extends AppCompatActivity {

    private ArrayList<ImageData> list = new ArrayList<>();
    private LinearLayout collage1, collage2, collage3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collage);

        collage1 = (LinearLayout) findViewById(R.id.collage_LR);
        collage2 = (LinearLayout) findViewById(R.id.collage_UD);
        collage3 = (LinearLayout) findViewById(R.id.collage_ULRD);

        collage1.setOnClickListener(onclick);
        collage2.setOnClickListener(onclick);
        collage3.setOnClickListener(onclick);


    }

    private View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            list.clear();

            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int x=width/2;
            int height = size.y;
            int y=height/2;

            switch (v.getId()) {
                case R.id.collage_LR:
                    list.add(new ImageData(0, 0, x, height));
                    list.add(new ImageData(x, 0, x, height));
                    break;
                case R.id.collage_UD:
                    list.add(new ImageData(0, 0, width, y));
                    list.add(new ImageData(0, y, width, y));
                    break;
                case R.id.collage_ULRD:
                    list.add(new ImageData(0, 0, width, height/3));
                    list.add(new ImageData(0, height/3, x, height/3));
                    list.add(new ImageData(x, height/3, x, height/3));
                    list.add(new ImageData(0, (2*height)/3, width, height/3));
                    break;
            }
            Intent intent = new Intent(CollageActivity.this,ChosenCollageActivity.class);
            intent.putExtra("list", list);
            startActivity(intent);
        }
    };
}
