package com.example.a4id1.manageralbumow.Helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by 4id1 on 2017-10-12.
 */
public class DatabaseManager extends SQLiteOpenHelper {
    public DatabaseManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tabela1 (_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 'title' TEXT, 'text' TEXT, 'color' INT, 'photoname' TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tabela1");
        onCreate(db);
    }


    public boolean insert(String title, String text, int color, String photoname){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("text", text);
        contentValues.put("color", color);
        contentValues.put("photoname", photoname);

        db.insertOrThrow("tabela1", null, contentValues); // gdy insert się nie powiedzie, będzie błąd
        db.close();
        return true;
    }

    public ArrayList<Note> getAll(){

        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Note> notes= new ArrayList<>();
        Cursor result = db.rawQuery("SELECT * FROM tabela1" , null);
        while(result.moveToNext()){
            notes.add( new Note(
                    result.getString(result.getColumnIndex("title")),
                    result.getString(result.getColumnIndex("text")),
                    result.getInt(result.getColumnIndex("color")),
                    result.getString(result.getColumnIndex("photoname"))
            ));

        }
        return notes;
    }

    public void delete(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM tabela1 WHERE photoname='" + id + "'");
    }

    public void edit(String id, String title, String text, int color){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("title", title);
        contentValues.put("text", text);
        contentValues.put("color", color);

        db.execSQL("UPDATE tabela1 SET title='"+title+"', text='" + text + "', color='" + color + "' WHERE photoname='" + id + "'");
        db.close();
    }
}
