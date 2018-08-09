package com.example.a4id1.manageralbumow.Helpers;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.icu.text.DisplayContext;
import android.view.View;

/**
 * Created by 4id1 on 2017-12-14.
 */
public class PreviewText extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private String text;
    private Typeface font;
    private int color;

    public PreviewText(Context context, int color, Typeface font, String text) {
        super(context);
        this.color = color;
        this.font = font;
        this.text = text;

        paint.reset();
        paint.setAntiAlias(true);
        paint.setTextSize(100);
        paint.setTypeface(font);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);
        canvas.drawText(text, 90, 220, paint);

    }

    public void setText(String text){
        this.text = text;
    }

    public void setFont(Typeface font){
        this.font = font;
        paint.setTypeface(font);
    }
}
