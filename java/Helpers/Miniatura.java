package com.example.a4id1.manageralbumow.Helpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Mikołaj on 25.10.2017.
 */

public class Miniatura extends ImageView {
    private Bitmap bitmap;
    private int y;
    private int x;

    public Miniatura(Context context, Bitmap bitmap, int x, int y) {
        super(context);
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(x,y);
        lp.setMargins(0,0,0,0);
        this.setLayoutParams(lp);
        this.setBackgroundColor(0xff0000ff);
        this.setScaleType(ScaleType.CENTER_CROP);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(Color.argb(150, 255, 255, 255)); // lub paint.setColor(Color.RED);
        canvas.drawRect(0,0,100,100, paint);
    }
}
