package com.example.a4id1.manageralbumow.Activities;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.a4id1.manageralbumow.Helpers.PreviewText;
import com.example.a4id1.manageralbumow.R;

import java.io.IOException;

public class FontsActivity extends AppCompatActivity {

    private LinearLayout scrollLayout;
    private LinearLayout fontPreview;
    private PreviewText previewText;
    private Button button;
    private EditText editText;
    private String fontName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fonts);


        scrollLayout = (LinearLayout) findViewById(R.id.fontsList);

        fontPreview = (LinearLayout) findViewById(R.id.fontPreview);
        AssetManager assetManager = getAssets();
        try {
            final String[] lista = assetManager.list("Fonts"); // fonts to nazwa podfolderu w assets
            for(int i=0; i<lista.length; i++){
                final LinearLayout tmp = new LinearLayout(FontsActivity.this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                tmp.setLayoutParams(params);
                final TextView text = new TextView(FontsActivity.this);
                text.setText(lista[i].split("\\.")[0]);
                text.setTextSize(30);
                //text.setTextColor(pxl);
                final Typeface tf= Typeface.createFromAsset(getAssets(),"Fonts/" + lista[i]);
                text.setTypeface (tf);
                tmp.addView(text);
                tmp.setId(i);
                scrollLayout.addView(tmp);

                tmp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fontPreview.removeAllViews();
                        int color = -8000000;
                        previewText = new PreviewText(FontsActivity.this,color, tf,"text");
                        fontPreview.addView(previewText);

                        int id = tmp.getId();
                        fontName = lista[id];
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        TextWatcher textWatcher = new TextWatcher(){
            @Override
            public void afterTextChanged(Editable s) {
                fontPreview.removeAllViews();
                fontPreview.addView(previewText);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        };

        button = (Button) findViewById(R.id.fontButton);
        editText = (EditText) findViewById(R.id.fontPreviewText);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                String text = editText.getText().toString();
                intent.putExtra("text", text);
                intent.putExtra("font", fontName);
                setResult(FontsActivity.RESULT_OK, intent);
                finish();

            }
        });

    }
}
