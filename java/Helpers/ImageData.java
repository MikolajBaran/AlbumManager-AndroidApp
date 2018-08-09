package com.example.a4id1.manageralbumow.Helpers;

import java.io.Serializable;

/**
 * Created by 4id1 on 2017-11-09.
 */
public class ImageData implements Serializable {
    private int x,y,w,h;

    public int getH() {
        return h;
    }

    public int getW() {
        return w;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public ImageData(int x, int y, int w, int h) {
        this.h = h;
        this.w = w;
        this.x = x;

        this.y = y;
    }



}
