package com.example.a4id1.manageralbumow.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a4id1.manageralbumow.Helpers.DatabaseManager;
import com.example.a4id1.manageralbumow.Helpers.Note;
import com.example.a4id1.manageralbumow.R;

import java.util.ArrayList;

/**
 * Created by 4id1 on 2017-10-26.
 */
public class MyArrayAdapter extends ArrayAdapter {

    private ArrayList<Note> _list;
    private Context _context;
    private int _resource;
    private DatabaseManager _db;

    public MyArrayAdapter(Context context, int resource, ArrayList objects, DatabaseManager db) {
        super(context, resource, objects);

        this._list = objects;
        this._context = context;
        this._resource = resource;
        this._db = db;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //inflater - klasa konwertująca xml na kod javy
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.note_layout, null);
//convertView = inflater.inflate(_resource, null);
//szukamy każdego TextView w layoucie

        TextView tvId = (TextView) convertView.findViewById(R.id.listviewTextId);
        tvId.setText(_db.getAll().get(position).getPhotoname());

        TextView tvTitle = (TextView) convertView.findViewById(R.id.listviewTextTitle);
        tvTitle.setText(_db.getAll().get(position).getTitle());

        Integer color = _db.getAll().get(position).getColor();
        tvTitle.setTextColor(color);

        TextView tvText = (TextView) convertView.findViewById(R.id.listviewTextText);
        tvText.setText(_db.getAll().get(position).getText());

//gdybyśmy chcieli klikać Imageview wewnątrz wiersza:
       /* ImageView iv1 = (ImageView) convertView.findViewById(R.id.iv2);
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // klik w obrazek
            }
        });*/

        return convertView;
    }
}
