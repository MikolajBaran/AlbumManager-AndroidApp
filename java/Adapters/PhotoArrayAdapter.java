package com.example.a4id1.manageralbumow.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a4id1.manageralbumow.R;

import java.util.ArrayList;

/**
 * Created by 4id1 on 2017-11-16.
 */
public class PhotoArrayAdapter extends ArrayAdapter {
private String[] list;
private int resource;


    public PhotoArrayAdapter(Context context, int resource, String[] list) {
        super(context, resource, list);
        this.list = list;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //inflater - klasa konwertująca xml na kod javy
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.photo_layout, null);
//convertView = inflater.inflate(_resource, null);
//szukamy każdego TextView w layoucie
        TextView tvText = (TextView) convertView.findViewById(R.id.photoMenuText);
        tvText.setText(list[position]);

        ImageView ivImage = (ImageView) convertView.findViewById(R.id.photoMenuImage);
        if(position == 0){
            ivImage.setImageResource(R.drawable.ic_format_color_text_black_48dp);
        }
        else if(position == 1){
            ivImage.setImageResource(R.drawable.ic_file_upload_black_48dp);
        }
        else if(position == 2){
            ivImage.setImageResource(R.drawable.ic_blur_on_black_48dp);
        }
        else{
            ivImage.setImageResource(R.drawable.ic_keyboard_arrow_right_black_48dp);
        }

        return convertView;
    }
}
