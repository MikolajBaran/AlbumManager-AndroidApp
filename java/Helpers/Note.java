package com.example.a4id1.manageralbumow.Helpers;

/**
 * Created by 4id1 on 2017-10-19.
 */
public class Note {
    private String title;
    private String text;
    private int color;
    private String photoname;

    public Note(String title, String text, int color, String photoname) {
        this.title = title;
        this.text = text;
        this.color = color;
        this.photoname = photoname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getPhotoname() {
        return photoname;
    }

    public void setPhotoname(String photoname) {
        this.photoname = photoname;
    }
}
