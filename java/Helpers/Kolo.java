package com.example.a4id1.manageralbumow.Helpers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by Mikołaj on 25.10.2017.
 */

public class Kolo extends View {
    public Kolo(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = this.getWidth();
        int height = this.getHeight();
        super.onDraw(canvas);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(Color.argb(150, 255, 255, 255)); // lub paint.setColor(Color.RED);
        canvas.drawCircle(width/2, height/2, width/4, paint);
    }
}
